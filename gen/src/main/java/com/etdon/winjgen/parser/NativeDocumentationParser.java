package com.etdon.winjgen.parser;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.util.Exceptional;
import org.jetbrains.annotations.NotNull;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

public final class NativeDocumentationParser {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .build();

    public NativeDocumentationParser(@NotNull final String url) {

        if (!this.isValidUrl(url))
            throw Exceptional.of(RuntimeException.class, "The provided URL is not a valid native documentation URL.");

    }

    public boolean isValidUrl(@NotNull final String url) {

        final List<String> components = new ArrayList<>();
        final char[] chars = url.toCharArray();
        final StringBuilder componentBuffer = new StringBuilder();
        for (final char c : chars) {
            if (c == '.') {
                if (!componentBuffer.isEmpty()) {
                    components.add(componentBuffer.toString());
                    componentBuffer.setLength(0);
                }
            } else {
                componentBuffer.append(c);
            }
        }

        Preconditions.checkState(components.size() == 2);
        return components.getFirst().equals("learn") && components.getLast().equals("microsoft");

    }

}
