package com.stevewedig.blog.digraph.id_graph;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.stevewedig.blog.util.LambdaLib.Fn1;

public interface IdGraph<Id> {

  // ===========================================================================
  // idSet
  // ===========================================================================

  /**
   * @return The set of ids (larger than nodeSet when the graph as unboundIds).
   */
  ImmutableSet<Id> idSet();

  int idSize();

  // ===========================================================================
  // parents
  // ===========================================================================

  ImmutableSetMultimap<Id, Id> id__parentIds();

  ImmutableSet<Id> parentIdSet(Id id);

  Fn1<Id, List<Id>> parentIdListLambda();

  // ===========================================================================
  // children
  // ===========================================================================

  ImmutableSetMultimap<Id, Id> id__childIds();

  ImmutableSet<Id> childIdSet(Id id);

  Fn1<Id, List<Id>> childIdListLambda();

  // ===========================================================================
  // ancestors
  // ===========================================================================

  Iterable<Id> ancestorIdIterable(Id id);

  /**
   * 
   * @param id
   * @return ancestorIds, not inclusive, no duplicates
   */
  ImmutableSet<Id> ancestorIdSet(Id id);

  // ===========================================================================
  // descendants
  // ===========================================================================

  Iterable<Id> descendantIdIterable(Id id);

  /**
   * 
   * @param id
   * @return descendantIds, not inclusive
   */
  ImmutableSet<Id> descendantIdSet(Id id);

  // ===========================================================================
  // roots (sources)
  // ===========================================================================

  ImmutableSet<Id> rootIdSet();

  // ===========================================================================
  // leaves (sinks)
  // ===========================================================================

  ImmutableSet<Id> leafIdSet();

  // ===========================================================================
  // topological sort
  // ===========================================================================

  boolean containsCycle();

  /**
   * @return Topologically sorted list of ids, which will be Optional.absent() when the graph
   *         contains a cycle.
   */
  Optional<ImmutableList<Id>> optionalTopsortIdList();

  // ===========================================================================
  // generic traversal
  // ===========================================================================

  /**
   * Generic id traversal.
   * 
   * @param depthFirst Whether to traverse depth first or breadth first.
   * @param includeStarts Whether to include the start ids in the traversal.
   * @param startIds The initial id set.
   * @param expand A function mapping an id to the next ids.
   * @return An id iterable corresponding to the traversal.
   */
  Iterable<Id> idIterable(boolean depthFirst, boolean includeStarts, ImmutableList<Id> startIds,
      Fn1<Id, List<Id>> expand);

}
