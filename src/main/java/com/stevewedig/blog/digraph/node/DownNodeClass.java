package com.stevewedig.blog.digraph.node;

import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.value_objects.ValueMixin;

public class DownNodeClass<Id> extends ValueMixin implements DownNode<Id> {

  // ===========================================================================
  // state
  // ===========================================================================

  private final Id id;
  private final ImmutableSet<Id> childIds;

  @Override
  public Object[] fields() {
    return array("id", id, "childIds", childIds);
  }

  // ===========================================================================
  // constructor
  // ===========================================================================

  public DownNodeClass(Id id, ImmutableSet<Id> childIds) {
    this.id = id;
    this.childIds = childIds;
  }

  // ===========================================================================
  // properties
  // ===========================================================================

  @Override
  public Id id() {
    return id;
  }

  @Override
  public ImmutableSet<Id> childIds() {
    return childIds;
  }

}
