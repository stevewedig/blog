package com.stevewedig.blog.translate;

public interface Translator<Syntax, Model> extends Parser<Syntax, Model>, Writer<Syntax, Model> {
}
