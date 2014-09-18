package com.stevewedig.blog.digraph.id_graph;

import java.util.Set;

import com.google.common.collect.ImmutableList;

/**
 * A dag containing ids.
 */
public interface IdDag<Id> extends IdGraph<Id> {

  // ===========================================================================
  // ids
  // ===========================================================================

  @Override
  IdDag<Id> filterIdGraph(Set<Id> ids);

  // ===========================================================================
  // ancestors
  // ===========================================================================

  @Override
  IdDag<Id> ancestorIdGraph(Id id, boolean inclusive);

  @Override
  IdDag<Id> ancestorIdGraph(Set<Id> ids, boolean inclusive);

  // ===========================================================================
  // descendants
  // ===========================================================================

  @Override
  IdDag<Id> descendantIdGraph(Id id, boolean inclusive);

  @Override
  IdDag<Id> descendantIdGraph(Set<Id> ids, boolean inclusive);

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
