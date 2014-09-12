package com.stevewedig.blog.digraph.node_graph_partial;

import com.stevewedig.blog.digraph.id_graph.IdTree;

/**
 * A partial tree containing nodes.
 */
public interface PartialTree<Id, Node> extends PartialDag<Id, Node>, IdTree<Id> {

  // ===========================================================================
  // exposing inner idTree
  // ===========================================================================

  /**
   * The internal id tree.
   */
  @Override
  IdTree<Id> idGraph();


}
