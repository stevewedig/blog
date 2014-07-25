package com.stevewedig.blog.format;

public interface Format<Model> extends FormatParser<Model>, FormatWriter<Model>,
    Translator<String, Model> {

}
