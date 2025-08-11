package com.etdon.winjgen;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class Tokenizer {

    private static final Set<String> KEYWORDS = Set.of("typedef", "struct", "const");

    private final char[] input;

    public Tokenizer(@NotNull final String input) {

        this.input = input.toCharArray();

    }

    public List<Token> tokenize() {

        final List<Token> tokens = new ArrayList<>();
        final StringBuilder valueBuilder = new StringBuilder();
        boolean escape = false;
        for (final char c : this.input) {
            if (escape)
                break;
            switch (c) {
                case '(' -> {
                    if (!valueBuilder.isEmpty()) {
                        tokens.add(new Token(TokenType.FUNCTION_NAME, valueBuilder.toString()));
                        valueBuilder.setLength(0);
                    }
                    tokens.add(new Token(TokenType.PARAMETERS_START));
                }
                case ')' -> tokens.add(new Token(TokenType.PARAMETERS_END));
                case '[' -> tokens.add(new Token(TokenType.OPENING_SQUARE_BRACKET));
                case ']' -> {
                    if (!valueBuilder.isEmpty() && tokens.getLast().getType() == TokenType.OPENING_SQUARE_BRACKET) {
                        tokens.add(new Token(TokenType.FLOW, valueBuilder.toString()));
                        valueBuilder.setLength(0);
                    }
                    tokens.add(new Token(TokenType.CLOSING_SQUARE_BRACKET));
                }
                case '{' -> {
                    if (!valueBuilder.isEmpty()) {
                        tokens.add(new Token(TokenType.STRUCT_NAME, valueBuilder.toString()));
                        valueBuilder.setLength(0);
                    }
                    tokens.add(new Token(TokenType.OPENING_CURLY_BRACKET));
                }
                case '}' -> {
                    tokens.add(new Token(TokenType.CLOSING_CURLY_BRACKET));
                    escape = true;
                }
                case ',' -> {
                    if (tokens.getLast().getType() != TokenType.OPENING_SQUARE_BRACKET) {
                        if (!valueBuilder.isEmpty() && tokens.getLast().getType() == TokenType.TYPE) {
                            tokens.add(new Token(TokenType.PARAMETER_NAME, valueBuilder.toString()));
                            valueBuilder.setLength(0);
                        }
                        tokens.add(new Token(TokenType.SEPARATOR));
                    } else {
                        valueBuilder.append(c);
                    }
                }
                case ';' -> {
                    if (!valueBuilder.isEmpty() && tokens.getLast().getType() == TokenType.TYPE) {
                        tokens.add(new Token(TokenType.PARAMETER_NAME, valueBuilder.toString()));
                        valueBuilder.setLength(0);
                    }
                    tokens.add(new Token(TokenType.SEMICOLON));
                }
                case ' ', '\t', '\r', '\n' -> {
                    if (tokens.isEmpty() && !valueBuilder.isEmpty()) {
                        final String value = valueBuilder.toString();
                        valueBuilder.setLength(0);
                        if (KEYWORDS.contains(value)) {
                            tokens.add(new Token(TokenType.KEYWORD, value));
                        } else {
                            tokens.add(new Token(TokenType.RETURN_TYPE, value));
                        }
                    } else if (!valueBuilder.isEmpty()) {
                        final String value = valueBuilder.toString();
                        if (KEYWORDS.contains(value)) {
                            tokens.add(new Token(TokenType.KEYWORD, value));
                            valueBuilder.setLength(0);
                        } else {
                            switch (tokens.getLast().getType()) {
                                case CLOSING_SQUARE_BRACKET, OPENING_CURLY_BRACKET, SEMICOLON -> {
                                    tokens.add(new Token(TokenType.TYPE, value.toUpperCase()));
                                    valueBuilder.setLength(0);
                                }
                                case TYPE -> {
                                    tokens.add(new Token(TokenType.PARAMETER_NAME, value));
                                    valueBuilder.setLength(0);
                                }
                            }
                        }
                    }
                }
                default -> {
                    if (this.isSupportedCharacter(c))
                        valueBuilder.append(c);
                }
            }
        }

        return tokens;

    }

    private boolean isSupportedCharacter(final char c) {

        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9') ||
                c == '_';

    }

}
