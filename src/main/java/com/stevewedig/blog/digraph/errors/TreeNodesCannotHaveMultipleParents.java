package com.stevewedig.blog.digraph.errors;

import com.stevewedig.blog.errors.ErrorMixin;

public class TreeNodesCannotHaveMultipleParents extends ErrorMixin {
  private static final long serialVersionUID = 1L;
  
  public TreeNodesCannotHaveMultipleParents() {
    super();
  }
  
  public TreeNodesCannotHaveMultipleParents(String template, Object... parts) {
    super(template, parts);
  };
}
