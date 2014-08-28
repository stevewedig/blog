package com.stevewedig.blog.translate;

/**
 * A Translator parses and writes, so it converts between Syntax and Model.
 */
public interface Translator<Syntax, Model> extends Parser<Syntax, Model>, Writer<Syntax, Model> {
}
