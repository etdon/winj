package com.etdon.winjgen.util;

import com.etdon.commons.conditional.Preconditions;
import com.etdon.commons.tuple.Pair;
import com.etdon.commons.util.MapUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class Humanizer {

    private static final Map<String, Pair<String, WordOrder>> WORD_EXPANSIONS = MapUtils.newMap(
            Pair.of("wnd", Pair.of("window", WordOrder.LATE)),
            Pair.of("n", Pair.of("", WordOrder.NORMAL)),
            Pair.of("dw", Pair.of("", WordOrder.NORMAL)),
            Pair.of("ex", Pair.of("extended", WordOrder.NORMAL)),
            Pair.of("h", Pair.of("handle", WordOrder.LAST)),
            Pair.of("lp", Pair.of("pointer", WordOrder.LAST)),
            Pair.of("lpsz", Pair.of("name", WordOrder.LATE)),
            Pair.of("msg", Pair.of("message", WordOrder.NORMAL)),
            Pair.of("wparam", Pair.of("firstParameter", WordOrder.NORMAL)),
            Pair.of("lparam", Pair.of("secondParameter", WordOrder.NORMAL)),
            Pair.of("lpcb", Pair.of("", WordOrder.NORMAL)),
            Pair.of("hdc", Pair.of("deviceContextHandle", WordOrder.NORMAL)),
            Pair.of("rect", Pair.of("rectangle", WordOrder.NORMAL))
    );

    @NotNull
    public static String humanizeNativeParameterName(@NotNull final String input) {

        Preconditions.checkNotNull(input);
        final char[] chars = input.toCharArray();
        final List<Pair<String, WordOrder>> words = new ArrayList<>();
        final StringBuilder buffer = new StringBuilder();
        for (final char c : chars) {
            if (Character.isUpperCase(c)) {
                if (!buffer.isEmpty()) {
                    processExpansion(words, buffer);
                    buffer.setLength(0);
                    buffer.append(c);
                } else {
                    buffer.append(c);
                }
            } else {
                buffer.append(c);
            }
        }

        if (!buffer.isEmpty())
            processExpansion(words, buffer);

        final StringBuilder output = new StringBuilder();
        words.sort(Comparator.comparingInt((word) -> word.getValue().getValue()));
        for (final Pair<String, WordOrder> pair : words) {
            final String word = pair.getKey();
            if (output.isEmpty() && Character.isUpperCase(word.charAt(0))) {
                output.append(Character.toLowerCase(word.charAt(0))).append(word.substring(1));
            } else if (!output.isEmpty() && Character.isLowerCase(output.charAt(output.length() - 1))) {
                output.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
            } else {
                output.append(word);
            }
        }

        // Last minute word expansion.
        final Pair<String, WordOrder> override = WORD_EXPANSIONS.get(output.toString().toLowerCase());
        if (override != null) {
            output.setLength(0);
            output.append(override.getKey());
        }

        return output.toString();

    }

    private static void processExpansion(@NotNull final List<Pair<String, WordOrder>> words, @NotNull final StringBuilder buffer) {

        final Pair<String, WordOrder> expansion = WORD_EXPANSIONS.get(buffer.toString().toLowerCase());
        if (expansion != null) {
            final String expandedWord = expansion.getKey();
            if (!expandedWord.isEmpty())
                words.add(Pair.of(expandedWord, expansion.getValue()));
        } else {
            words.add(Pair.of(buffer.toString(), WordOrder.NORMAL));
        }

    }

    @NotNull
    public static String splitParameterName(@NotNull final String input) {

        Preconditions.checkNotNull(input);
        if (input.isEmpty())
            return "";

        final StringBuilder output = new StringBuilder();
        final char[] chars = input.toCharArray();
        for (final char c : chars) {
            if (Character.isLowerCase(c)) {
                output.append(c);
            } else {
                if (!output.isEmpty())
                    output.append(' ');
                output.append(Character.toLowerCase(c));
            }
        }

        return output.toString();

    }

    private Humanizer() {

        throw new UnsupportedOperationException();

    }

    private enum WordOrder {

        FIRST(0),
        EARLY(1),
        NORMAL(2),
        LATE(3),
        LAST(4);

        final int value;

        WordOrder(final int value) {

            this.value = value;

        }

        public int getValue() {

            return this.value;

        }

    }

}
