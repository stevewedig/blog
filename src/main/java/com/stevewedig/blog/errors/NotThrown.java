package com.stevewedig.blog.errors;

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
