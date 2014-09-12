package com.stevewedig.blog.digraph.node;

import com.google.common.collect.ImmutableSet;

/**
 * A digraph node that knows its own id and its child ids.
 */
public interface DownNode<Id> extends BaseNode<Id> {

  ImmutableSet<Id> childIds();
}
