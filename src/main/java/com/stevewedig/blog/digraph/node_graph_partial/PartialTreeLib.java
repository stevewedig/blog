package com.stevewedig.blog.digraph.node_graph_partial;

import java.util.Set;

import com.google.common.collect.*;
import com.stevewedig.blog.digraph.id_graph.IdTree;
import com.stevewedig.blog.digraph.node.*;
import com.stevewedig.blog.digraph.node_graph.TreeClass;
import com.stevewedig.blog.util.SetLib;

/**
 * A library for creating PartialTrees.
 */
public abstract class PartialTreeLib {

  // ===========================================================================
  // tree from state
  // ===========================================================================

  public static <Id, Node> PartialTree<Id, Node> tree(IdTree<Id> idTree,
      ImmutableBiMap<Id, Node> id__node) {

    return new TreeClass<>(idTree, id__node, true);
  }

  // ===========================================================================
  // tree from up nodes
  // ===========================================================================

  public static <Id, Node extends UpNode<Id>> PartialTree<Id, Node> up(ImmutableSet<Node> nodeSet) {

    IdTree<Id> idTree = UpNodeLib.nodes__idTree(nodeSet);

    ImmutableBiMap<Id, Node> id__node = UpNodeLib.nodes__nodeMap(nodeSet);

    return tree(idTree, id__node);
  }

  public static <Id, Node extends UpNode<Id>> PartialTree<Id, Node> up(Iterable<Node> nodeIter) {
    return up(ImmutableSet.copyOf(nodeIter));
  }

  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> PartialTree<Id, Node> up(Node... nodeArray) {
    return up(ImmutableSet.copyOf(nodeArray));
  }

  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> PartialTree<Id, Node> up(Set<Node>... trees) {
    return up(SetLib.union(trees));
  }

  // ===========================================================================
  // tree from down nodes
  // ===========================================================================

  public static <Id, Node extends DownNode<Id>> PartialTree<Id, Node> down(
      ImmutableSet<Node> nodeSet) {

    IdTree<Id> idTree = DownNodeLib.nodes__idTree(nodeSet);

    ImmutableBiMap<Id, Node> id__node = DownNodeLib.nodes__nodeMap(nodeSet);

    return tree(idTree, id__node);
  }

  public static <Id, Node extends DownNode<Id>> PartialTree<Id, Node> down(Iterable<Node> nodeIter) {
    return down(ImmutableSet.copyOf(nodeIter));
  }

  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> PartialTree<Id, Node> down(Node... nodeArray) {
    return down(ImmutableSet.copyOf(nodeArray));
  }

  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> PartialTree<Id, Node> down(Set<Node>... trees) {
    return down(SetLib.union(trees));
  }

}
