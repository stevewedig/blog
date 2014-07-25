package com.stevewedig.blog.format.url_request;

import com.stevewedig.blog.format.Format;
import com.stevewedig.blog.format.url_path.UrlPath;
import com.stevewedig.blog.format.url_path.UrlPathLib;
import com.stevewedig.blog.format.url_query.UrlQuery;
import com.stevewedig.blog.format.url_query.UrlQueryLib;

public abstract class FakeUrlRequestFormatLib {

  // ===========================================================================
  // path
  // ===========================================================================

  public static Format<String> urlPathPartFormat = new Format<String>() {

    @Override
    public String parse(String writtenPathPart) {
      return writtenPathPart;
    }

    @Override
    public String write(String pathPart) {
      return pathPart;
    }
  };
  
  public static Format<UrlPath> urlPathFormat = UrlPathLib.format(urlPathPartFormat);

  // ===========================================================================
  // query
  // ===========================================================================

  public static Format<String> urlQueryPartFormat = new Format<String>() {

    @Override
    public String parse(String writtenQueryPart) {
      return writtenQueryPart;
    }

    @Override
    public String write(String queryPart) {
      return queryPart;
    }
  };

  public static Format<UrlQuery> urlQueryFormat = UrlQueryLib.format(urlQueryPartFormat);

  // ===========================================================================
  // request
  // ===========================================================================
  
  public static Format<UrlRequest> urlRequestFormat = UrlRequestLib.format(urlPathFormat, urlQueryFormat);

}
