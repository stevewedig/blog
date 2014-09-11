package com.stevewedig.blog.digraph.node_graph;

import com.google.common.collect.ImmutableList;
import com.stevewedig.blog.digraph.id_graph.IdDag;

/**
 * A dag containing nodes.
 */
public interface Dag<Id, Node> extends Graph<Id, Node>, IdDag<Id> {

  // ===========================================================================
  // exposing inner idDag
  // ===========================================================================

  /**
   * The internal id dag.
   */
  @Override
  IdDag<Id> idGraph();

  // ===========================================================================
  // topological sort
  // ===========================================================================

  /**
   * A topologically sorted list of nodes, with roots (sources) first.
   */
  ImmutableList<Node> topsortNodeList();

  // ===========================================================================
  // depth first
  // ===========================================================================

  /**
   * A depth first node iterable.
   */
  Iterable<Node> depthNodeIterable();

  /**
   * A depth first node list.
   */
  ImmutableList<Node> depthNodeList();

  // ===========================================================================
  // breadth first
  // ===========================================================================

  /**
   * A breadth first node iterable.
   */
  Iterable<Node> breadthNodeIterable();

  /**
   * A breadth first node list.
   */
  ImmutableList<Node> breadthNodeList();

}
