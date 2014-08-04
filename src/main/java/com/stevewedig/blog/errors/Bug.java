package com.stevewedig.blog.errors;



/**
 * Thrown when we have a bug in the code.
 */
public class Bug extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public Bug() {
    super();
  }

  public Bug(String template, Object... parts) {
    super(template, parts);
  }
}
