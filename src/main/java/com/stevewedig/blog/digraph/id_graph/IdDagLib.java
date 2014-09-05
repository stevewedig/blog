package com.stevewedig.blog.digraph.id_graph;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;
import com.stevewedig.blog.util.MultimapLib;

public abstract class IdDagLib {

  // ===========================================================================
  // fromParentMap
  // ===========================================================================

  public static <Id> IdDag<Id> fromParentMap(Set<Id> idSet, Multimap<Id, Id> id__parentIds) {
    return new IdDagClass<Id>(ImmutableSet.copyOf(idSet),
        ImmutableSetMultimap.copyOf(id__parentIds));
  }

  public static <Id> IdDag<Id> fromParentMap(Multimap<Id, Id> id__parentIds) {
    return fromParentMap(MultimapLib.keysAndValues(id__parentIds), id__parentIds);
  }

  // ===========================================================================
  // fromChildMap
  // ===========================================================================

  public static <Id> IdDag<Id> fromChildMap(Set<Id> idSet, Multimap<Id, Id> id__childIds) {

    ImmutableSetMultimap<Id, Id> id__parentIds =
        ImmutableSetMultimap.copyOf(id__childIds).inverse();

    return fromParentMap(idSet, id__parentIds);
  }

  public static <Id> IdDag<Id> fromChildMap(Multimap<Id, Id> id__childIds) {
    return fromChildMap(MultimapLib.keysAndValues(id__childIds), id__childIds);
  }

}
