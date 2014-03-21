package com.stevewedig.foundation.url.encoder;

import com.stevewedig.foundation.url.path.Path;
import com.stevewedig.foundation.url.path_query.PathQuery;
import com.stevewedig.foundation.url.query.Query;

public interface UrlEncoder {

  // ===========================================================================
  // path
  // ===========================================================================

  String writePathPart(String part);

  String parsePathPart(String writtenPart);

  String writePath(Path path);

  Path parsePath(String str);

  // ===========================================================================
  // query
  // ===========================================================================

  String writeQueryPart(String part);

  String parseQueryPart(String writtenPart);

  String writeQuery(String... query);

  String writeQuery(Query query);

  Query parseQuery(String str);

  // ===========================================================================
  // path query
  // ===========================================================================

  String writePathQuery(PathQuery pathQuery);

  PathQuery parsePathQuery(String str);

}
