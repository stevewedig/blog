package com.stevewedig.blog.digraph.node;

import com.google.common.collect.ImmutableSet;

/**
 * A digraph node that knows its own id and its parent ids.
 */
public interface UpNode<Id> extends BaseNode<Id> {


  /**
   * The node's parent ids.
   */
  ImmutableSet<Id> parentIds();
}
