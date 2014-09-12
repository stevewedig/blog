package com.stevewedig.blog.digraph.errors;

import com.stevewedig.blog.errors.ErrorMixin;

public class TreeCannotBeEmpty extends ErrorMixin {
  private static final long serialVersionUID = 1L;
  
  public TreeCannotBeEmpty() {
    super();
  }
  
  public TreeCannotBeEmpty(String template, Object... parts) {
    super(template, parts);
  };
}