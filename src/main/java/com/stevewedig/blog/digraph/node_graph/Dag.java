package com.stevewedig.blog.digraph.node_graph;

import com.google.common.collect.ImmutableList;
import com.stevewedig.blog.digraph.id_graph.IdDag;

public interface Dag<Id, Node> extends Graph<Id, Node>, IdDag<Id> {

  // ===========================================================================
  // exposing inner idDag
  // ===========================================================================

  @Override
  IdDag<Id> idGraph();

  // ===========================================================================
  // topological sort
  // ===========================================================================

  /**
   * @return Topologically sorted list of nodes.
   */
  ImmutableList<Node> topsortNodeList();

  // ===========================================================================
  // depth first
  // ===========================================================================

  Iterable<Node> depthNodeIterable();

  ImmutableList<Node> depthNodeList();

  // ===========================================================================
  // breadth first
  // ===========================================================================

  Iterable<Node> breadthNodeIterable();

  ImmutableList<Node> breadthNodeList();

}
