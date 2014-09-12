package com.stevewedig.blog.digraph.node_graph;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.stevewedig.blog.digraph.node_graph_partial.PartialTree;

/**
 * A tree containing nodes.
 */
public interface Tree<Id, Node> extends Dag<Id, Node>, PartialTree<Id, Node> {

  // ===========================================================================
  // root
  // ===========================================================================

  /**
   * The tree's root node.
   */
  Node rootNode();

  // ===========================================================================
  // parent
  // ===========================================================================

  /**
   * Getting an id's parent node, will be absent if the id is the root.
   */
  Optional<Node> parentNode(Id id);

  // ===========================================================================
  // ancestors
  // ===========================================================================
  
  /**
   * Getting an id's ancestor node list, with the root node first and the id's parent node last.
   */
  ImmutableList<Node> ancestorNodeList(Id id);

}
