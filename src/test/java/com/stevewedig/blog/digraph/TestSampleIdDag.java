package com.stevewedig.blog.digraph;

import static com.stevewedig.blog.translate.FormatLib.*;
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

  private static ImmutableSet<String> idSet = parseSet("a, b, c, d, e");

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
    // ids
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

    assertEquals(parseSet(""), dag.parentIdSet("a"));
    assertEquals(parseSet("a"), dag.parentIdSet("b"));
    assertEquals(parseSet("a"), dag.parentIdSet("c"));
    assertEquals(parseSet("b, c"), dag.parentIdSet("d"));
    assertEquals(parseSet("d"), dag.parentIdSet("e"));

    // =================================
    // children
    // =================================

    // b, c <- a
    // d <- b
    // d <- c
    // e <- d
    // <- e

    assertEquals(parseSet("b, c"), dag.childIdSet("a"));
    assertEquals(parseSet("d"), dag.childIdSet("b"));
    assertEquals(parseSet("d"), dag.childIdSet("c"));
    assertEquals(parseSet("e"), dag.childIdSet("d"));
    assertEquals(parseSet(""), dag.childIdSet("e"));

    // =================================
    // ancestors
    // =================================

    // a ->
    // b -> a
    // c -> a
    // d -> b, c
    // e -> d

    assertEquals(parseSet(""), dag.ancestorIdSet("a", false));
    assertEquals(parseSet("a"), dag.ancestorIdSet("b", false));
    assertEquals(parseSet("a"), dag.ancestorIdSet("c", false));
    assertEquals(parseSet("a, b, c"), dag.ancestorIdSet("d", false));
    assertEquals(parseSet("a, b, c, d"), dag.ancestorIdSet("e", false));

    // =================================
    // descendants
    // =================================

    // b, c <- a
    // d <- b
    // d <- c
    // e <- d
    // <- e

    assertEquals(parseSet("b, c, d, e"), dag.descendantIdSet("a", false));
    assertEquals(parseSet("d, e"), dag.descendantIdSet("b", false));
    assertEquals(parseSet("d, e"), dag.descendantIdSet("c", false));
    assertEquals(parseSet("e"), dag.descendantIdSet("d", false));
    assertEquals(parseSet(""), dag.descendantIdSet("e", false));

    // =================================
    // roots (sources)
    // =================================

    assertEquals(parseSet("a"), dag.rootIdSet());

    // =================================
    // leaves (sinks)
    // =================================

    assertEquals(parseSet("e"), dag.leafIdSet());

    // =================================
    // topological sort
    // =================================

    assertFalse(dag.containsCycle());

    ImmutableSet<ImmutableList<String>> topsortIdLists =
        ImmutableSet.of(parseList("a, b, c, d, e"), parseList("a, c, b, d, e"));

    assertTrue(topsortIdLists.contains(dag.topsortIdList()));

    // same instance
    assertTrue(dag.topsortIdList() == dag.optionalTopsortIdList().get());

    // =================================
    // depth first
    // =================================

    ImmutableSet<ImmutableList<String>> depthIdLists =
        ImmutableSet.of(parseList("a, b, d, e, c"), parseList("a, c, d, e, b"));

    assertTrue(depthIdLists.contains(dag.depthIdList()));

    // =================================
    // breadth first
    // =================================

    ImmutableSet<ImmutableList<String>> breadthIdLists =
        ImmutableSet.of(parseList("a, b, c, d, e"), parseList("a, c, b, d, e"));

    assertTrue(breadthIdLists.contains(dag.breadthIdList()));

  }

}
