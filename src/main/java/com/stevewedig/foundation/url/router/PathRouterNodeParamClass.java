package com.stevewedig.foundation.url.router;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

class PathRouterNodeParamClass<Location> extends PathRouterNodeMixin<Location> implements
    PathRouterNode<Location> {

  // fields
  private String prefix;
  private String suffix;
  private String key;
  private PathRouterNode<Location> child;

  // derived
  private ImmutableSet<PathRouterNode<Location>> children;
  
  public PathRouterNodeParamClass(Optional<Location> location, PathRouterNode<Location> child, String prefix, String key, String suffix) {
    super(location);
    this.child = child;
    this.prefix = prefix;
    this.key = key;
    this.suffix = suffix;
    
    this.children = ImmutableSet.of(child);
  }

  // TODO tostring

  // TODO pull into strlib
  
  @Override
  public ImmutableSet<PathRouterNode<Location>> children() {
    return children;
  }

  // ===========================================================================
  // parse
  // ===========================================================================
  
  @Override
  public PathRouterNode<Location> parse(String pathPart, Map<String, String> params)
      throws CannotParsePathQuery {

    if (params.containsKey(key))
      throw new PathRouterLocationConflict(key);

    if (!pathPart.startsWith(prefix))
      throw new CannotParsePathQuery();

    if (!pathPart.endsWith(suffix))
      throw new CannotParsePathQuery();

    String value = pathPart;
    value = value.substring(prefix.length());
    value = value.substring(0, value.length() - suffix.length());

    params.put(key, value);
    
    return child;
  }

  // ===========================================================================
  // write
  // ===========================================================================
  
  @Override
  public String write(PathRouterNode<Location> child, Map<String, String> params) throws CannotWriteLocationQuery {
    if(! child.equals(this.child))
      throw new RuntimeException("param node got unexpected child node");
    
    if(! params.containsKey(key))
      throw new CannotWriteLocationQuery();
    
    String value = params.get(key);
    String pathPart = prefix + value + suffix;
    
    params.remove(key);
    
    return pathPart;   
  }

}
