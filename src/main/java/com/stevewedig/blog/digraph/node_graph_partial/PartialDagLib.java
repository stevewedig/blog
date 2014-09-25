package com.stevewedig.blog.digraph.node_graph_partial;

import java.util.Set;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.digraph.id_graph.IdDag;
import com.stevewedig.blog.digraph.node.DownNode;
import com.stevewedig.blog.digraph.node.DownNodeLib;
import com.stevewedig.blog.digraph.node.UpNode;
import com.stevewedig.blog.digraph.node.UpNodeLib;
import com.stevewedig.blog.digraph.node_graph.DagClass;
import com.stevewedig.blog.util.SetLib;

/**
 * A library for creating Dags (digraphs containing nodes).
 */
public abstract class PartialDagLib {

  // ===========================================================================
  // dag from state
  // ===========================================================================

  /**
   * Create a PartialDag from the nested IdDag (the arc structure) and the mapping between id and node.
   */
  public static <Id, Node> PartialDag<Id, Node> dag(IdDag<Id> idDag,
      ImmutableBiMap<Id, Node> id__node) {

    return new DagClass<>(idDag, id__node, true);
  }

  // ===========================================================================
  // dag from up nodes
  // ===========================================================================

  /**
   * Create a PartialDag from an UpNode set.
   */
  public static <Id, Node extends UpNode<Id>> PartialDag<Id, Node> up(ImmutableSet<Node> nodeSet) {

    IdDag<Id> idDag = UpNodeLib.nodes__idDag(nodeSet);

    ImmutableBiMap<Id, Node> id__node = UpNodeLib.nodes__nodeMap(nodeSet);

    return dag(idDag, id__node);
  }

  /**
   * Create a PartialDag from an UpNode iterable.
   */
  public static <Id, Node extends UpNode<Id>> PartialDag<Id, Node> up(Iterable<Node> nodeIterable) {
    return up(ImmutableSet.copyOf(nodeIterable));
  }

  /**
   * Create a PartialDag from an UpNode array.
   */
  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> PartialDag<Id, Node> up(Node... nodeArray) {
    return up(ImmutableSet.copyOf(nodeArray));
  }

  /**
   * Create a PartialDag from the union of multiple UpNode sets (remember that Dag&lt;Node&gt; extends
   * Set&lt;Node&gt;).
   */
  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> PartialDag<Id, Node> up(Set<Node>... dags) {
    return up(SetLib.union(dags));
  }

  // ===========================================================================
  // dag from down nodes
  // ===========================================================================

  /**
   * Create a PartialDag from an DownNode set.
   */
  public static <Id, Node extends DownNode<Id>> PartialDag<Id, Node> down(ImmutableSet<Node> nodeSet) {

    IdDag<Id> idDag = DownNodeLib.nodes__idDag(nodeSet);

    ImmutableBiMap<Id, Node> id__node = DownNodeLib.nodes__nodeMap(nodeSet);

    return dag(idDag, id__node);
  }

  /**
   * Create a PartialDag from an DownNode iterable.
   */
  public static <Id, Node extends DownNode<Id>> PartialDag<Id, Node> down(Iterable<Node> nodeIterable) {
    return down(ImmutableSet.copyOf(nodeIterable));
  }

  /**
   * Create a PartialDag from an DownNode array.
   */
  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> PartialDag<Id, Node> down(Node... nodeArray) {
    return down(ImmutableSet.copyOf(nodeArray));
  }

  /**
   * Create a PartialDag from the union of multiple DownNode sets (remember that Dag&lt;Node&gt;
   * extends Set&lt;Node&gt;).
   */
  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> PartialDag<Id, Node> down(Set<Node>... dags) {
    return down(SetLib.union(dags));
  }

}
