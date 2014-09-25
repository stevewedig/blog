package com.stevewedig.blog.symbol;

import java.util.*;
import java.util.Map.Entry;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.value_objects.HasObjectHelper;

// can't extend Map because SymbolMap.get() is more restrictive
/**
 * A type safe mapping from symbols to values of matching types.
 */
public interface SymbolMap extends Iterable<Entry<Symbol<?>, Object>>, HasObjectHelper {

  // ===========================================================================
  // SymbolMap
  // ===========================================================================

  /**
   * Get the value associated with a symbol.
   */
  <Value> Value get(Symbol<Value> symbol);

  // ===================================
  // convenience getters
  // ===================================


  /**
   * Get the value associated with a symbol, or the defaultValue if it is missing, or the
   * defaultValue if the value is null.
   */
  <Value> Value getDefault(Symbol<Value> symbol, Value defaultValue);

  /**
   * Get the value associated with a symbol, or the defaultValue if it is missing, or the
   * defaultValue if its null and adaptNull = true.
   */
  <Value> Value getDefault(Symbol<Value> symbol, Value defaultValue, boolean adaptNull);

  /**
   * Get the value associated with a symbol, or Optional.absent if it is missing, or Optional.absent
   * if the value is null.
   */
  <Value> Optional<Value> getOptional(Symbol<Value> symbol);

  /**
   * Get the value associated with a symbol, or Optional.absent if it is missing, or Optional.absent
   * if the value is null and adaptTrue = true.
   */
  <Value> Optional<Value> getOptional(Symbol<Value> symbol, boolean adaptNull);

  /**
   * Get the value associated with a symbol, or null if it is missing.
   */
  <Value> Value getNullable(Symbol<Value> symbol);

  // ===================================
  // symbols
  // ===================================

  /**
   * Is the symbol a key in the SymbolMap?
   */
  boolean contains(Symbol<?> symbol);

  /**
   * The set of contained symbols.
   */
  ImmutableSet<Symbol<?>> symbols();

  // ===================================
  // copies
  // ===================================

  /**
   * Get a SymbolMap.Fluid copy.
   */
  SymbolMap.Fluid fluid();

  /**
   * Get a SymbolMap.Solid copy.
   */
  SymbolMap.Solid solid();

  /**
   * 
   * Get a Map copy of the state.
   */
  Map<Symbol<?>, Object> stateCopy();

  // ===========================================================================
  // SymbolMap.Solid
  // ===========================================================================

  /**
   * A SymbolMap that is immutable and a value object.
   */
  interface Solid extends SymbolMap {
  }

  // ===========================================================================
  // SymbolMap.Fluid
  // ===========================================================================

  /**
   * A SymbolMap that is mutable and an entity object.
   */
  interface Fluid extends SymbolMap {

    /**
     * Set the value associated with a symbol and return the map for use in map.put(k1, v1).put(k2,
     * v2)... chains.
     */
    <Value> SymbolMap.Fluid put(Symbol<Value> symbol, Value value);

    /**
     * Copy the state of updates into a symbol map.
     */
    SymbolMap.Fluid putAll(SymbolMap updates);

  }

}
