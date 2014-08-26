package com.stevewedig.blog.translate;

/**
 * Converts from Syntax to Model, so a Parser parses.
 */
public interface Parser<Syntax, Model> {

  Model parse(Syntax syntax) throws ParseError;
}
