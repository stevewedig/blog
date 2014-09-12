package com.stevewedig.blog.digraph.errors;

import com.stevewedig.blog.errors.ErrorMixin;

public class GraphContainedUnexpectedIds extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public GraphContainedUnexpectedIds() {
    super();
  }

  public GraphContainedUnexpectedIds(String template, Object... parts) {
    super(template, parts);
  };
}