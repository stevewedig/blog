package com.stevewedig.blog.digraph.node_graph;

import java.util.*;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.id_graph.*;

/**
 * An implementation of Tree.
 */
public class TreeClass<Id, Node> extends DagClass<Id, Node> implements Tree<Id, Node> {

  // ===========================================================================
  // state
  // ===========================================================================

  private final IdTree<Id> idTree;

  // ===========================================================================
  // constructor
  // ===========================================================================

  public TreeClass(IdTree<Id> idTree, ImmutableBiMap<Id, Node> id__node, boolean allowPartial) {
    super(idTree, id__node, allowPartial);
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
    return convertOptional(parentId(id), false);
  }

  // ===========================================================================
  // ancestors
  // ===========================================================================

  @Override
  public IdTree<Id> ancestorIdGraph(Id id, boolean inclusive) {
    return idTree.ancestorIdGraph(id, inclusive);
  }

  @Override
  public IdTree<Id> ancestorIdGraph(Set<Id> ids, boolean inclusive) {
    return idTree.ancestorIdGraph(ids, inclusive);
  }

  @Override
  public ImmutableList<Id> ancestorIdList(Id id, boolean inclusive) {
    return idTree.ancestorIdList(id, inclusive);
  }

  @Override
  public ImmutableList<Node> ancestorNodeList(Id id, boolean inclusive) {
    return convertList(ancestorIdList(id, inclusive), false);
  }

  // ===========================================================================
  // descendants
  // ===========================================================================

  @Override
  public IdTree<Id> descendantIdTree(Id id) {
    return idTree.descendantIdTree(id);
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
    return getNode(rootId());
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

  @Override
  public Id mostDeep(Set<Id> ids) {
    return idTree.mostDeep(ids);
  }

  @Override
  public Id leastDeep(Set<Id> ids) {
    return idTree.leastDeep(ids);
  }

}
