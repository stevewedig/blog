package com.stevewedig.blog.format.url_request;

import java.util.Map;

import com.stevewedig.blog.format.Format;
import com.stevewedig.blog.format.url_path.UrlPath;
import com.stevewedig.blog.format.url_path.UrlPathLib;
import com.stevewedig.blog.format.url_query.UrlQuery;
import com.stevewedig.blog.format.url_query.UrlQueryLib;
import com.stevewedig.blog.util.LambdaLib.Fn2;

public abstract class UrlRequestLib {

  // ===========================================================================
  // path & query
  // ===========================================================================

  public static UrlRequest request(UrlPath path, UrlQuery query) {
    return new UrlRequestClass(path, query);
  }

  public static UrlRequest request(Iterable<String> pathParts, UrlQuery query) {
    return request(UrlPathLib.dir(pathParts), query);
  }

  public static UrlRequest request(UrlPath path, Map<String, String> params) {
    return request(path, UrlQueryLib.query(params));
  }

  public static UrlRequest request(Iterable<String> pathParts, Map<String, String> params) {
    return request(UrlPathLib.dir(pathParts), UrlQueryLib.query(params));
  }

  // ===========================================================================
  // blank
  // ===========================================================================

  public static UrlRequest request() {
    return request(UrlPathLib.rootDir(), UrlQueryLib.query());
  }

  // ===========================================================================
  // just path
  // ===========================================================================

  public static UrlRequest request(UrlPath path) {
    return request(path, UrlQueryLib.query());
  }

  public static UrlRequest request(Iterable<String> pathParts) {
    return request(UrlPathLib.dir(pathParts), UrlQueryLib.query());
  }

  // ===========================================================================
  // just query
  // ===========================================================================

  public static UrlRequest request(UrlQuery query) {
    return request(UrlPathLib.rootDir(), query);
  }

  public static UrlRequest request(Map<String, String> params) {
    return request(UrlPathLib.rootDir(), UrlQueryLib.query(params));
  }

  // ===========================================================================
  // format
  // ===========================================================================

  public static Format<UrlRequest> format(Format<UrlPath> pathFormat, Format<UrlQuery> queryFormat) {
    return new UrlRequestFormatClass(pathFormat, queryFormat);
  }

  public static Fn2<Format<UrlPath>, Format<UrlQuery>, Format<UrlRequest>> createFormat =
      new Fn2<Format<UrlPath>, Format<UrlQuery>, Format<UrlRequest>>() {
        @Override
        public Format<UrlRequest> apply(Format<UrlPath> a, Format<UrlQuery> b) {
          return format(a, b);
        }
      };

}
