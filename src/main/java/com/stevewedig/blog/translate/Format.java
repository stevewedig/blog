package com.stevewedig.blog.translate;

public interface Format<Model> extends FormatParser<Model>, FormatWriter<Model>,
    Translator<String, Model> {
}
