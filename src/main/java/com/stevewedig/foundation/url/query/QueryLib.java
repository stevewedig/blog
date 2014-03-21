package com.stevewedig.foundation.url.query;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.stevewedig.foundation.collect.MapLib;

public abstract class QueryLib {

  public static Query query(ImmutableMap<String,String> params) {
    return new QueryClass(params);
  }

  public static Query query(Map<String,String> params) {
    return query(ImmutableMap.copyOf(params));
  }
  
  public static Query query() {
    ImmutableMap<String,String> params = ImmutableMap.of();
    return query(params);
  }

  public static Query query(Object[] keysAndValues) {
    ImmutableMap<String, String> params = MapLib.unsafe(keysAndValues);
    return query(params);
  }
}
