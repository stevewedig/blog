package com.stevewedig.blog.digraph.errors;

import com.stevewedig.blog.errors.ErrorMixin;

public class GraphHadUnexpectedIds extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public GraphHadUnexpectedIds() {
    super();
  }

  public GraphHadUnexpectedIds(String template, Object... parts) {
    super(template, parts);
  };
}