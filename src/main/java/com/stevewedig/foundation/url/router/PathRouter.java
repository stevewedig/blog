package com.stevewedig.foundation.url.router;

import com.stevewedig.foundation.url.path_query.PathQuery;

public interface PathRouter<Location> {

  LocationQuery<Location> parse(PathQuery pathQuery) throws CannotParsePathQuery;

  PathQuery write(LocationQuery<Location> locationQuery) throws CannotWriteLocationQuery;

  // ===========================================================================
  
  public interface Builder<Location> {

    PathRouter<Location> build();
    
    Builder<Location> put(String pathStr, Location location);
    Builder<Location> put(String pathStr, PathRouter<Location> subRouter);

  }
}
