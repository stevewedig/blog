package com.stevewedig.blog.digraph.node_graph_partial;

import com.stevewedig.blog.digraph.id_graph.IdDag;

/**
 * A partial dag containing nodes.
 */
public interface PartialDag<Id, Node> extends PartialGraph<Id, Node>, IdDag<Id> {

  // ===========================================================================
  // exposing inner idDag
  // ===========================================================================

  /**
   * The internal id dag.
   */
  @Override
  IdDag<Id> idGraph();

}
