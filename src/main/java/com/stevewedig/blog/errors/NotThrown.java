package com.stevewedig.blog.errors;

/**
 * Thrown when a test expected an exception to be thrown.
 */
public class NotThrown extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public NotThrown() {
    super();
  }

  public NotThrown(String template, Object... parts) {
    super(template, parts);
  }

  public NotThrown(Class<?> e) {
    this("Was expected to throw %s", e);
  }
}
