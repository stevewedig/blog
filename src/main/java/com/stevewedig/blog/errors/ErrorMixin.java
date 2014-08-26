package com.stevewedig.blog.errors;

import static com.stevewedig.blog.util.StrLib.formatN;

/**
 * Extends RuntimeException with convenient constructors.
 */
public abstract class ErrorMixin extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ErrorMixin() {
    super();
  }

  public ErrorMixin(String template, Object... parts) {
    super(formatN(template, parts));
  }
  
  public ErrorMixin(Exception e) {
    this("Caused by %s", e);
  }

}
