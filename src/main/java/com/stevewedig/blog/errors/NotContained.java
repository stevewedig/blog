package com.stevewedig.blog.errors;

/**
 * Thrown when a container didn't contain an item or key.
 */
public class NotContained extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public NotContained() {
    super();
  }

  public NotContained(String template, Object... parts) {
    super(template, parts);
  }
}
