package com.stevewedig.blog.digraph.errors;

import static com.stevewedig.blog.translate.FormatLib.parseList;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.id_graph.*;

// example dag with varying branch factors and path depths
//
// id -> parentIds...
// a ->
// b -> a
// c -> b
// d -> c
// e -> b
// f -> a
// g -> f
// h -> a
//
// childIds <- id...
// b, f, h <- a
// c, e <- b
// d <- c
// <- d
// <- e
// g <- f
// <- g
// <- h
public class TestSampleIdTree {

  // ===========================================================================
  // ids
  // ===========================================================================

  private static ImmutableSet<String> idSet = ImmutableSet.of("a", "b", "c", "d", "e", "f", "g",
      "h");

  // ===========================================================================
  // graphs
  // ===========================================================================

  private static IdTree<String> idTreeFromParentMap() {

    Multimap<String, String> id__parentIds = HashMultimap.create();

    // a ->
    // b -> a
    // c -> b
    // d -> c
    // e -> b
    // f -> a
    // g -> f
    // h -> a
    id__parentIds.put("b", "a");
    id__parentIds.put("c", "b");
    id__parentIds.put("d", "c");
    id__parentIds.put("e", "b");
    id__parentIds.put("f", "a");
    id__parentIds.put("g", "f");
    id__parentIds.put("h", "a");

    return IdTreeLib.fromParentMap(idSet, id__parentIds);
  }

  private static IdTree<String> idTreeFromChildMap() {

    Multimap<String, String> id__childIds = HashMultimap.create();

    // b, f, h <- a
    // c, e <- b
    // d <- c
    // <- d
    // <- e
    // g <- f
    // <- g
    // <- h
    id__childIds.put("a", "b");
    id__childIds.put("a", "f");
    id__childIds.put("a", "h");
    id__childIds.put("b", "c");
    id__childIds.put("b", "e");
    id__childIds.put("c", "d");
    id__childIds.put("f", "g");

    return IdTreeLib.fromChildMap(idSet, id__childIds);
  }

  // ===========================================================================
  // tests
  // ===========================================================================

  @Test
  public void testIdTreeFromParentMap() {

    verifyIdTree(idTreeFromParentMap());
  }

  @Test
  public void testIdTreeFromChildMap() {

    verifyIdTree(idTreeFromChildMap());
  }

  // ===========================================================================
  // verify tree
  // ===========================================================================

  public static void verifyIdTree(IdTree<String> tree) {

    // =================================
    // idSet
    // =================================

    assertEquals(idSet, tree.idSet());
    assertEquals(idSet.size(), tree.idSize());

    // =================================
    // parents
    // =================================

    // a ->
    // b -> a
    // c -> b
    // d -> c
    // e -> b
    // f -> a
    // g -> f
    // h -> a
    assertEquals(Optional.absent(), tree.parentId("a"));
    assertEquals(Optional.of("a"), tree.parentId("b"));
    assertEquals(Optional.of("b"), tree.parentId("c"));
    assertEquals(Optional.of("c"), tree.parentId("d"));
    assertEquals(Optional.of("b"), tree.parentId("e"));
    assertEquals(Optional.of("a"), tree.parentId("f"));
    assertEquals(Optional.of("f"), tree.parentId("g"));
    assertEquals(Optional.of("a"), tree.parentId("h"));

    // =================================
    // children
    // =================================

    // b, f, h <- a
    // c, e <- b
    // d <- c
    // <- d
    // <- e
    // g <- f
    // <- g
    // <- h
    assertEquals(ImmutableSet.of("b", "f", "h"), tree.childIdSet("a"));
    assertEquals(ImmutableSet.of("c", "e"), tree.childIdSet("b"));
    assertEquals(ImmutableSet.of("d"), tree.childIdSet("c"));
    assertEquals(ImmutableSet.of(), tree.childIdSet("d"));
    assertEquals(ImmutableSet.of(), tree.childIdSet("e"));
    assertEquals(ImmutableSet.of("g"), tree.childIdSet("f"));
    assertEquals(ImmutableSet.of(), tree.childIdSet("g"));
    assertEquals(ImmutableSet.of(), tree.childIdSet("h"));

    // =================================
    // ancestors
    // =================================

    // a ->
    // b -> a
    // c -> b
    // d -> c
    // e -> b
    // f -> a
    // g -> f
    // h -> a

    // ancestor set
    assertEquals(ImmutableSet.of(), tree.ancestorIdSet("a"));
    assertEquals(ImmutableSet.of("a"), tree.ancestorIdSet("b"));
    assertEquals(ImmutableSet.of("a", "b"), tree.ancestorIdSet("c"));
    assertEquals(ImmutableSet.of("a", "b", "c"), tree.ancestorIdSet("d"));
    assertEquals(ImmutableSet.of("a", "b"), tree.ancestorIdSet("e"));
    assertEquals(ImmutableSet.of("a"), tree.ancestorIdSet("f"));
    assertEquals(ImmutableSet.of("a", "f"), tree.ancestorIdSet("g"));
    assertEquals(ImmutableSet.of("a"), tree.ancestorIdSet("h"));

    // ancestor list
    assertEquals(ImmutableList.of(), tree.ancestorIdList("a"));
    assertEquals(ImmutableList.of("a"), tree.ancestorIdList("b"));
    assertEquals(ImmutableList.of("a", "b"), tree.ancestorIdList("c"));
    assertEquals(ImmutableList.of("a", "b", "c"), tree.ancestorIdList("d"));
    assertEquals(ImmutableList.of("a", "b"), tree.ancestorIdList("e"));
    assertEquals(ImmutableList.of("a"), tree.ancestorIdList("f"));
    assertEquals(ImmutableList.of("a", "f"), tree.ancestorIdList("g"));
    assertEquals(ImmutableList.of("a"), tree.ancestorIdList("h"));

    // depth
    assertEquals(0, tree.depth("a"));
    assertEquals(1, tree.depth("b"));
    assertEquals(2, tree.depth("c"));
    assertEquals(3, tree.depth("d"));
    assertEquals(2, tree.depth("e"));
    assertEquals(1, tree.depth("f"));
    assertEquals(2, tree.depth("g"));
    assertEquals(1, tree.depth("h"));

    // maxDepth
    assertEquals(3, tree.maxDepth());

    // =================================
    // descendants
    // =================================

    // b, f, h <- a
    // c, e <- b
    // d <- c
    // <- d
    // <- e
    // g <- f
    // <- g
    // <- h
    assertEquals(ImmutableSet.of("b", "c", "d", "e", "f", "g", "h"), tree.descendantIdSet("a"));
    assertEquals(ImmutableSet.of("c", "d", "e"), tree.descendantIdSet("b"));
    assertEquals(ImmutableSet.of("d"), tree.descendantIdSet("c"));
    assertEquals(ImmutableSet.of(), tree.descendantIdSet("d"));
    assertEquals(ImmutableSet.of(), tree.descendantIdSet("e"));
    assertEquals(ImmutableSet.of("g"), tree.descendantIdSet("f"));
    assertEquals(ImmutableSet.of(), tree.descendantIdSet("g"));
    assertEquals(ImmutableSet.of(), tree.descendantIdSet("h"));

    // =================================
    // root (source)
    // =================================

    assertEquals("a", tree.rootId());

    // =================================
    // leaves (sinks)
    // =================================

    assertEquals(ImmutableSet.of("d", "e", "g", "h"), tree.leafIdSet());

    // =================================
    // topological sort
    // =================================

    assertFalse(tree.containsCycle());

    verifyTopsort(tree);

    // =================================
    // depth first
    // =================================

    @SuppressWarnings("unchecked")
    ImmutableSet<ImmutableList<String>> depthIdLists =
        ImmutableSet.of(parseList("a, b, c, d, e, f, g, h"), parseList("a, b, c, d, e, h, f, g"),
            parseList("a, b, e, c, d, f, g, h"), parseList("a, b, e, c, d, h, f, g"),
            parseList("a, f, g, b, c, d, e, h"), parseList("a, f, g, b, e, c, d, h"),
            parseList("a, f, g, h, b, c, d, e"), parseList("a, f, g, h, b, e, c, e"),
            parseList("a, h, b, c, d, e, f, g"), parseList("a, h, b, e, c, d, f, g"),
            parseList("a, h, f, a, b, c, d, e"), parseList("a, h, f, a, b, e, c, d"));

    assertTrue(depthIdLists.contains(tree.depthIdList()));

    // =================================
    // breadth first
    // =================================

    verifyBreadth(tree);

  }

  // ===========================================================================
  // helpers
  // ===========================================================================

  private static <Id> void verifyTopsort(IdTree<Id> tree) {

    Set<Id> found = new HashSet<>();

    for (Id id : tree.topsortIdList()) {

      assertTrue(Sets.difference(tree.parentIdSet(id), found).isEmpty());

      found.add(id);
    }
  }

  private static <Id> void verifyBreadth(IdTree<Id> tree) {

    int currentDepth = 0;

    for (Id id : tree.breadthIdList()) {

      int depth = tree.depth(id);

      assertTrue(depth >= currentDepth);

      currentDepth = depth;
    }
  }


}
