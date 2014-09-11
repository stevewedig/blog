package com.stevewedig.blog.digraph.node_graph;

import java.util.Iterator;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.stevewedig.blog.digraph.id_graph.IdDag;

/**
 * An implementation of Dag.
 */
public class DagClass<Id, Node> extends GraphClass<Id, Node> implements Dag<Id, Node> {

  // ===========================================================================
  // state
  // ===========================================================================

  private final IdDag<Id> idDag;

  // ===========================================================================
  // constructor
  // ===========================================================================

  public DagClass(IdDag<Id> idDag, ImmutableBiMap<Id, Node> id__node) {
    super(idDag, id__node);
    this.idDag = idDag;
  }

  // ===========================================================================
  // idDag
  // ===========================================================================

  @Override
  public IdDag<Id> idGraph() {
    return idDag;
  }

  // ===========================================================================
  // topological sort
  // ===========================================================================

  @Override
  public ImmutableList<Id> topsortIdList() {
    return idDag.topsortIdList();
  }

  @Override
  public ImmutableList<Node> topsortNodeList() {
    return optionalTopsortNodeList().get();
  }

  // ===========================================================================
  // depth first
  // ===========================================================================

  @Override
  public Iterable<Id> depthIdIterable() {
    return idDag.depthIdIterable();
  }

  @Override
  public ImmutableList<Id> depthIdList() {
    return idDag.depthIdList();
  }

  // ===================================

  @Override
  public Iterable<Node> depthNodeIterable() {
    return nodeWrapIterable(idDag.depthIdIterable(), true);
  }

  // ===================================

  @Override
  public ImmutableList<Node> depthNodeList() {
    if (depthNodeList == null)
      depthNodeList = nodeWrapList(depthIdList(), true);
    return depthNodeList;
  }

  private ImmutableList<Node> depthNodeList;

  // ===========================================================================
  // breadth first
  // ===========================================================================

  @Override
  public Iterable<Id> breadthIdIterable() {
    return idDag.breadthIdIterable();
  }

  @Override
  public ImmutableList<Id> breadthIdList() {
    return idDag.breadthIdList();
  }

  // ===================================

  @Override
  public Iterable<Node> breadthNodeIterable() {
    return nodeWrapIterable(idDag.breadthIdIterable(), true);
  }

  // ===================================

  @Override
  public ImmutableList<Node> breadthNodeList() {
    if (breadthNodeList == null)
      breadthNodeList = nodeWrapList(breadthIdList(), true);
    return breadthNodeList;
  }

  private ImmutableList<Node> breadthNodeList;

  // ===========================================================================
  // overriding some Set methods to use nodeList
  // ===========================================================================

  @Override
  public Iterator<Node> iterator() {
    return topsortNodeList().iterator();
  }

  @Override
  public Object[] toArray() {
    return topsortNodeList().toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return topsortNodeList().toArray(a);
  }

}
