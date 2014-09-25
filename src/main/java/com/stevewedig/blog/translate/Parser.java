package com.stevewedig.blog.translate;

/**
 * A Parser parses, so it converts from Syntax to Model.
 */
public interface Parser<Syntax, Model> {

  /**
   * Convert syntax to the corresponding model.
   */
  Model parse(Syntax syntax) throws ParseError;
}
