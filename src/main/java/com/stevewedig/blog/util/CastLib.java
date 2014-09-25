package com.stevewedig.blog.util;

import java.util.Map;

/**
 * Casting related utilities.
 */
public abstract class CastLib {

  /**
   * Cast a value to the Value parameter, which can be inferred from an assignment.
   */
  public static <Value> Value cast(Object value) {

    @SuppressWarnings("unchecked")
    Value casted = (Value) value;

    return casted;
  }

  /**
   * Get a value from a map, and cast it to the Value parameter, which can be inferred from an
   * assignment.
   */
  public static <Value> Value get(Map<?, ?> map, Object key) {

    Object value = map.get(key);

    return cast(value);
  }

}
