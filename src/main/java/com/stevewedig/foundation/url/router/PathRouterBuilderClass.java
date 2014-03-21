package com.stevewedig.foundation.url.router;

import com.google.common.base.Optional;
import com.stevewedig.foundation.etc.StrLib;
import com.stevewedig.foundation.url.path.Path;
import com.stevewedig.foundation.url.path.PathLib;
import com.stevewedig.foundation.url.router.PathRouter.Builder;

public class PathRouterBuilderClass<Location> implements PathRouter.Builder<Location> {

  private static char OPEN_DELIM = '{';
  private static char CLOSE_DELIM = '}';

  // ===========================================================================
  // build
  // ===========================================================================
  
  @Override
  public PathRouter<Location> build() {
    // TODO Auto-generated method stub
    return null;
  }

  // ===========================================================================
  // put
  // ===========================================================================

  @Override
  public Builder<Location> put(String pathStr, Location location) {
    
    if(StrLib.startsWith(pathStr, "/") || pathStr.endsWith("/"))
      throw new RuntimeException("PathRouter paths shouldn't start or end with /");
    
    Path path = PathLib.split(pathStr, '/');
   
    put(path, location);
    
    return this;
  }

  @Override
  public Builder<Location> put(String path, PathRouter<Location> subRouter) {

    return this;
  }
  
  private void put(Path path, Location location) {
    
  }

  // ===========================================================================
  // node
  // ===========================================================================

  private PathRouterNode<Location> node(Optional<Location> location, String pathPart) {

    boolean isParam = StrLib.containsInterpolation(pathPart, OPEN_DELIM, CLOSE_DELIM);

    // if (isParam)
    // return paramNode(location, pathPart);
    // else
    // return mapNode(location, pathPart);

    return null;
  }

  // ===========================================================================
  // map node
  // ===========================================================================

  private PathRouterNode<Location> mapNode(Optional<Location> location, String pathPart) {

    return null;
  }

  // ===========================================================================
  // param node
  // ===========================================================================

  private PathRouterNode<Location> paramNode(Optional<Location> location,
      PathRouterNode<Location> child, String pathPart) {

    String[] triple = StrLib.splitInterpolation(pathPart, OPEN_DELIM, CLOSE_DELIM);
    String prefix = triple[0];
    String key = triple[1];
    String suffix = triple[2];

    return PathRouterLib.paramNode(location, child, prefix, key, suffix);
  }

}
