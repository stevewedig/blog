package com.stevewedig.blog.digraph.node_graph;

import java.util.Set;

import com.google.common.collect.*;
import com.stevewedig.blog.digraph.id_graph.IdGraph;
import com.stevewedig.blog.digraph.node.*;
import com.stevewedig.blog.util.SetLib;

/**
 * A library for creating Graphs (digraphs containing nodes).
 */
public abstract class GraphLib {

  // ===========================================================================
  // graph from state
  // ===========================================================================

  /**
   * Create a Graph the nested IdGraph (the arc structure) and the mapping between id and node.
   */
  public static <Id, Node> Graph<Id, Node> graph(IdGraph<Id> idGraph,
      ImmutableBiMap<Id, Node> id__node) {

    return new GraphClass<>(idGraph, id__node, false);
  }

  // ===========================================================================
  // graph from up nodes
  // ===========================================================================

  /**
   * Create a Graph from an UpNode set.
   */
  public static <Id, Node extends UpNode<Id>> Graph<Id, Node> up(ImmutableSet<Node> nodeSet) {

    IdGraph<Id> idGraph = UpNodeLib.nodes__idGraph(nodeSet);

    ImmutableBiMap<Id, Node> id__node = UpNodeLib.nodes__nodeMap(nodeSet);

    return graph(idGraph, id__node);
  }

  /**
   * Create a Graph from an UpNode iterable.
   */
  public static <Id, Node extends UpNode<Id>> Graph<Id, Node> up(Iterable<Node> nodeIterable) {
    return up(ImmutableSet.copyOf(nodeIterable));
  }

  /**
   * Create a Graph from an UpNode array.
   */
  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> Graph<Id, Node> up(Node... nodeArray) {
    return up(ImmutableSet.copyOf(nodeArray));
  }

  /**
   * Create a Graph from the union of multiple UpNode sets (remember that Graph&lt;Node&gt; extends
   * Set&lt;Node&gt;).
   */
  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> Graph<Id, Node> up(Set<Node>... graphs) {
    return up(SetLib.union(graphs));
  }

  // ===========================================================================
  // graph from down nodes
  // ===========================================================================

  /**
   * Create a Graph from an DownNode set.
   */
  public static <Id, Node extends DownNode<Id>> Graph<Id, Node> down(ImmutableSet<Node> nodeSet) {

    IdGraph<Id> idGraph = DownNodeLib.nodes__idGraph(nodeSet);

    ImmutableBiMap<Id, Node> id__node = DownNodeLib.nodes__nodeMap(nodeSet);

    return graph(idGraph, id__node);
  }

  /**
   * Create a Graph from an DownNode iterable.
   */
  public static <Id, Node extends DownNode<Id>> Graph<Id, Node> down(Iterable<Node> nodeIterable) {
    return down(ImmutableSet.copyOf(nodeIterable));
  }

  /**
   * Create a Graph from an DownNode array.
   */
  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> Graph<Id, Node> down(Node... nodeArray) {
    return down(ImmutableSet.copyOf(nodeArray));
  }

  /**
   * Create a Graph from the union of multiple DownNode sets (remember that Graph&lt;Node&gt;
   * extends Set&lt;Node&gt;).
   */
  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> Graph<Id, Node> down(Set<Node>... graphs) {
    return down(SetLib.union(graphs));
  }

}
