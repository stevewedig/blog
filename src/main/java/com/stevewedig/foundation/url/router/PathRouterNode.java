package com.stevewedig.foundation.url.router;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

public interface PathRouterNode<Location> {

  Optional<Location> location();

  ImmutableSet<PathRouterNode<Location>> children();
  
  PathRouterNode<Location> parse(String pathPart, Map<String, String> params)
      throws CannotParsePathQuery;

  String write(PathRouterNode<Location> child, Map<String, String> params) throws CannotWriteLocationQuery;
}

