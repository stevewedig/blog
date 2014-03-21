package com.stevewedig.foundation.url.path_query;

import java.util.Map;

import com.stevewedig.foundation.url.path.Path;
import com.stevewedig.foundation.url.path.PathLib;
import com.stevewedig.foundation.url.query.Query;
import com.stevewedig.foundation.url.query.QueryLib;

public abstract class PathQueryLib {

  // ===========================================================================
  // path & query
  // ===========================================================================
  
  public static PathQuery pathQuery(Path path, Query query) {
    return new PathQueryClass(path, query);
  }

  public static PathQuery pathQuery(Iterable<String> pathParts, Query query) {
    return pathQuery(PathLib.path(pathParts), query);
  }
  
  public static PathQuery pathQuery(Path path, Map<String, String> params) {
    return pathQuery(path, QueryLib.query(params));
  }

  public static PathQuery pathQuery(Iterable<String> pathParts, Map<String, String> params) {
    return pathQuery(PathLib.path(pathParts), QueryLib.query(params));
  }

  // ===========================================================================
  // blank
  // ===========================================================================

  public static PathQuery pathQuery() {
    return pathQuery(PathLib.path(), QueryLib.query());
  }

  // ===========================================================================
  // just path
  // ===========================================================================

  public static PathQuery pathQuery(Path path) {
    return pathQuery(path, QueryLib.query());
  }

  public static PathQuery pathQuery(Iterable<String> pathParts) {
    return pathQuery(PathLib.path(pathParts), QueryLib.query());
  }

  // ===========================================================================
  // just query
  // ===========================================================================
  
  public static PathQuery pathQuery(Query query) {
    return pathQuery(PathLib.path(), query);
  }

  public static PathQuery pathQuery(Map<String, String> params) {
    return pathQuery(PathLib.path(), QueryLib.query(params));
  }
}
