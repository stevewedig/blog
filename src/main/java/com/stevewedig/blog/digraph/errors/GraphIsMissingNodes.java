package com.stevewedig.blog.digraph.errors;

import com.stevewedig.blog.errors.ErrorMixin;

public class GraphIsMissingNodes extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public GraphIsMissingNodes() {
    super();
  }

  public GraphIsMissingNodes(String template, Object... parts) {
    super(template, parts);
  };
}