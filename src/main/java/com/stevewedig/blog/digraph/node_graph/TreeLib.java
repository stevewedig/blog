package com.stevewedig.blog.digraph.node_graph;

import java.util.Set;

import com.google.common.collect.*;
import com.stevewedig.blog.digraph.errors.GraphIsMissingNodes;
import com.stevewedig.blog.digraph.id_graph.IdTree;
import com.stevewedig.blog.digraph.node.*;
import com.stevewedig.blog.util.SetLib;

/**
 * A library for creating Trees.
 */
public abstract class TreeLib {

  // ===========================================================================
  // tree from state
  // ===========================================================================

  public static <Id, Node> Tree<Id, Node> tree(IdTree<Id> idTree, ImmutableBiMap<Id, Node> id__node) {

    Tree<Id, Node> tree = new TreeClass<>(idTree, id__node);

    if (!tree.unboundIdSet().isEmpty())
      throw new GraphIsMissingNodes("unbound ids = %s", tree.unboundIdSet());

    return tree;
  }

  // ===========================================================================
  // tree from up nodes
  // ===========================================================================

  public static <Id, Node extends UpNode<Id>> Tree<Id, Node> up(ImmutableSet<Node> nodeSet) {

    IdTree<Id> idTree = UpNodeLib.nodes__idTree(nodeSet);

    ImmutableBiMap<Id, Node> id__node = UpNodeLib.nodes__nodeMap(nodeSet);

    return tree(idTree, id__node);
  }

  public static <Id, Node extends UpNode<Id>> Tree<Id, Node> up(Iterable<Node> nodeIter) {
    return up(ImmutableSet.copyOf(nodeIter));
  }

  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> Tree<Id, Node> up(Node... nodeArray) {
    return up(ImmutableSet.copyOf(nodeArray));
  }

  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> Tree<Id, Node> up(Set<Node>... trees) {
    return up(SetLib.union(trees));
  }

  // ===========================================================================
  // tree from down nodes
  // ===========================================================================

  public static <Id, Node extends DownNode<Id>> Tree<Id, Node> down(ImmutableSet<Node> nodeSet) {

    IdTree<Id> idTree = DownNodeLib.nodes__idTree(nodeSet);

    ImmutableBiMap<Id, Node> id__node = DownNodeLib.nodes__nodeMap(nodeSet);

    return tree(idTree, id__node);
  }

  public static <Id, Node extends DownNode<Id>> Tree<Id, Node> down(Iterable<Node> nodeIter) {
    return down(ImmutableSet.copyOf(nodeIter));
  }

  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> Tree<Id, Node> down(Node... nodeArray) {
    return down(ImmutableSet.copyOf(nodeArray));
  }

  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> Tree<Id, Node> down(Set<Node>... trees) {
    return down(SetLib.union(trees));
  }

}
