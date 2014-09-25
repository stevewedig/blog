package com.stevewedig.blog.digraph.node_graph;

import java.util.Set;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.digraph.id_graph.IdDag;
import com.stevewedig.blog.digraph.node.DownNode;
import com.stevewedig.blog.digraph.node.DownNodeLib;
import com.stevewedig.blog.digraph.node.UpNode;
import com.stevewedig.blog.digraph.node.UpNodeLib;
import com.stevewedig.blog.util.SetLib;

/**
 * A library for creating Dags (digraphs containing nodes).
 */
public abstract class DagLib {

  // ===========================================================================
  // dag from state
  // ===========================================================================

  /**
   * Create a Dag from the nested IdDag (the arc structure) and the mapping between id and node.
   */
  public static <Id, Node> Dag<Id, Node> dag(IdDag<Id> idDag,
      ImmutableBiMap<Id, Node> id__node) {

    return new DagClass<>(idDag, id__node, false);
  }

  // ===========================================================================
  // dag from up nodes
  // ===========================================================================

  /**
   * Create a Dag from an UpNode set.
   */
  public static <Id, Node extends UpNode<Id>> Dag<Id, Node> up(ImmutableSet<Node> nodeSet) {

    IdDag<Id> idDag = UpNodeLib.nodes__idDag(nodeSet);

    ImmutableBiMap<Id, Node> id__node = UpNodeLib.nodes__nodeMap(nodeSet);

    return dag(idDag, id__node);
  }

  /**
   * Create a Dag from an UpNode iterable.
   */
  public static <Id, Node extends UpNode<Id>> Dag<Id, Node> up(Iterable<Node> nodeIterable) {
    return up(ImmutableSet.copyOf(nodeIterable));
  }

  /**
   * Create a Dag from an UpNode array.
   */
  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> Dag<Id, Node> up(Node... nodeArray) {
    return up(ImmutableSet.copyOf(nodeArray));
  }

  /**
   * Create a Dag from the union of multiple UpNode sets (remember that Dag&lt;Node&gt; extends
   * Set&lt;Node&gt;).
   */
  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> Dag<Id, Node> up(Set<Node>... dags) {
    return up(SetLib.union(dags));
  }

  // ===========================================================================
  // dag from down nodes
  // ===========================================================================

  /**
   * Create a Dag from an DownNode set.
   */
  public static <Id, Node extends DownNode<Id>> Dag<Id, Node> down(ImmutableSet<Node> nodeSet) {

    IdDag<Id> idDag = DownNodeLib.nodes__idDag(nodeSet);

    ImmutableBiMap<Id, Node> id__node = DownNodeLib.nodes__nodeMap(nodeSet);

    return dag(idDag, id__node);
  }

  /**
   * Create a Dag from an DownNode iterable.
   */
  public static <Id, Node extends DownNode<Id>> Dag<Id, Node> down(Iterable<Node> nodeIterable) {
    return down(ImmutableSet.copyOf(nodeIterable));
  }

  /**
   * Create a Dag from an DownNode array.
   */
  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> Dag<Id, Node> down(Node... nodeArray) {
    return down(ImmutableSet.copyOf(nodeArray));
  }

  /**
   * Create a Dag from the union of multiple DownNode sets (remember that Dag&lt;Node&gt;
   * extends Set&lt;Node&gt;).
   */
  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> Dag<Id, Node> down(Set<Node>... dags) {
    return down(SetLib.union(dags));
  }

}
