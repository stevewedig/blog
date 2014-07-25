package com.stevewedig.blog.format.url_query;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.stevewedig.blog.format.Format;
import com.stevewedig.blog.group.query.MapLib;
import com.stevewedig.blog.util.LambdaLib.Fn1;

public abstract class UrlQueryLib {

  // ===========================================================================
  // query
  // ===========================================================================

  public static UrlQuery query(ImmutableMap<String, String> params) {
    return new UrlQueryClass(params);
  }

  public static UrlQuery query(Map<String, String> params) {
    return query(ImmutableMap.copyOf(params));
  }

  public static UrlQuery query(String... keysAndValues) {
    ImmutableMap<String, String> params = MapLib.unsafe(keysAndValues);
    return query(params);
  }

  // ===========================================================================
  // format
  // ===========================================================================

  public static Format<UrlQuery> format(Format<String> queryPartFormat) {
    return new UrlQueryFormatClass(queryPartFormat);
  }

  public static Fn1<Format<String>, Format<UrlQuery>> createFormat =
      new Fn1<Format<String>, Format<UrlQuery>>() {
        @Override
        public Format<UrlQuery> apply(Format<String> a) {
          return format(a);
        }
      };
}
