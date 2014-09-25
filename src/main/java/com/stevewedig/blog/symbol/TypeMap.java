package com.stevewedig.blog.symbol;

import java.util.Map;

// can't extend Map because TypeMap.get() is more restrictive
/**
 * A type safe mapping from type objects to values of matching types.
 */
public interface TypeMap {

  // ===========================================================================
  // state
  // ===========================================================================

  /**
   * A Map copy of the state.
   */
  Map<Class<?>, Object> stateCopy();

  // ===========================================================================
  // core behavior
  // ===========================================================================

  /**
   * Set the value associated with a type, and return the map for use in map.put(k1, v1).put(k2,
   * v2)... chains.
   */
  <Value> TypeMap put(Class<Value> type, Value value);

  /**
   * Get the value associated with a type.
   */
  <Value> Value get(Class<Value> type);

  // ===========================================================================
  // other map behavior
  // ===========================================================================

  /**
   * Is there a value associated with a type?
   */
  boolean containsKey(Class<?> type);

  
  /**
   * Remove the value associated with the type.
   */
  void remove(Class<Integer> type);
  
  /**
   * Does the map contain any entries?
   */
  boolean isEmpty();

  /**
   * Empty the map.
   */
  void clear();

  /**
   * How many entries does the map contain?
   */
  int size();

}
