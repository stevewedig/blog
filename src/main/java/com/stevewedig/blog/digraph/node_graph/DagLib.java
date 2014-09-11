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
 * A library for creating Dags.
 */
public abstract class DagLib {

  // ===========================================================================
  // dag from state
  // ===========================================================================

  public static <Id, Node> Dag<Id, Node> dag(IdDag<Id> idDag,
      ImmutableBiMap<Id, Node> id__node) {
    
    return new DagClass<>(idDag, id__node);
  }

  // ===========================================================================
  // dag from up nodes
  // ===========================================================================

  public static <Id, Node extends UpNode<Id>> Dag<Id, Node> up(ImmutableSet<Node> nodeSet) {
        
    IdDag<Id> idDag = UpNodeLib.nodes__idDag(nodeSet);
    
    ImmutableBiMap<Id, Node> id__node = UpNodeLib.nodes__nodeMap(nodeSet);
    
    return dag(idDag, id__node);
  }

  public static <Id, Node extends UpNode<Id>> Dag<Id, Node> up(Iterable<Node> nodeIter) {
    return up(ImmutableSet.copyOf(nodeIter));
  }

  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> Dag<Id, Node> up(Node... nodeArray) {
    return up(ImmutableSet.copyOf(nodeArray));
  }
  
  @SafeVarargs
  public static <Id, Node extends UpNode<Id>> Dag<Id, Node> up(Set<Node>... dags) {
    return up(SetLib.union(dags));
  }

  // ===========================================================================
  // dag from down nodes
  // ===========================================================================

  public static <Id, Node extends DownNode<Id>> Dag<Id, Node> down(ImmutableSet<Node> nodeSet) {
        
    IdDag<Id> idDag = DownNodeLib.nodes__idDag(nodeSet);
    
    ImmutableBiMap<Id, Node> id__node = DownNodeLib.nodes__nodeMap(nodeSet);
    
    return dag(idDag, id__node);
  }

  public static <Id, Node extends DownNode<Id>> Dag<Id, Node> down(Iterable<Node> nodeIter) {
    return down(ImmutableSet.copyOf(nodeIter));
  }

  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> Dag<Id, Node> down(Node... nodeArray) {
    return down(ImmutableSet.copyOf(nodeArray));
  }

  @SafeVarargs
  public static <Id, Node extends DownNode<Id>> Dag<Id, Node> down(Set<Node>... dags) {
    return down(SetLib.union(dags));
  }

}
