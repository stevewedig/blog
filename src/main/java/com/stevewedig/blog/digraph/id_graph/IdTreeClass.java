package com.stevewedig.blog.digraph.id_graph;

import java.util.*;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.errors.*;
import com.stevewedig.blog.util.CollectLib;

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
  // ids
  // ===========================================================================

  private IdTree<Id> treeFilterByIds(Set<Id> ids) {
    return IdTreeLib.fromParentMap(ids, filterParentMap(ids));
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
  public IdTree<Id> ancestorIdGraph(Id id, boolean inclusive) {
    return ancestorIdGraph(ImmutableSet.of(id), inclusive);
  }

  @Override
  public IdTree<Id> ancestorIdGraph(Set<Id> ids, boolean inclusive) {
    return treeFilterByIds(ancestorIdSet(ids, inclusive));
  }
  
  // ===================================

  @Override
  public ImmutableList<Id> ancestorIdList(Id id, boolean inclusive) {

    List<Id> ancestorIds = new ArrayList<>();
    if (inclusive)
      ancestorIds.add(id);

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
  // descendants
  // ===========================================================================

  @Override
  public IdTree<Id> descendantIdTree(Id id) {
    return treeFilterByIds(descendantIdSet(id, true));
  }

  // ===========================================================================
  // root
  // ===========================================================================

  @Override
  public Id rootId() {
    return Iterables.getOnlyElement(rootIdSet());
  }

  // ===========================================================================
  // depth
  // ===========================================================================

  @Override
  public int depth(Id id) {
    return ancestorIdList(id, false).size();
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

  // ===================================

  @Override
  public Id mostDeep(Collection<Id> ids) {

    CollectLib.assertNotEmpty(ids);

    Id currentId = null;
    Integer currentDepth = null;

    for (Id category : ids) {

      int depth = depth(category);

      if (currentId == null || depth > currentDepth) {
        currentId = category;
        currentDepth = depth;
      }
    }

    return currentId;
  }

  // ===================================

  @Override
  public Id leastDeep(Collection<Id> ids) {

    CollectLib.assertNotEmpty(ids);

    Id currentId = null;
    Integer currentDepth = null;

    for (Id category : ids) {

      int depth = depth(category);

      if (currentId == null || depth < currentDepth) {
        currentId = category;
        currentDepth = depth;
      }
    }

    return currentId;

  }

}
