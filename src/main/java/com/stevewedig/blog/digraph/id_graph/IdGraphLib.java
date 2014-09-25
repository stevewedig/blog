package com.stevewedig.blog.digraph.id_graph;

import java.util.Set;

import com.google.common.collect.*;
import com.stevewedig.blog.util.MultimapLib;

/**
 * A library for creating IdGraphs.
 */
public abstract class IdGraphLib {

  // ===========================================================================
  // fromParentMap
  // ===========================================================================

  /**
   * Create an IdGraph from the graph's id set and a mapping from id to parent ids (specified as a multimap).
   */
  public static <Id> IdGraph<Id> fromParentMap(Set<Id> idSet, Multimap<Id, Id> id__parentIds) {
    return new IdGraphClass<Id>(ImmutableSet.copyOf(idSet),
        ImmutableSetMultimap.copyOf(id__parentIds));
  }

  /**
   * Create an IdGraph from a mapping from id to parent ids (specified as a multimap), assuming all ids are in that mapping.
   */
  public static <Id> IdGraph<Id> fromParentMap(Multimap<Id, Id> id__parentIds) {
    return fromParentMap(MultimapLib.keysAndValues(id__parentIds), id__parentIds);
  }

  /**
   * Create an IdGraph from the graph's id set and a mapping from id to parent ids (specified as alternating keys and values).
   */
  @SafeVarargs
  public static <Id> IdGraph<Id> fromParentMap(Set<Id> idSet, Id... alternatingIdsAndParentIds) {
    return fromParentMap(idSet, MultimapLib.of(alternatingIdsAndParentIds));
  }

  /**
   * Create an IdGraph from a mapping from id to parent ids (specified as alternating keys and values), assuming all ids are in that mapping.
   */
  @SafeVarargs
  public static <Id> IdGraph<Id> fromParentMap(Id... alternatingIdsAndParentIds) {
    return fromParentMap(MultimapLib.of(alternatingIdsAndParentIds));
  }

  // ===========================================================================
  // fromChildMap
  // ===========================================================================

  /**
   * Create an IdGraph from the graph's id set and a mapping from id to child ids (specified as a multimap).
   */
  public static <Id> IdGraph<Id> fromChildMap(Set<Id> idSet, Multimap<Id, Id> id__childIds) {

    ImmutableSetMultimap<Id, Id> id__parentIds =
        ImmutableSetMultimap.copyOf(id__childIds).inverse();

    return fromParentMap(idSet, id__parentIds);
  }

  /**
   * Create an IdGraph from a mapping from id to child ids (specified as a multimap), assuming all ids are in that mapping.
   */
  public static <Id> IdGraph<Id> fromChildMap(Multimap<Id, Id> id__childIds) {
    return fromChildMap(MultimapLib.keysAndValues(id__childIds), id__childIds);
  }

  /**
   * Create an IdGraph from the graph's id set and a mapping from id to child ids (specified as alternating keys and values).
   */
  @SafeVarargs
  public static <Id> IdGraph<Id> fromChildMap(Set<Id> idSet, Id... alternatingIdsAndChildIds) {
    return fromChildMap(idSet, MultimapLib.of(alternatingIdsAndChildIds));
  }

  /**
   * Create an IdGraph from a mapping from id to child ids (specified as alternating keys and values), assuming all ids are in that mapping.
   */
  @SafeVarargs
  public static <Id> IdGraph<Id> fromChildMap(Id... alternatingIdsAndChildIds) {
    return fromChildMap(MultimapLib.of(alternatingIdsAndChildIds));
  }

}
