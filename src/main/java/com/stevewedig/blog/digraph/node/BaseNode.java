package com.stevewedig.blog.digraph.node;


/**
 * A digraph node that knows its own id.
 */
public interface BaseNode<Id> {

  Id id();
}
