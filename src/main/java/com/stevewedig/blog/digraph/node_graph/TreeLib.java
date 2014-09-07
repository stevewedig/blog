package com.stevewedig.blog.digraph.node_graph;

import java.util.Set;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.digraph.id_graph.IdTree;
import com.stevewedig.blog.digraph.node.DownNode;
import com.stevewedig.blog.digraph.node.DownNodeLib;
import com.stevewedig.blog.digraph.node.UpNode;
import com.stevewedig.blog.digraph.node.UpNodeLib;
import com.stevewedig.blog.util.SetLib;

public abstract class TreeLib {

  // ===========================================================================
  // tree from state
  // ===========================================================================

  public static <Id, Node> Tree<Id, Node> tree(IdTree<Id> idTree,
      ImmutableBiMap<Id, Node> id__node) {
    
    return new TreeClass<>(idTree, id__node);
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
