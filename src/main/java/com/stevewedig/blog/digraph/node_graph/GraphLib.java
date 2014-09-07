package com.stevewedig.blog.digraph.node_graph;

import java.util.Set;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.digraph.id_graph.IdGraph;
import com.stevewedig.blog.digraph.node.DownNode;
import com.stevewedig.blog.digraph.node.DownNodeLib;
import com.stevewedig.blog.digraph.node.UpNode;
import com.stevewedig.blog.digraph.node.UpNodeLib;
import com.stevewedig.blog.util.SetLib;

public abstract class GraphLib {

  // ===========================================================================
  // graph from state
  // ===========================================================================

  public static <Id, Node> Graph<Id, Node> graph(IdGraph<Id> idGraph,
      ImmutableBiMap<Id, Node> id__node) {

    return new GraphClass<>(idGraph, id__node);
  }

  // ===========================================================================
  // graph from up nodes
  // ===========================================================================

  public static <Id, Node extends UpNode<Id>> Graph<Id, Node> up(ImmutableSet<Node> nodeSet) {

    IdGraph<Id> idGraph = UpNodeLib.nodes__idGraph(nodeSet);

    ImmutableBiMap<Id, Node> id__node = UpNodeLib.nodes__nodeMap(nodeSet);

    return graph(idGraph, id__node);
  }

  public static <Id, Node extends UpNode<Id>> Graph<Id, Node> up(Iterable<Node> nodeIter) {
    return up(ImmutableSet.copyOf(nodeIter));
  }

  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> Graph<Id, Node> up(Node... nodeArray) {
    return up(ImmutableSet.copyOf(nodeArray));
  }

  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> Graph<Id, Node> up(Set<Node>... graphs) {
    return up(SetLib.union(graphs));
  }

  // ===========================================================================
  // graph from down nodes
  // ===========================================================================

  public static <Id, Node extends DownNode<Id>> Graph<Id, Node> down(ImmutableSet<Node> nodeSet) {

    IdGraph<Id> idGraph = DownNodeLib.nodes__idGraph(nodeSet);

    ImmutableBiMap<Id, Node> id__node = DownNodeLib.nodes__nodeMap(nodeSet);

    return graph(idGraph, id__node);
  }

  public static <Id, Node extends DownNode<Id>> Graph<Id, Node> down(Iterable<Node> nodeIter) {
    return down(ImmutableSet.copyOf(nodeIter));
  }

  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> Graph<Id, Node> down(Node... nodeArray) {
    return down(ImmutableSet.copyOf(nodeArray));
  }

  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> Graph<Id, Node> down(Set<Node>... graphs) {
    return down(SetLib.union(graphs));
  }

}
