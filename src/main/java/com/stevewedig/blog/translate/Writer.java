package com.stevewedig.blog.translate;

/**
 * Converts from Model to Syntax, so a Writer writes.
 */
public interface Writer<Syntax, Model> {

  Syntax write(Model model);
}
