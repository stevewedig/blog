package com.stevewedig.blog.errors;

/**
 * Thrown when inherited mutation methods are called on immutable objects.
 */
public class NotMutable extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public NotMutable() {
    super();
  }

  public NotMutable(String string) {
    super(string);
  }

}
