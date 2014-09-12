package com.stevewedig.blog.digraph.node_graph_partial;

import com.stevewedig.blog.digraph.id_graph.IdTree;
import com.stevewedig.blog.digraph.node_graph.Dag;

/**
 * A tree containing nodes.
 */
public interface PartialTree<Id, Node> extends Dag<Id, Node>, IdTree<Id> {

  // ===========================================================================
  // exposing inner idTree
  // ===========================================================================

  /**
   * The internal id tree.
   */
  @Override
  IdTree<Id> idGraph();


}
