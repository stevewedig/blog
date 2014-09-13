package com.stevewedig.blog.digraph.id_graph;

import java.util.Collection;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

/**
 * A tree containing ids.
 */
public interface IdTree<Id> extends IdDag<Id> {

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

  Id mostDeep(Collection<Id> ids);

  Id leastDeep(Collection<Id> ids);

}
