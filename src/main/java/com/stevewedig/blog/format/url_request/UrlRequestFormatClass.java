package com.stevewedig.blog.format.url_request;

import static java.util.Objects.requireNonNull;

import com.stevewedig.blog.format.Format;
import com.stevewedig.blog.format.ParseError;
import com.stevewedig.blog.format.url_path.UrlPath;
import com.stevewedig.blog.format.url_query.UrlQuery;
import com.stevewedig.blog.format.url_query.UrlQueryLib;

public class UrlRequestFormatClass implements Format<UrlRequest> {

  private static char QUESTION = '?';

  private Format<UrlPath> pathFormat;
  private Format<UrlQuery> queryFormat;

  public UrlRequestFormatClass(Format<UrlPath> pathFormat, Format<UrlQuery> queryFormat) {
    super();
    this.pathFormat = pathFormat;
    this.queryFormat = queryFormat;
  }

  // ===========================================================================
  // parse
  // ===========================================================================

  @Override
  public String write(UrlRequest request) {
    requireNonNull(request);

    UrlPath path = request.path();
    UrlQuery query = request.query();

    String pathStr = pathFormat.write(path);

    if (query.isEmpty())
      return pathStr;

    String queryStr = queryFormat.write(query);

    return pathStr + QUESTION + queryStr;
  }

  // ===========================================================================
  // write
  // ===========================================================================

  @Override
  public UrlRequest parse(String str) throws ParseError {
    requireNonNull(str);

    int offset = str.indexOf(QUESTION);

    String pathStr;
    String queryStr;
    if (offset < 0) {
      pathStr = str;
      queryStr = null;
    } else {
      pathStr = str.substring(0, offset);
      queryStr = str.substring(offset + 1);
    }

    UrlPath path = pathFormat.parse(pathStr);

    UrlQuery query = queryStr == null ? UrlQueryLib.query() : queryFormat.parse(queryStr);

    return UrlRequestLib.request(path, query);
  }

}
