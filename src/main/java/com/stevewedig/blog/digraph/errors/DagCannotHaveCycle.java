package com.stevewedig.blog.digraph.errors;

import com.stevewedig.blog.errors.ErrorMixin;

public class DagCannotHaveCycle extends ErrorMixin {
  private static final long serialVersionUID = 1L;
  
  public DagCannotHaveCycle() {
    super();
  }
  
  public DagCannotHaveCycle(String template, Object... parts) {
    super(template, parts);
  };
}