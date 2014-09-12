package com.stevewedig.blog.digraph.errors;

import com.stevewedig.blog.errors.ErrorMixin;

public class TreeCannotHaveMultipleRoots extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public TreeCannotHaveMultipleRoots() {
    super();
  }

  public TreeCannotHaveMultipleRoots(String template, Object... parts) {
    super(template, parts);
  };
}