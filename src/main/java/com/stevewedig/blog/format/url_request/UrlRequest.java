package com.stevewedig.blog.format.url_request;

import com.stevewedig.blog.format.url_path.UrlPath;
import com.stevewedig.blog.format.url_query.UrlQuery;

public interface UrlRequest {

  UrlPath path();

  UrlQuery query();

  // ===========================================================================

  boolean isFile();

  boolean isDir();

  UrlRequest toggleIsFile();
}
