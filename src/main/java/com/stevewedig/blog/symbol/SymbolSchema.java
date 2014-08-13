package com.stevewedig.blog.symbol;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * Validates SymbolMaps or any other set of symbols by checking required and optional symbols.
 */
public interface SymbolSchema {

  // ===========================================================================
  // creating
  // ===========================================================================

  SymbolSchema withOptional(Symbol<?>... optionalSymbols);

  // ===========================================================================
  // symbols
  // ===========================================================================

  ImmutableSet<Symbol<?>> symbols();

  ImmutableSet<Symbol<?>> requiredSymbols();

  ImmutableSet<Symbol<?>> optionalSymbols();

  // ===========================================================================
  // validate
  // ===========================================================================

  void validate(Symbol<?>... symbols) throws InvalidSymbols;

  void validate(Iterable<Symbol<?>> symbols) throws InvalidSymbols;

  void validate(Set<Symbol<?>> symbols) throws InvalidSymbols;

  void validate(SymbolMap map) throws InvalidSymbols;

}
