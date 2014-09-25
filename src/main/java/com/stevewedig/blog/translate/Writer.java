package com.stevewedig.blog.translate;

/**
 * A Writer writes, so it converts from Model to Syntax.
 */
public interface Writer<Syntax, Model> {

  /**
   * Convert a model to the corresponding syntax.
   */
  Syntax write(Model model);
}
