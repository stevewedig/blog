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

  /**
   * Create a schema with the addition of the optionalSymbols, for use in creating schemas via
   * SymbolLib.schema(requiredSymbols...).withOptional(optionalSymbols...).
   */
  SymbolSchema withOptional(Symbol<?>... optionalSymbols);

  // ===========================================================================
  // symbols
  // ===========================================================================

  /**
   * All of the symbols in the schema.
   */
  ImmutableSet<Symbol<?>> symbols();

  /**
   * Just the required symbols in the schema.
   */
  ImmutableSet<Symbol<?>> requiredSymbols();

  /**
   * Just the optional symbols in the schema.
   */
  ImmutableSet<Symbol<?>> optionalSymbols();

  // ===========================================================================
  // validate
  // ===========================================================================

  /**
   * Validate that required symbols are provided and that the provided symbols are all expected.
   */
  void validate(Symbol<?>... symbols) throws InvalidSymbols;

  /**
   * Validate that required symbols are provided and that the provided symbols are all expected.
   */
  void validate(Iterable<Symbol<?>> symbols) throws InvalidSymbols;

  /**
   * Validate that required symbols are provided and that the provided symbols are all expected.
   */
  void validate(Set<Symbol<?>> symbols) throws InvalidSymbols;

  /**
   * Validate the map's symbols, ensuring that required symbols are provided and that the provided
   * symbols are all expected.
   */
  void validate(SymbolMap map) throws InvalidSymbols;

}
