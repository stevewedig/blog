package com.stevewedig.foundation.url.router;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableBiMap;
import com.stevewedig.foundation.url.query.Query;
import com.stevewedig.foundation.url.query.QueryLib;

public class PathRouterLib {

  // ===========================================================================
  // router
  // ===========================================================================

  public static <Location> PathRouter<Location> router(PathRouterNode<Location> root) {
    return new PathRouterClass<>(root);
  }

  public static <Location> PathRouter.Builder<Location> builder() {
    return new PathRouterBuilderClass<>();
  }

  // ===========================================================================
  // nodes
  // ===========================================================================

  public static <Location> PathRouterNode<Location> paramNode(Optional<Location> location,
      PathRouterNode<Location> child, String prefix, String key, String suffix) {
    return new PathRouterNodeParamClass<>(location, child, prefix, key, suffix);
  }

  public static <Location> PathRouterNode<Location> mapNode(Optional<Location> location,
      ImmutableBiMap<String, PathRouterNode<Location>> key__child) {
    return new PathRouterNodeMapClass<>(location, key__child);
  }

  // ===========================================================================
  // locationQuery
  // ===========================================================================

  public static <Location> LocationQuery<Location> locationQuery(Location location, Query query) {
    return new LocationQueryClass<>(location, query);
  }

  public static <Location> LocationQuery<Location> locationQuery(Location location,
      Map<String, String> params) {
    return locationQuery(location, QueryLib.query(params));
  }

}
