package com.stevewedig.blog.symbol;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

// can't extend Map because get() is more restrictive, however you can access the stateCopy()
/**
 * A type safe mapping from symbols to values of matching types.
 */
public interface SymbolMap extends Iterable<Entry<Symbol<?>, Object>> {

  // ===========================================================================
  // SymbolMap
  // ===========================================================================

  /**
   * 
   * @return type safe value associated with symbol
   */
  <Value> Value get(Symbol<Value> symbol);

  // ===================================
  // convenience getters
  // ===================================

  <Value> Value getDefault(Symbol<Value> symbol, Value defaultValue, boolean adaptNull);

  <Value> Value getDefault(Symbol<Value> symbol, Value defaultValue);

  <Value> Optional<Value> getOptional(Symbol<Value> symbol, boolean adaptNull);

  <Value> Optional<Value> getOptional(Symbol<Value> symbol);

  <Value> Value getNullable(Symbol<Value> symbol);

  // ===================================
  // symbols
  // ===================================

  /**
   * @return Whether the symbol is contained in the SymbolMap.
   */
  boolean contains(Symbol<?> symbol);

  /**
   * @return The symbols in the SymbolMap.
   */
  ImmutableSet<Symbol<?>> symbols();

  // ===================================
  // copies
  // ===================================

  /**
   * @return A SymbolMap.Mutable copy.
   */
  SymbolMap.Mutable mutable();

  /**
   * @return An immutable SymbolMap copy.
   */
  SymbolMap immutable();

  /**
   * 
   * @return A Map copy.
   */
  Map<Symbol<?>, Object> stateCopy();

  /**
   * 
   * @return An ImmutableMap copy (which will fail if any values are null).
   */
  ImmutableMap<Symbol<?>, Object> immutableStateCopy();

  // ===========================================================================
  // SymbolMap.Mutable
  // ===========================================================================

  interface Mutable extends SymbolMap {

    // fluid interface allows us to build immutable maps via mutable ones
    <Value> SymbolMap.Mutable put(Symbol<Value> symbol, Value value);
    
    SymbolMap.Mutable putAll(SymbolMap delta);

  }

}
