package com.stevewedig.blog.format.url_query;

import com.google.common.collect.ImmutableMap;

public interface UrlQuery {
  ImmutableMap<String,String> params();

  boolean isEmpty();
  boolean notEmpty();
}
