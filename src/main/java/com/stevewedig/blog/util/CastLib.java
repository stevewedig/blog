package com.stevewedig.blog.util;

import java.util.Map;

public abstract class CastLib {

  public static <Value> Value cast(Object value) {

    @SuppressWarnings("unchecked")
    Value casted = (Value) value;

    return casted;
  }

  public static <Value> Value get(Map<?, ?> map, Object key) {

    Object value = map.get(key);

    return cast(value);
  }

}
