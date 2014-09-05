package com.stevewedig.blog.digraph.node;

import com.google.common.collect.ImmutableSet;

public interface DownNode<Id> {

  Id id();

  ImmutableSet<Id> childIds();
}
