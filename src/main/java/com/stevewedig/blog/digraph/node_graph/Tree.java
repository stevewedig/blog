package com.stevewedig.blog.digraph.node_graph;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.stevewedig.blog.digraph.id_graph.IdTree;

/**
 * A tree containing nodes.
 */
public interface Tree<Id, Node> extends Dag<Id, Node>, IdTree<Id> {

  // ===========================================================================
  // exposing inner idTree
  // ===========================================================================

  @Override
  IdTree<Id> idGraph();

  // ===========================================================================
  // root
  // ===========================================================================

  Node rootNode();

  // ===========================================================================
  // parent
  // ===========================================================================

  Optional<Node> parentNode(Id id);

  // ===========================================================================
  // ancestors
  // ===========================================================================

  ImmutableList<Node> ancestorNodeList(Id id);

}
