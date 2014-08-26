package com.stevewedig.blog.symbol;

import com.stevewedig.blog.errors.ErrorMixin;


/**
 * Thrown by SymbolSchema when validation fails.
 */
public class InvalidSymbols extends ErrorMixin {
  private static final long serialVersionUID = 1L;

  public InvalidSymbols() {
    super();
  }

  public InvalidSymbols(String template, Object... parts) {
    super(template, parts);
  }
}
