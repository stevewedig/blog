package com.stevewedig.blog.digraph.node;


/**
 * A node that knows its own id.
 */
public interface BaseNode<Id> {

  /**
   * The node's id.
   */
  Id id();
}
