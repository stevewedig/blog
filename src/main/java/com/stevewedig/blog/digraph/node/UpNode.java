package com.stevewedig.blog.digraph.node;

import com.google.common.collect.ImmutableSet;

public interface UpNode<Id> {

  Id id();

  ImmutableSet<Id> parentIds();
}
