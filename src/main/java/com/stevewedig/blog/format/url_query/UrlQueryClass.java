package com.stevewedig.blog.format.url_query;

import com.google.common.collect.ImmutableMap;
import com.stevewedig.blog.value_objects.ValueMixin;

class UrlQueryClass extends ValueMixin implements UrlQuery {
  private final ImmutableMap<String, String> params;

  public UrlQueryClass(ImmutableMap<String, String> key__val) {
    this.params = key__val;
  }

  @Override
  public ImmutableMap<String, String> params() {
    return params;
  }

  // ===========================================================================
  // ValueMixin
  // ===========================================================================

  @Override
  public Object[] fields() {
    return array("params", params);
  }

  // ===========================================================================

  @Override
  public boolean isEmpty() {
    return params.isEmpty();
  }

  @Override
  public boolean notEmpty() {
    return !isEmpty();
  }
}
