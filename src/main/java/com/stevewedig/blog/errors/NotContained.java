package com.stevewedig.blog.errors;

public class NotContained extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public NotContained() {
    super();
  }

  public NotContained(String template, Object... parts) {
    super(template, parts);
  }
}
