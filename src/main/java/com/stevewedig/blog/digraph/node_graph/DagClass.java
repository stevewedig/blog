package com.stevewedig.blog.digraph.node_graph;

import java.util.Set;

import com.google.common.collect.*;
import com.stevewedig.blog.digraph.id_graph.*;

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

  public DagClass(IdDag<Id> idDag, ImmutableBiMap<Id, Node> id__node, boolean allowPartial) {
    super(idDag, id__node, allowPartial);
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
  // ids
  // ===========================================================================

  @Override
  public IdDag<Id> filterIdGraph(Set<Id> ids) {
    return idDag.filterIdGraph(ids);
  }

  // ===========================================================================
  // ancestors
  // ===========================================================================

  @Override
  public IdDag<Id> ancestorIdGraph(Id id, boolean inclusive) {
    return idDag.ancestorIdGraph(id, inclusive);
  }

  @Override
  public IdDag<Id> ancestorIdGraph(Set<Id> ids, boolean inclusive) {
    return idDag.ancestorIdGraph(ids, inclusive);
  }

  // ===========================================================================
  // descendants
  // ===========================================================================

  @Override
  public IdDag<Id> descendantIdGraph(Id id, boolean inclusive) {
    return idDag.descendantIdGraph(id, inclusive);
  }

  @Override
  public IdDag<Id> descendantIdGraph(Set<Id> ids, boolean inclusive) {
    return idDag.descendantIdGraph(ids, inclusive);
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
    return nodeWrapIterable(idDag.depthIdIterable(), false);
  }

  // ===================================

  @Override
  public ImmutableList<Node> depthNodeList() {
    if (depthNodeList == null)
      depthNodeList = nodeWrapList(depthIdList(), false);
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
    return nodeWrapIterable(idDag.breadthIdIterable(), false);
  }

  // ===================================

  @Override
  public ImmutableList<Node> breadthNodeList() {
    if (breadthNodeList == null)
      breadthNodeList = nodeWrapList(breadthIdList(), false);
    return breadthNodeList;
  }

  private ImmutableList<Node> breadthNodeList;

}
