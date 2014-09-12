package com.stevewedig.blog.digraph;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.collect.*;
import com.stevewedig.blog.digraph.id_graph.*;

// example graph containing cycles (a->b->c->d->a, and a->e->a)
//
// id -> parentIds...
// a -> d, e
// b -> a
// c -> b
// d -> c
// e -> a
// f ->
//
// childIds <- id...
// b, e <- a
// c <- b
// d <- c
// a <- d
// a <- e
// <- f
public class TestSampleIdGraph {

  // ===========================================================================
  // ids
  // ===========================================================================

  private static ImmutableSet<String> idSet = ImmutableSet.of("a", "b", "c", "d", "e", "f");

  // ===========================================================================
  // graphs
  // ===========================================================================

  private static IdGraph<String> idGraphFromParentMap() {

    Multimap<String, String> id__parentIds = HashMultimap.create();

    // a -> d, e
    // b -> a
    // c -> b
    // d -> c
    // e -> a
    // f ->
    id__parentIds.put("a", "d");
    id__parentIds.put("a", "e");
    id__parentIds.put("b", "a");
    id__parentIds.put("c", "b");
    id__parentIds.put("d", "c");
    id__parentIds.put("e", "a");

    return IdGraphLib.fromParentMap(idSet, id__parentIds);
  }

  // ===================================

  private static IdGraph<String> idGraphFromChildMap() {

    Multimap<String, String> id__childIds = HashMultimap.create();

    // b, e <- a
    // c <- b
    // d <- c
    // a <- d
    // a <- e
    // <- f
    id__childIds.put("a", "b");
    id__childIds.put("a", "e");
    id__childIds.put("b", "c");
    id__childIds.put("c", "d");
    id__childIds.put("d", "a");
    id__childIds.put("e", "a");

    return IdGraphLib.fromChildMap(idSet, id__childIds);
  }

  // ===========================================================================
  // tests
  // ===========================================================================

  @Test
  public void testIdGraphFromParentMap() {

    verifyIdGraph(idGraphFromParentMap());
  }

  @Test
  public void testIdGraphFromChildMap() {

    verifyIdGraph(idGraphFromChildMap());
  }

  // ===========================================================================
  // verify graph
  // ===========================================================================

  public static void verifyIdGraph(IdGraph<String> graph) {

    // =================================
    // idSet
    // =================================

    assertEquals(idSet, graph.idSet());
    assertEquals(idSet.size(), graph.idSize());

    // =================================
    // optIdList
    // =================================

    assertTrue(graph.containsCycle());

    assertFalse(graph.optionalTopsortIdList().isPresent());

    // =================================
    // parents
    // =================================

    // a -> d, e
    // b -> a
    // c -> b
    // d -> c
    // e -> a
    // f ->
    assertEquals(ImmutableSet.of("d", "e"), graph.parentIdSet("a"));
    assertEquals(ImmutableSet.of("a"), graph.parentIdSet("b"));
    assertEquals(ImmutableSet.of("b"), graph.parentIdSet("c"));
    assertEquals(ImmutableSet.of("c"), graph.parentIdSet("d"));
    assertEquals(ImmutableSet.of("a"), graph.parentIdSet("e"));
    assertEquals(ImmutableSet.of(), graph.parentIdSet("f"));

    // =================================
    // children
    // =================================

    // b, e <- a
    // c <- b
    // d <- c
    // a <- d
    // a <- e
    // <- f
    assertEquals(ImmutableSet.of("b", "e"), graph.childIdSet("a"));
    assertEquals(ImmutableSet.of("c"), graph.childIdSet("b"));
    assertEquals(ImmutableSet.of("d"), graph.childIdSet("c"));
    assertEquals(ImmutableSet.of("a"), graph.childIdSet("d"));
    assertEquals(ImmutableSet.of("a"), graph.childIdSet("e"));
    assertEquals(ImmutableSet.of(), graph.childIdSet("f"));

    // =================================
    // ancestors
    // =================================

    assertEquals(ImmutableSet.of("b", "c", "d", "e"), graph.ancestorIdSet("a"));
    assertEquals(ImmutableSet.of("a", "c", "d", "e"), graph.ancestorIdSet("b"));
    assertEquals(ImmutableSet.of("a", "b", "d", "e"), graph.ancestorIdSet("c"));
    assertEquals(ImmutableSet.of("a", "b", "c", "e"), graph.ancestorIdSet("d"));
    assertEquals(ImmutableSet.of("a", "b", "c", "d"), graph.ancestorIdSet("e"));
    assertEquals(ImmutableSet.of(), graph.ancestorIdSet("f"));

    // =================================
    // descendants
    // =================================

    assertEquals(ImmutableSet.of("b", "c", "d", "e"), graph.descendantIdSet("a"));
    assertEquals(ImmutableSet.of("a", "c", "d", "e"), graph.descendantIdSet("b"));
    assertEquals(ImmutableSet.of("a", "b", "d", "e"), graph.descendantIdSet("c"));
    assertEquals(ImmutableSet.of("a", "b", "c", "e"), graph.descendantIdSet("d"));
    assertEquals(ImmutableSet.of("a", "b", "c", "d"), graph.descendantIdSet("e"));
    assertEquals(ImmutableSet.of(), graph.descendantIdSet("f"));

    // =================================
    // roots (sources)
    // =================================

    assertEquals(ImmutableSet.of("f"), graph.rootIdSet());

    // =================================
    // leaves (sinks)
    // =================================

    assertEquals(ImmutableSet.of("f"), graph.leafIdSet());

  }

}
