package com.stevewedig.blog.digraph;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.collect.*;
import com.stevewedig.blog.digraph.id_graph.*;

// example dag containing diamond (a, b, c, d)
//
// id -> parentIds...
// a ->
// b -> a
// c -> a
// d -> b, c
// e -> d
//
// childIds <- id...
// b, c <- a
// d <- b
// d <- c
// e <- d
// <- e
public class TestSampleIdDag {

  // ===========================================================================
  // ids
  // ===========================================================================

  private static ImmutableSet<String> idSet = ImmutableSet.of("a", "b", "c", "d", "e");

  // ===========================================================================
  // graphs
  // ===========================================================================

  private static IdDag<String> idDagFromParentMap() {

    Multimap<String, String> id__parentIds = HashMultimap.create();

    // a ->
    // b -> a
    // c -> a
    // d -> b, c
    // e -> d
    id__parentIds.put("b", "a");
    id__parentIds.put("c", "a");
    id__parentIds.put("d", "b");
    id__parentIds.put("d", "c");
    id__parentIds.put("e", "d");

    return IdDagLib.fromParentMap(idSet, id__parentIds);
  }

  private static IdDag<String> idDagFromChildMap() {

    Multimap<String, String> id__childIds = HashMultimap.create();

    // b, c <- a
    // d <- b
    // d <- c
    // e <- d
    // <- e
    id__childIds.put("a", "b");
    id__childIds.put("a", "c");
    id__childIds.put("b", "d");
    id__childIds.put("c", "d");
    id__childIds.put("d", "e");

    return IdDagLib.fromChildMap(idSet, id__childIds);
  }

  // ===========================================================================
  // tests
  // ===========================================================================

  @Test
  public void testIdDagFromParentMap() {

    verifyIdDag(idDagFromParentMap());
  }

  @Test
  public void testIdDagFromChildMap() {

    verifyIdDag(idDagFromChildMap());
  }

  // ===========================================================================
  // verify dag
  // ===========================================================================

  public static void verifyIdDag(IdDag<String> dag) {

    // =================================
    // idSet
    // =================================

    assertEquals(idSet, dag.idSet());
    assertEquals(idSet.size(), dag.idSize());

    // =================================
    // parents
    // =================================

    // a ->
    // b -> a
    // c -> a
    // d -> b, c
    // e -> d
    assertEquals(ImmutableSet.of(), dag.parentIdSet("a"));
    assertEquals(ImmutableSet.of("a"), dag.parentIdSet("b"));
    assertEquals(ImmutableSet.of("a"), dag.parentIdSet("c"));
    assertEquals(ImmutableSet.of("b", "c"), dag.parentIdSet("d"));
    assertEquals(ImmutableSet.of("d"), dag.parentIdSet("e"));

    // =================================
    // children
    // =================================

    // b, c <- a
    // d <- b
    // d <- c
    // e <- d
    // <- e
    assertEquals(ImmutableSet.of("b", "c"), dag.childIdSet("a"));
    assertEquals(ImmutableSet.of("d"), dag.childIdSet("b"));
    assertEquals(ImmutableSet.of("d"), dag.childIdSet("c"));
    assertEquals(ImmutableSet.of("e"), dag.childIdSet("d"));
    assertEquals(ImmutableSet.of(), dag.childIdSet("e"));

    // =================================
    // ancestors
    // =================================

    // a ->
    // b -> a
    // c -> a
    // d -> b, c
    // e -> d
    assertEquals(ImmutableSet.of(), dag.ancestorIdSet("a"));
    assertEquals(ImmutableSet.of("a"), dag.ancestorIdSet("b"));
    assertEquals(ImmutableSet.of("a"), dag.ancestorIdSet("c"));
    assertEquals(ImmutableSet.of("a", "b", "c"), dag.ancestorIdSet("d"));
    assertEquals(ImmutableSet.of("a", "b", "c", "d"), dag.ancestorIdSet("e"));

    // =================================
    // descendants
    // =================================

    // b, c <- a
    // d <- b
    // d <- c
    // e <- d
    // <- e
    assertEquals(ImmutableSet.of("b", "c", "d", "e"), dag.descendantIdSet("a"));
    assertEquals(ImmutableSet.of("d", "e"), dag.descendantIdSet("b"));
    assertEquals(ImmutableSet.of("d", "e"), dag.descendantIdSet("c"));
    assertEquals(ImmutableSet.of("e"), dag.descendantIdSet("d"));
    assertEquals(ImmutableSet.of(), dag.descendantIdSet("e"));

    // =================================
    // roots (sources)
    // =================================

    assertEquals(ImmutableSet.of("a"), dag.rootIdSet());

    // =================================
    // leaves (sinks)
    // =================================

    assertEquals(ImmutableSet.of("e"), dag.leafIdSet());

    // =================================
    // topological sort
    // =================================

    assertFalse(dag.containsCycle());

    ImmutableSet<ImmutableList<String>> topsortIdLists =
        ImmutableSet.of(ImmutableList.of("a", "b", "c", "d", "e"),
            ImmutableList.of("a", "c", "b", "d", "e"));

    assertTrue(topsortIdLists.contains(dag.topsortIdList()));

    // same instance
    assertTrue(dag.topsortIdList() == dag.optionalTopsortIdList().get());

    // =================================
    // depth first
    // =================================

    ImmutableSet<ImmutableList<String>> depthIdLists =
        ImmutableSet.of(ImmutableList.of("a", "b", "d", "e", "c"),
            ImmutableList.of("a", "c", "d", "e", "b"));

    assertTrue(depthIdLists.contains(dag.depthIdList()));

    // =================================
    // breadth first
    // =================================

    ImmutableSet<ImmutableList<String>> breadthIdLists =
        ImmutableSet.of(ImmutableList.of("a", "b", "c", "d", "e"),
            ImmutableList.of("a", "c", "b", "d", "e"));

    assertTrue(breadthIdLists.contains(dag.breadthIdList()));

  }

}
