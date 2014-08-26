package com.stevewedig.blog.translate;

/**
 * A "type alias" binding Translator's Syntax to String.
 */
public interface Format<Model> extends FormatParser<Model>, FormatWriter<Model>,
    Translator<String, Model> {
}
