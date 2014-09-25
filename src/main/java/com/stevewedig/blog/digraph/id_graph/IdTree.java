package com.stevewedig.blog.digraph.id_graph;

import java.util.*;

import com.google.common.base.Optional;
import com.google.common.collect.*;

/**
 * A tree containing ids.
 */
public interface IdTree<Id> extends IdDag<Id> {

  // ===========================================================================
  // ancestors
  // ===========================================================================

  @Override
  IdTree<Id> ancestorIdGraph(Id id, boolean inclusive);

  @Override
  IdTree<Id> ancestorIdGraph(Set<Id> ids, boolean inclusive);

  // ===========================================================================
  // descendants
  // ===========================================================================

  // descendantIdGraph(id/ids, inclusive) won't necessarily return trees

  IdTree<Id> descendantIdTree(Id id);

  // ===========================================================================
  // root
  // ===========================================================================

  /**
   * The tree's root id.
   */
  Id rootId();

  // ===========================================================================
  // parent
  // ===========================================================================

  /**
   * Getting an id's parent id, will be absent if the id is the root.
   */
  Optional<Id> parentId(Id id);

  // ===========================================================================
  // ancestors
  // ===========================================================================

  /**
   * Getting an id's ancestor id list, with the root id first and the id's parent id last.
   */
  ImmutableList<Id> ancestorIdList(Id id, boolean inclusive);

  // ===========================================================================
  // depth
  // ===========================================================================

  /**
   * Getting the size of an id's ancestor list.
   */
  int depth(Id id);

  /**
   * The maximum depth in the tree.
   */
  int maxDepth();

  /**
   * Select the most deep id from a set, non-deterministic if there are ties.
   */
  Id mostDeep(Set<Id> ids);

  /**
   * Select the least deep id from a set, non-deterministic if there are ties.
   */
  Id leastDeep(Set<Id> ids);

}
