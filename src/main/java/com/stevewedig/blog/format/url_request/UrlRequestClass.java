package com.stevewedig.blog.format.url_request;

import com.stevewedig.blog.format.url_path.UrlPath;
import com.stevewedig.blog.format.url_query.UrlQuery;
import com.stevewedig.blog.value_objects.ValueMixin;

class UrlRequestClass extends ValueMixin implements UrlRequest {
  private final UrlPath path;
  private final UrlQuery query;

  public UrlRequestClass(UrlPath path, UrlQuery query) {
    super();
    this.path = path;
    this.query = query;
  }

  @Override
  public UrlPath path() {
    return path;
  }

  @Override
  public UrlQuery query() {
    return query;
  }

  // ===========================================================================
  // ValueMixin
  // ===========================================================================

  @Override
  public Object[] fields() {
    return array("path", path, "query", query);
  }

  // ===========================================================================
  
  @Override
  public boolean isFile() {
    return path.isFile();
  }

  @Override
  public boolean isDir() {
    return path.isDir();
  }

  @Override
  public UrlRequest toggleIsFile() {
    return UrlRequestLib.request(path().toggleIsFile(), query());
  }

}
