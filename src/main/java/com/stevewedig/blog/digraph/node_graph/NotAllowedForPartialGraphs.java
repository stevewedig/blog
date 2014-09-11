package com.stevewedig.blog.digraph.node_graph;

import com.stevewedig.blog.errors.ErrorMixin;

/**
 * Thrown when trying to create an invalid graph.
 */
public class NotAllowedForPartialGraphs extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public NotAllowedForPartialGraphs() {
    super();
  }

  public NotAllowedForPartialGraphs(String template, Object... parts) {
    super(template, parts);
  }
}
