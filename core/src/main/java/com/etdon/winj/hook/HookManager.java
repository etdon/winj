package com.etdon.winj.hook;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.di.ServiceProvider;
import com.etdon.jbinder.function.NativeCaller;
import com.etdon.winj.function.kernel32.error.GetLastError;
import com.etdon.winj.function.user32.SetWindowsHookExW;
import com.etdon.winj.function.user32.UnhookWindowsHookEx;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.lang.foreign.MemorySegment;
import java.util.Map;
import java.util.concurrent.*;

public class HookManager implements Closeable {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Map<ManagedHook, HookRegistration> hooks = new ConcurrentHashMap<>();

    private final NativeCaller nativeCaller;

    public HookManager(@NotNull final ServiceProvider serviceProvider) {

        Preconditions.checkNotNull(serviceProvider);
        this.nativeCaller = serviceProvider.getOrThrow(NativeCaller.class);

    }

    public void register(@NotNull final ManagedHook managedHook) throws Throwable {

        Preconditions.checkNotNull(managedHook);
        this.registerWindowsHook(managedHook);
        this.hooks.put(managedHook, HookRegistration.NULL);

    }

    public void register(@NotNull final ManagedHook managedHook, final long delay, @NotNull final TimeUnit timeUnit) {

        Preconditions.checkNotNull(managedHook);
        Preconditions.checkNotNull(timeUnit);
        final Runnable thread = () -> {
            try {
                this.process(managedHook);
            } catch (final Throwable ex) {
                throw new RuntimeException(ex);
            }
        };

        final ScheduledFuture<?> task = scheduler.scheduleAtFixedRate(thread, 0, delay, timeUnit);
        this.hooks.put(managedHook, HookRegistration.ofTask(task));

    }

    public void unregister(@NotNull final String id) {

        Preconditions.checkNotNull(id);
        ManagedHook managedHook = null;
        for (final Map.Entry<ManagedHook, HookRegistration> entry : this.hooks.entrySet())
            if (entry.getKey().getId().equals(id))
                managedHook = entry.getKey();

        if (managedHook != null)
            this.unregister(managedHook);

    }

    public void unregister(@NotNull final ManagedHook managedHook) {

        try {
            Preconditions.checkNotNull(managedHook);
            if (managedHook.getHandle() != null)
                this.unregisterWindowsHook(managedHook.getHandle());
            final HookRegistration hookRegistration = this.hooks.remove(managedHook);
            if (hookRegistration != null && hookRegistration.task != null)
                hookRegistration.task.cancel(false);
        } catch (final Throwable ex) {
            throw new RuntimeException(ex);
        }

    }

    private void process(@NotNull final ManagedHook managedHook) throws Throwable {

        if (managedHook.getHandle() != null) {
            final boolean success = this.unregisterWindowsHook(managedHook.getHandle());
            if (success) {
                managedHook.setHandle(null);
            } else {
                throw new RuntimeException("Failed to unregister hook " + managedHook + " Error code: " + this.nativeCaller.call(GetLastError.getInstance()));
            }
        }

        final MemorySegment handle = this.registerWindowsHook(managedHook);
        if (handle.address() != 0L) {
            managedHook.setHandle(handle);
        } else {
            throw new RuntimeException("Failed to register hook " + managedHook + " - Error code: " + this.nativeCaller.call(GetLastError.getInstance()));
        }

    }

    private MemorySegment registerWindowsHook(@NotNull final ManagedHook managedHook) throws Throwable {

        return (MemorySegment) this.nativeCaller.call(
                SetWindowsHookExW.builder()
                        .type(managedHook.getType())
                        .procedurePointer(managedHook.getStub())
                        .ownerHandle(managedHook.getOwner())
                        .threadId(managedHook.getThreadId())
                        .build()
        );

    }

    private boolean unregisterWindowsHook(@NotNull final MemorySegment handle) throws Throwable {

        return ((int) this.nativeCaller.call(UnhookWindowsHookEx.builder().hookHandle(handle).build())) > 0;

    }

    @Override
    public void close() {

        this.hooks.keySet().forEach(this::unregister);
        this.scheduler.shutdownNow();

    }

    private static class HookRegistration {

        private static final HookRegistration NULL = new HookRegistration();

        private final ScheduledFuture<?> task;

        private HookRegistration(@NotNull final ScheduledFuture<?> task) {

            Preconditions.checkNotNull(task);
            this.task = task;

        }

        private HookRegistration() {

            this.task = null;

        }

        public ScheduledFuture<?> getTask() {

            return this.task;

        }

        private static HookRegistration ofTask(@NotNull final ScheduledFuture<?> task) {

            return new HookRegistration(task);

        }

    }

}
