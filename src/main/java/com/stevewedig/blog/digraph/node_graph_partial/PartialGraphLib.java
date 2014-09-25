package com.stevewedig.blog.digraph.node_graph_partial;

import java.util.Set;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.digraph.id_graph.IdGraph;
import com.stevewedig.blog.digraph.node.DownNode;
import com.stevewedig.blog.digraph.node.DownNodeLib;
import com.stevewedig.blog.digraph.node.UpNode;
import com.stevewedig.blog.digraph.node.UpNodeLib;
import com.stevewedig.blog.digraph.node_graph.GraphClass;
import com.stevewedig.blog.util.SetLib;

/**
 * A library for creating Graphs (digraphs containing nodes).
 */
public abstract class PartialGraphLib {

  // ===========================================================================
  // graph from state
  // ===========================================================================

  /**
   * Create a PartialGraph from the nested IdGraph (the arc structure) and the mapping between id and node.
   */
  public static <Id, Node> PartialGraph<Id, Node> graph(IdGraph<Id> idGraph,
      ImmutableBiMap<Id, Node> id__node) {

    return new GraphClass<>(idGraph, id__node, true);
  }

  // ===========================================================================
  // graph from up nodes
  // ===========================================================================

  /**
   * Create a PartialGraph from an UpNode set.
   */
  public static <Id, Node extends UpNode<Id>> PartialGraph<Id, Node> up(ImmutableSet<Node> nodeSet) {

    IdGraph<Id> idGraph = UpNodeLib.nodes__idGraph(nodeSet);

    ImmutableBiMap<Id, Node> id__node = UpNodeLib.nodes__nodeMap(nodeSet);

    return graph(idGraph, id__node);
  }

  /**
   * Create a PartialGraph from an UpNode iterable.
   */
  public static <Id, Node extends UpNode<Id>> PartialGraph<Id, Node> up(Iterable<Node> nodeIterable) {
    return up(ImmutableSet.copyOf(nodeIterable));
  }

  /**
   * Create a PartialGraph from an UpNode array.
   */
  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> PartialGraph<Id, Node> up(Node... nodeArray) {
    return up(ImmutableSet.copyOf(nodeArray));
  }

  /**
   * Create a PartialGraph from the union of multiple UpNode sets (remember that Graph&lt;Node&gt; extends
   * Set&lt;Node&gt;).
   */
  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> PartialGraph<Id, Node> up(Set<Node>... graphs) {
    return up(SetLib.union(graphs));
  }

  // ===========================================================================
  // graph from down nodes
  // ===========================================================================

  /**
   * Create a PartialGraph from an DownNode set.
   */
  public static <Id, Node extends DownNode<Id>> PartialGraph<Id, Node> down(ImmutableSet<Node> nodeSet) {

    IdGraph<Id> idGraph = DownNodeLib.nodes__idGraph(nodeSet);

    ImmutableBiMap<Id, Node> id__node = DownNodeLib.nodes__nodeMap(nodeSet);

    return graph(idGraph, id__node);
  }

  /**
   * Create a PartialGraph from an DownNode iterable.
   */
  public static <Id, Node extends DownNode<Id>> PartialGraph<Id, Node> down(Iterable<Node> nodeIterable) {
    return down(ImmutableSet.copyOf(nodeIterable));
  }

  /**
   * Create a PartialGraph from an DownNode array.
   */
  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> PartialGraph<Id, Node> down(Node... nodeArray) {
    return down(ImmutableSet.copyOf(nodeArray));
  }

  /**
   * Create a PartialGraph from the union of multiple DownNode sets (remember that Graph&lt;Node&gt;
   * extends Set&lt;Node&gt;).
   */
  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> PartialGraph<Id, Node> down(Set<Node>... graphs) {
    return down(SetLib.union(graphs));
  }

}
