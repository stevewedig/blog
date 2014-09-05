package com.stevewedig.blog.digraph.node;

import com.stevewedig.blog.errors.ErrorMixin;

/**
 * Thrown when trying to create an invalid graph.
 */
public class NodeIdConflict extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public NodeIdConflict() {
    super();
  }

  public NodeIdConflict(String template, Object... parts) {
    super(template, parts);
  }
}
