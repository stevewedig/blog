package com.stevewedig.blog.digraph.node;

import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.value_objects.ValueMixin;

/**
 * An implementation of UpNode.
 */
public class UpNodeClass<Id> extends ValueMixin implements UpNode<Id> {

  // ===========================================================================
  // state
  // ===========================================================================

  private final Id id;
  private final ImmutableSet<Id> parentIds;

  @Override
  public Object[] fields() {
    return array("id", id, "parentIds", parentIds);
  }

  // ===========================================================================
  // constructor
  // ===========================================================================

  public UpNodeClass(Id id, ImmutableSet<Id> parentIds) {
    this.id = id;
    this.parentIds = parentIds;
  }

  // ===========================================================================
  // properties
  // ===========================================================================

  @Override
  public Id id() {
    return id;
  }

  @Override
  public ImmutableSet<Id> parentIds() {
    return parentIds;
  }

}
