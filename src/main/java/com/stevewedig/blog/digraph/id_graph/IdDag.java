package com.stevewedig.blog.digraph.id_graph;

import com.google.common.collect.ImmutableList;

public interface IdDag<Id> extends IdGraph<Id> {
  
  // ===========================================================================
  // topological sort
  // ===========================================================================

  /**
   * @return Topologically sorted list of ids.
   */
  ImmutableList<Id> topsortIdList();
  
  // ===========================================================================
  // depth first
  // ===========================================================================

  Iterable<Id> depthIdIterable();
  
  ImmutableList<Id> depthIdList();

  // ===========================================================================
  // breadth first
  // ===========================================================================

  Iterable<Id> breadthIdIterable();

  ImmutableList<Id> breadthIdList();

}
