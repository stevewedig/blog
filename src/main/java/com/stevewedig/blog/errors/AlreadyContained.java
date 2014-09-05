package com.stevewedig.blog.errors;


/**
 * Thrown when a container already contains an item or key.
 */
public class AlreadyContained extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public AlreadyContained() {
    super();
  }

  public AlreadyContained(String template, Object... parts) {
    super(template, parts);
  }
}
