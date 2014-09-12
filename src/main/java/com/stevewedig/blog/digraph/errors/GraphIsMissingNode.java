package com.stevewedig.blog.digraph.errors;

import com.stevewedig.blog.errors.ErrorMixin;

public class GraphIsMissingNode extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public GraphIsMissingNode() {
    super();
  }

  public GraphIsMissingNode(String template, Object... parts) {
    super(template, parts);
  };
}