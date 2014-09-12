package com.stevewedig.blog.digraph.id_graph;

import java.util.*;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.errors.*;

/**
 * An implementation of IdTree.
 */
public class IdTreeClass<Id> extends IdDagClass<Id> implements IdTree<Id> {

  // ===========================================================================
  // constructor
  // ===========================================================================

  public IdTreeClass(ImmutableSet<Id> idSet, ImmutableSetMultimap<Id, Id> id__parentIds) {

    super(idSet, id__parentIds);

    validate();
  }

  // ===========================================================================
  // validate
  // ===========================================================================

  private void validate() throws TreeCannotBeEmpty, TreeCannotHaveMultipleRoots,
      TreeNodesCannotHaveMultipleParents {

    if (idSet().size() == 0)
      throw new TreeCannotBeEmpty();

    if (rootIdSet().size() > 1)
      throw new TreeCannotHaveMultipleRoots("rootIds = %s", rootIdSet());

    for (Id id : idSet()) {

      ImmutableSet<Id> parentIds = id__parentIds().get(id);

      if (parentIds.size() >= 2)
        throw new TreeNodesCannotHaveMultipleParents("id = %s, parentIds = %s", id, parentIds);
    }
  }

  // ===========================================================================
  // root
  // ===========================================================================

  @Override
  public Id rootId() {
    return Iterables.getOnlyElement(rootIdSet());
  }

  // ===========================================================================
  // parent
  // ===========================================================================

  @Override
  public Optional<Id> parentId(Id id) {

    ImmutableSet<Id> parents = parentIdSet(id);

    if (parents.isEmpty())
      return Optional.absent();
    else
      return Optional.of(Iterables.getOnlyElement(parents));
  }

  // ===========================================================================
  // ancestors
  // ===========================================================================

  @Override
  public ImmutableList<Id> ancestorIdList(Id id) {

    List<Id> ancestorIds = new ArrayList<>();

    Id currentId = id;

    while (true) {

      Optional<Id> parentId = parentId(currentId);

      if (!parentId.isPresent())
        break;

      currentId = parentId.get();
      ancestorIds.add(0, currentId);
    }

    return ImmutableList.copyOf(ancestorIds);
  }

  // ===========================================================================
  // depth
  // ===========================================================================

  @Override
  public int depth(Id id) {
    return ancestorIdList(id).size();
  }

  // ===================================

  @Override
  public int maxDepth() {
    if (maxDepth == null) {

      maxDepth = 0;

      for (Id id : idSet()) {

        int depth = depth(id);

        if (depth > maxDepth)
          maxDepth = depth;
      }

    }
    return maxDepth;
  }

  private Integer maxDepth;

}
