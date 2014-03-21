package com.stevewedig.foundation.url.query;

import com.google.common.collect.ImmutableMap;
import com.stevewedig.foundation.value_object.ValueMixin;
import com.stevewedig.foundation.value_object.ValueObjectHelper;

class QueryClass extends ValueMixin implements Query {
  private final ImmutableMap<String,String> params;

  public QueryClass(ImmutableMap<String,String> key__val) {
    this.params = key__val;
  }

  @Override
  public ImmutableMap<String,String> params() {
    return params;
  }

  // ===========================================================================
  // ValueMixin
  // ===========================================================================

  @Override
  public ValueObjectHelper objectHelper() {
    return objectHelper("params", params);
  }  
}
