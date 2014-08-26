package com.stevewedig.blog.translate;

/**
 * Converts between Syntax and Model, so a Translator parses and writes.
 */
public interface Translator<Syntax, Model> extends Parser<Syntax, Model>, Writer<Syntax, Model> {
}
