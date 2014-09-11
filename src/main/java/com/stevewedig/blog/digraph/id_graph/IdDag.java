package com.stevewedig.blog.digraph.id_graph;

import com.google.common.collect.ImmutableList;

/**
 * A dag containing ids.
 */
public interface IdDag<Id> extends IdGraph<Id> {
  
  // ===========================================================================
  // topological sort
  // ===========================================================================

  /**
   * A topologically sorted list of ids, with roots (sources) first.
   */
  ImmutableList<Id> topsortIdList();
  
  // ===========================================================================
  // depth first
  // ===========================================================================

  /**
   * A depth first id iterable.
   */
  Iterable<Id> depthIdIterable();
  
  /**
   * A depth first id list.
   */
  ImmutableList<Id> depthIdList();

  // ===========================================================================
  // breadth first
  // ===========================================================================

  /**
   * A breadth first id iterable.
   */
  Iterable<Id> breadthIdIterable();

  /**
   * A breadth first id list.
   */
  ImmutableList<Id> breadthIdList();

}
