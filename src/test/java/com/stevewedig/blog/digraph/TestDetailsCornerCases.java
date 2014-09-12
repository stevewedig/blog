package com.stevewedig.blog.digraph;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.collect.*;
import com.stevewedig.blog.digraph.id_graph.*;

public class TestDetailsCornerCases {

  @Test
  public void testCornerCase__zeroNodes() {

    IdGraph<Object> graph = IdGraphLib.fromParentMap(ImmutableSetMultimap.of());
    assertTrue(graph.idSet().isEmpty());
    assertTrue(graph.id__parentIds().isEmpty());

    IdDag<Object> dag = IdDagLib.fromParentMap(ImmutableSetMultimap.of());
    assertTrue(dag.idSet().isEmpty());
    assertTrue(dag.id__parentIds().isEmpty());

  }

  @Test
  public void testCornerCase__oneNode() {

    IdGraph<String> graph =
        IdGraphLib.fromParentMap(ImmutableSet.of("a"), ImmutableSetMultimap.<String, String>of());
    assertEquals(ImmutableSet.of("a"), graph.idSet());

    IdDag<String> dag =
        IdDagLib.fromParentMap(ImmutableSet.of("a"), ImmutableSetMultimap.<String, String>of());
    assertEquals(ImmutableSet.of("a"), dag.idSet());

    IdTree<String> tree =
        IdTreeLib.fromParentMap(ImmutableSet.of("a"), ImmutableSetMultimap.<String, String>of());
    assertEquals("a", tree.rootId());

  }

  @Test
  public void testCornerCase__cyclic() {

    IdGraph<String> one = IdGraphLib.fromParentMap(ImmutableSetMultimap.of("a", "a"));
    assertTrue(one.containsCycle());
    assertFalse(one.optionalTopsortIdList().isPresent());

    IdGraph<String> two = IdGraphLib.fromParentMap(ImmutableSetMultimap.of("a", "b", "b", "a"));
    assertTrue(two.containsCycle());
    assertFalse(two.optionalTopsortIdList().isPresent());

    IdGraph<String> three =
        IdGraphLib.fromParentMap(ImmutableSetMultimap.of("a", "b", "b", "c", "c", "a"));
    assertTrue(three.containsCycle());
    assertFalse(three.optionalTopsortIdList().isPresent());

  }

  @Test
  public void testCornerCase__detatched() {

    IdGraph<String> graph = IdGraphLib.fromParentMap(ImmutableSetMultimap.of("b", "a", "c", "c"));
    assertEquals(ImmutableSet.of("a", "b", "c"), graph.idSet());
    assertEquals(ImmutableSet.of("a"), graph.rootIdSet());
    assertTrue(graph.containsCycle());

    IdDag<String> dag = IdDagLib.fromParentMap(ImmutableSetMultimap.of("b", "a", "d", "c"));
    assertEquals(ImmutableSet.of("a", "b", "c", "d"), dag.idSet());
    assertEquals(ImmutableSet.of("a", "c"), dag.rootIdSet());

  }

}
