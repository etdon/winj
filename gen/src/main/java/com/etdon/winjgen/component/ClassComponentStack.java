package com.etdon.winjgen.component;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.util.MapUtils;
import com.etdon.winjgen.component.impl.ClassComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ClassComponentStack {

    private final Map<Class<? extends ClassComponent>, Queue<ClassComponent>> flushed = new HashMap<>();
    private final Map<Class<? extends ClassComponent>, ClassComponent> stack = new HashMap<>();

    private Class<? extends ClassComponent> latest = null;

    public void set(@NotNull final ClassComponent component) {

        Preconditions.checkNotNull(component);
        this.latest = component.getClass();
        this.stack.put(component.getClass(), component);

    }

    @SuppressWarnings("unchecked")
    @NotNull
    public <T extends ClassComponent> T getOrCreate(@NotNull final Class<T> identifier) {

        Preconditions.checkNotNull(identifier);
        this.latest = identifier;
        return (T) Optional.ofNullable(this.stack.get(identifier)).orElseGet(() -> {
            try {
                final T classComponent = identifier.getConstructor().newInstance();
                this.set(classComponent);
                return classComponent;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    @SuppressWarnings("unchecked")
    @NotNull
    public <T extends ClassComponent> T getOrThrow(@NotNull final Class<T> identifier) {

        Preconditions.checkNotNull(identifier);
        this.latest = identifier;
        return (T) Optional.ofNullable(this.stack.get(identifier)).orElseThrow();

    }

    public boolean onStack(@NotNull final Class<? extends ClassComponent> identifier) {

        return this.stack.containsKey(identifier);

    }

    public void flushLatest() {

        Preconditions.checkNotNull(this.latest);
        this.flush(this.latest);

    }

    public void flush(@NotNull final Class<? extends ClassComponent> identifier) {

        Preconditions.checkNotNull(identifier);
        Optional.ofNullable(this.stack.remove(identifier)).ifPresent((value) -> {
            this.flushed.putIfAbsent(identifier, new ArrayDeque<>());
            MapUtils.addCollectionEntry(this.flushed, identifier, value);
        });

    }

    public void flushAll() {

        new HashSet<>(this.stack.keySet()).forEach(this::flush);

    }

    @Nullable
    public Queue<ClassComponent> getFlushedComponentsByIdentifier(@NotNull final Class<? extends ClassComponent> identifier) {

        Preconditions.checkNotNull(identifier);
        return this.flushed.get(identifier);

    }

    @NotNull
    public Map<Class<? extends ClassComponent>, Queue<ClassComponent>> getFlushedComponents() {

        return this.flushed;

    }

}
