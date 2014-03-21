package com.stevewedig.foundation.url.router;

import com.google.common.base.Optional;

public abstract class PathRouterNodeMixin<Location> implements PathRouterNode<Location> {

  private final Optional<Location> location;

  public PathRouterNodeMixin(Optional<Location> location) {
    super();
    this.location = location;
  }

  @Override
  public Optional<Location> location() {
    return location;
  }
}
