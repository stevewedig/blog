package com.stevewedig.blog.errors;

/**
 * Thrown when a method is not implemented.
 */
public class NotImplemented extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public NotImplemented() {
    super();
  }

  public NotImplemented(String template, Object... parts) {
    super(template, parts);
  }
}
