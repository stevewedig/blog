package com.stevewedig.blog.errors;

/**
 * Thrown when inherited mutation methods are called on immutable objects.
 */
public class NotMutable extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public NotMutable() {
    super();
  }

  public NotMutable(String template, Object... parts) {
    super(template, parts);
  }
}
