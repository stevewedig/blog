package com.stevewedig.blog.translate;

import com.stevewedig.blog.errors.ErrorMixin;

public class ParseError extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public ParseError() {
    super();
  }

  public ParseError(String template, Object... parts) {
    super(template, parts);
  }

  public ParseError(Exception e) {
    super(e);
  }
}
