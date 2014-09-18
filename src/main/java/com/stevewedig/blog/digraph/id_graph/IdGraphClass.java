package com.stevewedig.blog.digraph.id_graph;

import java.util.*;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.alg.*;
import com.stevewedig.blog.digraph.errors.GraphHadUnexpectedIds;
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

  private void validate() throws GraphHadUnexpectedIds {

    ImmutableSet<Id> mapIds = MultimapLib.keysAndValues(id__parentIds());

    Set<Id> unexpectedIds = Sets.difference(mapIds, idSet());

    if (!unexpectedIds.isEmpty())
      throw new GraphHadUnexpectedIds("unexpectedIds = %s, mapIds = %s, idSet = %s", unexpectedIds,
          mapIds, idSet());
  }

  // ===========================================================================
  // ids
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

  // ===================================

  @Override
  public void assertIdsEqual(ImmutableSet<Id> ids) {
    SetLib.assertEquals(idSet(), ids);
  }

  @Override
  public void assertIdsEqual(Id[] ids) {
    assertIdsEqual(ImmutableSet.copyOf(ids));
  }

  // ===================================

  @Override
  public IdGraph<Id> filterByIds(Set<Id> ids) {
    return IdGraphLib.fromParentMap(ids, filterParentMap(ids));
  }

  // ===========================================================================
  // parents
  // ===========================================================================

  @Override
  public boolean parentOf(Id id, Id potentialChild) {
    return childIdSet(id).contains(potentialChild);
  }

  // ===================================

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

  protected Fn1<Id, List<Id>> parentIdListLambda() {
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

  // ===================================

  protected SetMultimap<Id, Id> filterParentMap(final Set<Id> ids) {
    return MultimapLib.filterKeysAndValues(id__parentIds(), new Predicate<Id>() {
      @Override
      public boolean apply(Id id) {
        return ids.contains(id);
      }
    });
  }

  // ===========================================================================
  // children
  // ===========================================================================

  @Override
  public boolean childOf(Id id, Id potentialParent) {
    return parentIdSet(id).contains(potentialParent);
  }

  // ===================================

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

  protected Fn1<Id, List<Id>> childIdListLambda() {
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
  public boolean ancestorOf(Id id, Id potentialDescendant, boolean inclusive) {
    return descendantOf(potentialDescendant, id, inclusive);
  }

  @Override
  public Iterable<Id> ancestorIdIterable(Id id, boolean inclusive) {
    return idIterable(true, inclusive, ImmutableList.of(id), parentIdListLambda());
  }

  @Override
  public Iterable<Id> ancestorIdIterable(Set<Id> ids, boolean inclusive) {
    return idIterable(true, inclusive, ImmutableList.copyOf(ids), parentIdListLambda());
  }

  @Override
  public ImmutableSet<Id> ancestorIdSet(Id id, boolean inclusive) {
    return ImmutableSet.copyOf(ancestorIdIterable(id, inclusive));
  }

  @Override
  public ImmutableSet<Id> ancestorIdSet(Set<Id> ids, boolean inclusive) {
    return ImmutableSet.copyOf(ancestorIdIterable(ids, inclusive));
  }

  @Override
  public IdGraph<Id> ancestorIdGraph(Id id, boolean inclusive) {
    return ancestorIdGraph(ImmutableSet.of(id), inclusive);
  }

  @Override
  public IdGraph<Id> ancestorIdGraph(Set<Id> ids, boolean inclusive) {
    return filterByIds(ancestorIdSet(ids, inclusive));
  }

  // ===========================================================================
  // descendants
  // ===========================================================================

  @Override
  public boolean descendantOf(Id id, Id potentialAncestor, boolean inclusive) {
    return ancestorIdSet(id, inclusive).contains(potentialAncestor);
  }

  @Override
  public Iterable<Id> descendantIdIterable(Id id, boolean inclusive) {
    return idIterable(true, inclusive, ImmutableList.of(id), childIdListLambda());
  }

  @Override
  public Iterable<Id> descendantIdIterable(Set<Id> ids, boolean inclusive) {
    return idIterable(true, inclusive, ImmutableList.copyOf(ids), childIdListLambda());
  }

  @Override
  public ImmutableSet<Id> descendantIdSet(Id id, boolean inclusive) {
    return ImmutableSet.copyOf(descendantIdIterable(id, inclusive));
  }

  @Override
  public ImmutableSet<Id> descendantIdSet(Set<Id> ids, boolean inclusive) {
    return ImmutableSet.copyOf(descendantIdIterable(ids, inclusive));
  }

  @Override
  public IdGraph<Id> descendantIdGraph(Id id, boolean inclusive) {
    return descendantIdGraph(ImmutableSet.of(id), inclusive);
  }

  @Override
  public IdGraph<Id> descendantIdGraph(Set<Id> ids, boolean inclusive) {
    return filterByIds(descendantIdSet(ids, inclusive));
  }

  // ===========================================================================
  // roots (sources)
  // ===========================================================================

  @Override
  public boolean isRootId(Id id) {
    return rootIdSet().contains(id);
  }

  // ===================================

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
  public boolean isLeafId(Id id) {
    return leafIdSet().contains(id);
  }

  // ===================================

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

    // Notice that completedly delegate to TraverseLib. This is only exposed as a method on IdGraph
    // for convenient access.
    return TraverseLib.idIterable(depthFirst, includeStarts, startIds, expand);
  }

  @Override
  public ImmutableList<Id> idList(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Id, List<Id>> expand) {

    return ImmutableList.copyOf(idIterable(depthFirst, includeStarts, startIds, expand));
  }

}
