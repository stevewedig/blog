package com.stevewedig.blog.digraph.errors;

import com.stevewedig.blog.errors.ErrorMixin;

public class NodeIdConflict extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public NodeIdConflict() {
    super();
  }

  public NodeIdConflict(String template, Object... parts) {
    super(template, parts);
  }
}
