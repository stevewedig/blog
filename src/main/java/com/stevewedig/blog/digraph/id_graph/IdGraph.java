package com.stevewedig.blog.digraph.id_graph;


import java.util.*;
import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.util.LambdaLib.Fn1;

/**
 * A digraph containing ids.
 */
public interface IdGraph<Id> {

  // ===========================================================================
  // ids
  // ===========================================================================

  /**
   * The set of ids.
   */
  ImmutableSet<Id> idSet();

  /**
   * The number of ids.
   */
  int idSize();
  
  /**
   * Assert that a graph contains exactly these ids.
   */
  void assertIdsEqual(ImmutableSet<Id> ids);

  /**
   * Assert that a graph contains exactly these ids.
   */
  void assertIdsEqual(Id[] ids);

  /**
   * Filter a graph by only keep the specified ids and arcs between these ids.
   */
  IdGraph<Id> filterIdGraph(Set<Id> ids);

  // ===========================================================================
  // parents
  // ===========================================================================

  /**
   * Whether an id is a parent of another id.
   */
  boolean isParentOf(Id id, Id potentialChild);

  /**
   * The mapping from id to parent ids.
   */
  ImmutableSetMultimap<Id, Id> id__parentIds();

  /**
   * Getting an id's parent ids.
   */
  ImmutableSet<Id> parentIdSet(Id id);

  // ===========================================================================
  // children
  // ===========================================================================

  /**
   * Whether an id is a child of another id.
   */
  boolean isChildOf(Id id, Id potentialParent);

  /**
   * The mapping from id to child ids.
   */
  ImmutableSetMultimap<Id, Id> id__childIds();

  /**
   * Getting an id's child ids.
   */
  ImmutableSet<Id> childIdSet(Id id);

  // ===========================================================================
  // ancestors
  // ===========================================================================

  /**
   * Whether an id is a ancestor of another id.
   */
  boolean isAncestorOf(Id id, Id potentialDescendant, boolean inclusive);

  /**
   * Getting an id's ancestor id iterable (its parents, parents' parents, and so on).
   */
  Iterable<Id> ancestorIdIterable(Id id, boolean inclusive);

  /**
   * Getting an id set's ancestor id iterable (their parents, parents' parents, and so on).
   */
  Iterable<Id> ancestorIdIterable(Set<Id> ids, boolean inclusive);

  /**
   * Getting an id's ancestor id set (its parents, parents' parents, and so on).
   */
  ImmutableSet<Id> ancestorIdSet(Id id, boolean inclusive);

  /**
   * Getting an id set's ancestor id set (their parents, parents' parents, and so on).
   */
  ImmutableSet<Id> ancestorIdSet(Set<Id> ids, boolean inclusive);

  /**
   * Getting an id's ancestor id graph (its, parents' parents, and so on).
   */
  IdGraph<Id> ancestorIdGraph(Id id, boolean inclusive);

  /**
   * Getting an id set's ancestor id graph (their parents, parents' parents, and so on).
   */
  IdGraph<Id> ancestorIdGraph(Set<Id> ids, boolean inclusive);

  // ===========================================================================
  // descendants
  // ===========================================================================

  /**
   * Whether an id is a descendant of another id.
   */
  boolean isDescendantOf(Id id, Id potentialAncestor, boolean inclusive);

  /**
   * Getting an id's descendant id iterable (its children, childrens' children, and so on).
   */
  Iterable<Id> descendantIdIterable(Id id, boolean inclusive);

  /**
   * Getting an id set's descendant id iterable (their children, childrens' children, and so on).
   */
  Iterable<Id> descendantIdIterable(Set<Id> ids, boolean inclusive);

  /**
   * Getting an id's descendant id set (its children, childrens' children, and so on).
   */
  ImmutableSet<Id> descendantIdSet(Id id, boolean inclusive);

  /**
   * Getting an id set's descendant id set (their children, childrens' children, and so on).
   */
  ImmutableSet<Id> descendantIdSet(Set<Id> ids, boolean inclusive);


  /**
   * Getting an id's descendant id graph (its children, childrens' children, and so on).
   */
  IdGraph<Id> descendantIdGraph(Id id, boolean inclusive);

  /**
   * Getting an id set's descendant id graph (their children, childrens' children, and so on).
   */
  IdGraph<Id> descendantIdGraph(Set<Id> ids, boolean inclusive);

  // ===========================================================================
  // roots (sources)
  // ===========================================================================

  /**
   * Is an id a root?
   */
  boolean isRoot(Id id);

  /**
   * The digraph's root (source) ids, so the ids without parents.
   */
  ImmutableSet<Id> rootIdSet();

  // ===========================================================================
  // leaves (sinks)
  // ===========================================================================

  /**
   * Is an id a leaf?
   */
  boolean isLeaf(Id id);

  /**
   * The digraph's leaf (sink) ids, so the ids without children.
   */
  ImmutableSet<Id> leafIdSet();

  // ===========================================================================
  // topological sort
  // ===========================================================================

  /**
   * Whether the digraph contains a cycle.
   */
  boolean containsCycle();

  /**
   * A topologically sorted list of ids, with roots (sources) first (will be absent if the digraph
   * is cyclic).
   */
  Optional<ImmutableList<Id>> optionalTopsortIdList();

  // ===========================================================================
  // generic traversal
  // ===========================================================================

  /**
   * Generic id traversal as an iterable.
   * 
   * @param depthFirst Whether to traverse depth first or breadth first.
   * @param inclusive Whether to include the start ids in the traversal.
   * @param startId The initial id.
   * @param expand A function mapping an id to the next ids.
   * @return An id iterable corresponding to the traversal.
   */
  Iterable<Id> traverseIdIterable(boolean depthFirst, boolean inclusive, Id startId,
      Fn1<Id, List<Id>> expand);

  /**
   * Generic id traversal as an iterable.
   * 
   * @param depthFirst Whether to traverse depth first or breadth first.
   * @param inclusive Whether to include the start ids in the traversal.
   * @param startIds The initial id list.
   * @param expand A function mapping an id to the next ids.
   * @return An id iterable corresponding to the traversal.
   */
  Iterable<Id> traverseIdIterable(boolean depthFirst, boolean inclusive,
      ImmutableList<Id> startIds, Fn1<Id, List<Id>> expand);

  /**
   * Generic id traversal copied into a list.
   * 
   * @param depthFirst Whether to traverse depth first or breadth first.
   * @param inclusive Whether to include the start ids in the traversal.
   * @param startIds The initial id.
   * @param expand A function mapping an id to the next ids.
   * @return An id list corresponding to the traversal.
   */
  ImmutableList<Id> traverseIdList(boolean depthFirst, boolean inclusive, Id startId,
      Fn1<Id, List<Id>> expand);

  /**
   * Generic id traversal copied into a list.
   * 
   * @param depthFirst Whether to traverse depth first or breadth first.
   * @param inclusive Whether to include the start ids in the traversal.
   * @param startIds The initial id list.
   * @param expand A function mapping an id to the next ids.
   * @return An id list corresponding to the traversal.
   */
  ImmutableList<Id> traverseIdList(boolean depthFirst, boolean inclusive,
      ImmutableList<Id> startIds, Fn1<Id, List<Id>> expand);

}
