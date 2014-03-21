package com.stevewedig.foundation.url.query;

import com.google.common.collect.ImmutableMap;

public interface Query {
  ImmutableMap<String,String> params();
}
