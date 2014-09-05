package com.stevewedig.blog.digraph.node_graph;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.stevewedig.blog.digraph.id_graph.IdTree;

public class TreeClass<Id, Node> extends DagClass<Id, Node> implements Tree<Id, Node> {

  // ===========================================================================
  // state
  // ===========================================================================

  private final IdTree<Id> idTree;

  // ===========================================================================
  // constructor
  // ===========================================================================

  public TreeClass(IdTree<Id> idTree, ImmutableBiMap<Id, Node> id__node) {
    super(idTree, id__node);
    this.idTree = idTree;
  }
  
  // ===========================================================================
  // idTree
  // ===========================================================================
  
  @Override
  public IdTree<Id> idGraph() {
    return idTree;
  }

  // ===========================================================================
  // parent
  // ===========================================================================

  @Override
  public Optional<Id> parentId(Id id) {
    return idTree.parentId(id);
  }
  
  @Override
  public Optional<Node> parentNode(Id id) {
    return nodeWrapOptional(parentId(id));
  }

  // ===========================================================================
  // ancestors
  // ===========================================================================

  @Override
  public ImmutableList<Id> ancestorIdList(Id id) {
    return idTree.ancestorIdList(id);
  }

  @Override
  public ImmutableList<Node> ancestorNodeList(Id id) {
    return nodeWrapList(ancestorIdList(id));
  }
  
  // ===========================================================================
  // root (source)
  // ===========================================================================

  @Override
  public Id rootId() {
    return idTree.rootId();
  }

  @Override
  public Node rootNode() {
    return node(rootId());
  }

  // ===========================================================================
  // depth
  // ===========================================================================

  @Override
  public int depth(Id id) {
    return idTree.depth(id);
  }

  @Override
  public int maxDepth() {
    return idTree.maxDepth();
  }
  
}
