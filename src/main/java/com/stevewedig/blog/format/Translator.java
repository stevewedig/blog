package com.stevewedig.blog.format;

public interface Translator<Syntax, Model> extends Parser<Syntax, Model>, Writer<Syntax, Model> {

}
