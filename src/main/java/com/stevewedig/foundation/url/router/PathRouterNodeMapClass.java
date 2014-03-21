package com.stevewedig.foundation.url.router;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;

class PathRouterNodeMapClass<Location> extends PathRouterNodeMixin<Location> implements
    PathRouterNode<Location> {

  private ImmutableBiMap<String, PathRouterNode<Location>> key__child;

  public PathRouterNodeMapClass(Optional<Location> location,
      ImmutableBiMap<String, PathRouterNode<Location>> key__child) {
    super(location);
    this.key__child = key__child;
  }

  @Override
  public ImmutableSet<PathRouterNode<Location>> children() {
    return key__child.values();
  }
  
  // TODO tostr

  // ===========================================================================
  // parse
  // ===========================================================================

  @Override
  public PathRouterNode<Location> parse(String pathPart, Map<String, String> params)
      throws CannotParsePathQuery {

    if (!key__child.containsKey(pathPart))
      throw new CannotParsePathQuery();

    return key__child.get(pathPart);
  }

  // ===========================================================================
  // write
  // ===========================================================================

  @Override
  public String write(PathRouterNode<Location> child, Map<String, String> params) throws CannotWriteLocationQuery {
    if(! key__child.inverse().containsKey(child))
      throw new RuntimeException("unexpected child: " + child);
    
    String key = key__child.inverse().get(child);
    
    return key;
  }
}
