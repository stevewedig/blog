package com.stevewedig.foundation.url.router;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.stevewedig.foundation.url.path.Path;
import com.stevewedig.foundation.url.path_query.PathQuery;
import com.stevewedig.foundation.url.path_query.PathQueryLib;
import com.stevewedig.foundation.value_object.ValueMixin;
import com.stevewedig.foundation.value_object.ValueObjectHelper;

class PathRouterClass<Location> extends ValueMixin implements PathRouter<Location> {
  private final PathRouterNode<Location> root;

  public PathRouterClass(PathRouterNode<Location> root) {
    super();
    this.root = root;
  }

  @Override
  public ValueObjectHelper objectHelper() {
    return objectHelper();
  }

  private ImmutableList<PathRouterNode<Location>> location__nodePath(Location location) {
    // TODO Auto-generated method stub
    return null;
  }

  // ===========================================================================
  // parse
  // ===========================================================================

  @Override
  public LocationQuery<Location> parse(PathQuery pathQuery) throws CannotParsePathQuery {

    Map<String, String> params = Maps.newHashMap(pathQuery.query().params());

    PathRouterNode<Location> node = root;
    Path path = pathQuery.path();

    while (!path.isEmpty()) {
      node = node.parse(path.head(), params);
      path = path.tail();
    }

    if (!node.location().isPresent())
      throw new CannotParsePathQuery();

    Location location = node.location().get();

    return PathRouterLib.locationQuery(location, params);
  }

  // ===========================================================================
  // write
  // ===========================================================================

  @Override
  public PathQuery write(LocationQuery<Location> locationQuery) throws CannotWriteLocationQuery {

    Location location = locationQuery.location();

    ImmutableList<PathRouterNode<Location>> nodePath = location__nodePath(location);

    List<String> pathParts = new ArrayList<>();
    Map<String, String> params = Maps.newHashMap(locationQuery.query().params());

    PathRouterNode<Location> previousNode = null;
    for (PathRouterNode<Location> node : Lists.reverse(nodePath)) {

      if (previousNode == null) {
        previousNode = node;
        continue;
      }

      String pathPart = node.write(previousNode, params);
      pathParts.add(0, pathPart);
    }

    return PathQueryLib.pathQuery(pathParts, params);
  }
}
