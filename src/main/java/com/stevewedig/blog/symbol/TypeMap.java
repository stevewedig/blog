package com.stevewedig.blog.symbol;

import java.util.Map;

// can't extend Map because TypeMap.get() is more restrictive
/**
 * A type safe mapping from type objects to values of matching types.
 */
public interface TypeMap {

  // ===========================================================================
  // copy
  // ===========================================================================

  Map<Class<?>, Object> stateCopy();

  // ===========================================================================
  // core behavior
  // ===========================================================================

  <Value> TypeMap put(Class<Value> type, Value value);

  <Value> Value get(Class<Value> type);

  // ===========================================================================
  // other map behavior
  // ===========================================================================

  boolean containsKey(Class<?> type);

  void remove(Class<Integer> type);

  boolean isEmpty();

  void clear();

  int size();


}
