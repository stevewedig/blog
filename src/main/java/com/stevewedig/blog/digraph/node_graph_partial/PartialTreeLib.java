package com.stevewedig.blog.digraph.node_graph_partial;

import java.util.Set;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.digraph.id_graph.IdTree;
import com.stevewedig.blog.digraph.node.DownNode;
import com.stevewedig.blog.digraph.node.DownNodeLib;
import com.stevewedig.blog.digraph.node.UpNode;
import com.stevewedig.blog.digraph.node.UpNodeLib;
import com.stevewedig.blog.digraph.node_graph.TreeClass;
import com.stevewedig.blog.util.SetLib;

/**
 * A library for creating Trees (digraphs containing nodes).
 */
public abstract class PartialTreeLib {

  // ===========================================================================
  // tree from state
  // ===========================================================================

  /**
   * Create a PartialTree from the nested IdTree (the arc structure) and the mapping between id and node.
   */
  public static <Id, Node> PartialTree<Id, Node> tree(IdTree<Id> idTree,
      ImmutableBiMap<Id, Node> id__node) {

    return new TreeClass<>(idTree, id__node, true);
  }

  // ===========================================================================
  // tree from up nodes
  // ===========================================================================

  /**
   * Create a PartialTree from an UpNode set.
   */
  public static <Id, Node extends UpNode<Id>> PartialTree<Id, Node> up(ImmutableSet<Node> nodeSet) {

    IdTree<Id> idTree = UpNodeLib.nodes__idTree(nodeSet);

    ImmutableBiMap<Id, Node> id__node = UpNodeLib.nodes__nodeMap(nodeSet);

    return tree(idTree, id__node);
  }

  /**
   * Create a PartialTree from an UpNode iterable.
   */
  public static <Id, Node extends UpNode<Id>> PartialTree<Id, Node> up(Iterable<Node> nodeIterable) {
    return up(ImmutableSet.copyOf(nodeIterable));
  }

  /**
   * Create a PartialTree from an UpNode array.
   */
  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> PartialTree<Id, Node> up(Node... nodeArray) {
    return up(ImmutableSet.copyOf(nodeArray));
  }

  /**
   * Create a PartialTree from the union of multiple UpNode sets (remember that Tree&lt;Node&gt; extends
   * Set&lt;Node&gt;).
   */
  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> PartialTree<Id, Node> up(Set<Node>... trees) {
    return up(SetLib.union(trees));
  }

  // ===========================================================================
  // tree from down nodes
  // ===========================================================================

  /**
   * Create a PartialTree from an DownNode set.
   */
  public static <Id, Node extends DownNode<Id>> PartialTree<Id, Node> down(ImmutableSet<Node> nodeSet) {

    IdTree<Id> idTree = DownNodeLib.nodes__idTree(nodeSet);

    ImmutableBiMap<Id, Node> id__node = DownNodeLib.nodes__nodeMap(nodeSet);

    return tree(idTree, id__node);
  }

  /**
   * Create a PartialTree from an DownNode iterable.
   */
  public static <Id, Node extends DownNode<Id>> PartialTree<Id, Node> down(Iterable<Node> nodeIterable) {
    return down(ImmutableSet.copyOf(nodeIterable));
  }

  /**
   * Create a PartialTree from an DownNode array.
   */
  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> PartialTree<Id, Node> down(Node... nodeArray) {
    return down(ImmutableSet.copyOf(nodeArray));
  }

  /**
   * Create a PartialTree from the union of multiple DownNode sets (remember that Tree&lt;Node&gt;
   * extends Set&lt;Node&gt;).
   */
  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> PartialTree<Id, Node> down(Set<Node>... trees) {
    return down(SetLib.union(trees));
  }

}
