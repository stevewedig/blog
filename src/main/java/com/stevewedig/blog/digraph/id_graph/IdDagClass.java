package com.stevewedig.blog.digraph.id_graph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.stevewedig.blog.digraph.id_graph.GraphValidationErrors.DagCannotHaveCycle;

public class IdDagClass<Id> extends IdGraphClass<Id> implements IdDag<Id> {

  // ===========================================================================
  // constructor
  // ===========================================================================

  public IdDagClass(ImmutableSet<Id> idSet, ImmutableSetMultimap<Id, Id> id__parentIds) {

    super(idSet, id__parentIds);

    validate();
  }

  // ===========================================================================
  // validate
  // ===========================================================================

  private void validate() throws DagCannotHaveCycle {

    if (containsCycle())
      throw new DagCannotHaveCycle();
  }

  // ===========================================================================
  // toplogical sort
  // ===========================================================================

  @Override
  public ImmutableList<Id> topsortIdList() {
    return optionalTopsortIdList().get();
  }

  // ===========================================================================
  // depth first
  // ===========================================================================

  @Override
  public Iterable<Id> depthIdIterable() {
    return idIterable(true, true, ImmutableList.copyOf(rootIdSet()), childIdListLambda());
  }

  // ===================================

  @Override
  public ImmutableList<Id> depthIdList() {
    if (depthIdList == null)
      depthIdList = ImmutableList.copyOf(depthIdIterable());
    return depthIdList;
  }

  private ImmutableList<Id> depthIdList;

  // ===========================================================================
  // breadth first
  // ===========================================================================

  @Override
  public Iterable<Id> breadthIdIterable() {
    return idIterable(false, true, ImmutableList.copyOf(rootIdSet()), childIdListLambda());
  }

  // ===================================

  @Override
  public ImmutableList<Id> breadthIdList() {
    if (breadthIdList == null)
      breadthIdList = ImmutableList.copyOf(breadthIdIterable());
    return breadthIdList;
  }

  private ImmutableList<Id> breadthIdList;

}
