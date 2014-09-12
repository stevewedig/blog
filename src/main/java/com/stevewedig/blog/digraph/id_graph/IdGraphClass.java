package com.stevewedig.blog.digraph.id_graph;

import java.util.*;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.alg.*;
import com.stevewedig.blog.digraph.errors.GraphContainedUnexpectedIds;
import com.stevewedig.blog.util.LambdaLib.Fn1;
import com.stevewedig.blog.util.*;
import com.stevewedig.blog.value_objects.ValueMixin;

/**
 * An implementation of IdGraph.
 */
public class IdGraphClass<Id> extends ValueMixin implements IdGraph<Id> {

  // ===========================================================================
  // state
  // ===========================================================================

  @Override
  public Object[] fields() {
    return array("idSet", idSet(), "id__parentIds", id__parentIds());
  }

  // ===========================================================================
  // constructor
  // ===========================================================================

  public IdGraphClass(ImmutableSet<Id> idSet, ImmutableSetMultimap<Id, Id> id__parentIds) {

    this.idSet = idSet;
    this.id__parentIds = id__parentIds;

    validate();
  }

  // ===========================================================================
  // validate
  // ===========================================================================

  private void validate() throws GraphContainedUnexpectedIds {

    ImmutableSet<Id> mapIds = MultimapLib.keysAndValues(id__parentIds());

    Set<Id> unexpectedIds = Sets.difference(mapIds, idSet());

    if (!unexpectedIds.isEmpty())
      throw new GraphContainedUnexpectedIds("unexpectedIds = %s, mapIds = %s, idSet = %s",
          unexpectedIds, mapIds, idSet());
  }

  // ===========================================================================
  // idSet
  // ===========================================================================

  @Override
  public ImmutableSet<Id> idSet() {
    return idSet;
  }

  private ImmutableSet<Id> idSet;

  // ===================================

  @Override
  public int idSize() {
    return idSet().size();
  }

  // ===========================================================================
  // parents
  // ===========================================================================

  @Override
  public ImmutableSetMultimap<Id, Id> id__parentIds() {
    return id__parentIds;
  }

  private ImmutableSetMultimap<Id, Id> id__parentIds;

  // ===================================

  @Override
  public ImmutableSet<Id> parentIdSet(Id id) {
    return id__parentIds().get(id);
  }

  // ===================================

  @Override
  public Fn1<Id, List<Id>> parentIdListLambda() {
    if (parentIdListLambda == null)
      parentIdListLambda = new Fn1<Id, List<Id>>() {
        @Override
        public List<Id> apply(Id id) {
          // non-deterministic ordering
          return ImmutableList.copyOf(parentIdSet(id));
        }
      };

    return parentIdListLambda;
  }

  private Fn1<Id, List<Id>> parentIdListLambda;

  // ===========================================================================
  // children
  // ===========================================================================

  @Override
  public ImmutableSetMultimap<Id, Id> id__childIds() {
    if (id__childIds == null) {

      ImmutableSetMultimap.Builder<Id, Id> builder = ImmutableSetMultimap.builder();

      for (Id id : idSet())
        for (Id parentId : id__parentIds().get(id))
          builder.put(parentId, id);

      id__childIds = builder.build();
    }

    return id__childIds;
  }

  private ImmutableSetMultimap<Id, Id> id__childIds;

  // ===================================

  @Override
  public ImmutableSet<Id> childIdSet(Id id) {
    return id__childIds().get(id);
  }

  // ===================================

  @Override
  public Fn1<Id, List<Id>> childIdListLambda() {
    if (childIdListLambda == null)
      childIdListLambda = new Fn1<Id, List<Id>>() {
        @Override
        public List<Id> apply(Id id) {
          // non-deterministic ordering
          return ImmutableList.copyOf(childIdSet(id));
        }
      };

    return childIdListLambda;
  }

  private Fn1<Id, List<Id>> childIdListLambda;

  // ===========================================================================
  // ancestors
  // ===========================================================================

  @Override
  public Iterable<Id> ancestorIdIterable(Id id) {
    return idIterable(true, false, ImmutableList.of(id), parentIdListLambda());
  }

  @Override
  public ImmutableSet<Id> ancestorIdSet(Id id) {
    return ImmutableSet.copyOf(ancestorIdIterable(id));
  }

  // ===========================================================================
  // descendants
  // ===========================================================================

  @Override
  public Iterable<Id> descendantIdIterable(Id id) {
    return idIterable(true, false, ImmutableList.of(id), childIdListLambda());
  }

  @Override
  public ImmutableSet<Id> descendantIdSet(Id id) {
    return ImmutableSet.copyOf(descendantIdIterable(id));
  }

  // ===========================================================================
  // roots (sources)
  // ===========================================================================

  @Override
  public ImmutableSet<Id> rootIdSet() {
    if (rootIds == null) {

      ImmutableSet.Builder<Id> builder = ImmutableSet.builder();

      for (Id id : idSet)
        if (parentIdSet(id).isEmpty())
          builder.add(id);

      rootIds = builder.build();
    }

    return rootIds;
  }

  private ImmutableSet<Id> rootIds;

  // ===========================================================================
  // leaves (sinks)
  // ===========================================================================

  @Override
  public ImmutableSet<Id> leafIdSet() {
    if (leafIds == null) {

      ImmutableSet.Builder<Id> builder = ImmutableSet.builder();

      for (Id id : idSet)
        if (childIdSet(id).isEmpty())
          builder.add(id);

      leafIds = builder.build();
    }

    return leafIds;
  }

  private ImmutableSet<Id> leafIds;

  // ===========================================================================
  // topological sort
  // ===========================================================================

  @Override
  public Optional<ImmutableList<Id>> optionalTopsortIdList() {
    if (optionalTopsortIdList == null)
      optionalTopsortIdList = TopsortLib.sort(idSet(), id__parentIds());
    return optionalTopsortIdList;
  }

  private Optional<ImmutableList<Id>> optionalTopsortIdList;

  // ===================================

  @Override
  public boolean containsCycle() {
    return !optionalTopsortIdList().isPresent();
  }

  // ===========================================================================
  // generic traversal
  // ===========================================================================

  @Override
  public Iterable<Id> idIterable(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Id, List<Id>> expand) {

    // Notice that we just delegate to a static method. This is only exposed as a method on IdGraph
    // for convenient access.
    return TraverseLib.idIterable(depthFirst, includeStarts, startIds, expand);
  }

  @Override
  public ImmutableList<Id> idList(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Id, List<Id>> expand) {

    return ImmutableList.copyOf(idIterable(depthFirst, includeStarts, startIds, expand));
  }

}
