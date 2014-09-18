package com.stevewedig.blog.digraph;

import static com.stevewedig.blog.translate.FormatLib.parseList;
import static com.stevewedig.blog.translate.FormatLib.parseMultimap;
import static com.stevewedig.blog.translate.FormatLib.parseSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.stevewedig.blog.digraph.id_graph.IdDagLib;
import com.stevewedig.blog.digraph.id_graph.IdTree;
import com.stevewedig.blog.digraph.id_graph.IdTreeLib;
import com.stevewedig.blog.errors.NotThrown;

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

  private static ImmutableSet<String> idSet = parseSet("a, b, c, d, e, f, g, h");

  // ===========================================================================
  // graphs
  // ===========================================================================

  private static IdTree<String> idTreeFromParentMap() {

    return IdTreeLib.fromParentMap(idSet, getParentMap());
  }

  private static Multimap<String, String> getParentMap() {

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

    return id__parentIds;
  }

  // ===================================

  private static IdTree<String> idTreeFromChildMap() {

    return IdTreeLib.fromChildMap(idSet, getChildMap());
  }

  private static Multimap<String, String> getChildMap() {

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

    return id__childIds;
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
    // ids
    // =================================

    assertEquals(idSet, tree.idSet());

    assertEquals(idSet.size(), tree.idSize());

    tree.assertIdsEqual(idSet);

    try {
      tree.assertIdsEqual(parseSet("xxx"));
      throw new NotThrown(AssertionError.class);
    } catch (AssertionError e) {
    }

    assertEquals(IdDagLib.fromParentMap(parseMultimap("b = a, f = a")),
        tree.filterIdGraph(parseSet("a, b, f")));

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

    assertEquals(getParentMap(), tree.id__parentIds());

    assertTrue(tree.isParentOf("a", "b"));
    assertFalse(tree.isParentOf("b", "a"));

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

    assertEquals(getChildMap(), tree.id__childIds());

    assertFalse(tree.isChildOf("a", "b"));
    assertTrue(tree.isChildOf("b", "a"));

    assertEquals(parseSet("b, f, h"), tree.childIdSet("a"));
    assertEquals(parseSet("c, e"), tree.childIdSet("b"));
    assertEquals(parseSet("d"), tree.childIdSet("c"));
    assertEquals(parseSet(""), tree.childIdSet("d"));
    assertEquals(parseSet(""), tree.childIdSet("e"));
    assertEquals(parseSet("g"), tree.childIdSet("f"));
    assertEquals(parseSet(""), tree.childIdSet("g"));
    assertEquals(parseSet(""), tree.childIdSet("h"));

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

    // ancestor set, not inclusive
    assertEquals(parseSet(""), tree.ancestorIdSet("a", false));
    assertEquals(parseSet("a"), tree.ancestorIdSet("b", false));
    assertEquals(parseSet("a, b"), tree.ancestorIdSet("c", false));
    assertEquals(parseSet("a, b, c"), tree.ancestorIdSet("d", false));
    assertEquals(parseSet("a, b"), tree.ancestorIdSet("e", false));
    assertEquals(parseSet("a"), tree.ancestorIdSet("f", false));
    assertEquals(parseSet("a, f"), tree.ancestorIdSet("g", false));
    assertEquals(parseSet("a"), tree.ancestorIdSet("h", false));

    // ancestor set, inclusive
    assertEquals(parseSet("a"), tree.ancestorIdSet("a", true));
    assertEquals(parseSet("a, b"), tree.ancestorIdSet("b", true));
    assertEquals(parseSet("a, b, c"), tree.ancestorIdSet("c", true));
    assertEquals(parseSet("a, b, c, d"), tree.ancestorIdSet("d", true));
    assertEquals(parseSet("a, b, e"), tree.ancestorIdSet("e", true));
    assertEquals(parseSet("a, f"), tree.ancestorIdSet("f", true));
    assertEquals(parseSet("a, f, g"), tree.ancestorIdSet("g", true));
    assertEquals(parseSet("a, h"), tree.ancestorIdSet("h", true));

    // ancestor list, not inclusive
    assertEquals(parseList(""), tree.ancestorIdList("a", false));
    assertEquals(parseList("a"), tree.ancestorIdList("b", false));
    assertEquals(parseList("a, b"), tree.ancestorIdList("c", false));
    assertEquals(parseList("a, b, c"), tree.ancestorIdList("d", false));
    assertEquals(parseList("a, b"), tree.ancestorIdList("e", false));
    assertEquals(parseList("a"), tree.ancestorIdList("f", false));
    assertEquals(parseList("a, f"), tree.ancestorIdList("g", false));
    assertEquals(parseList("a"), tree.ancestorIdList("h", false));

    // ancestor list, inclusive
    assertEquals(parseList("a"), tree.ancestorIdList("a", true));
    assertEquals(parseList("a, b"), tree.ancestorIdList("b", true));
    assertEquals(parseList("a, b, c"), tree.ancestorIdList("c", true));
    assertEquals(parseList("a, b, c, d"), tree.ancestorIdList("d", true));
    assertEquals(parseList("a, b, e"), tree.ancestorIdList("e", true));
    assertEquals(parseList("a, f"), tree.ancestorIdList("f", true));
    assertEquals(parseList("a, f, g"), tree.ancestorIdList("g", true));
    assertEquals(parseList("a, h"), tree.ancestorIdList("h", true));

    // ancestor graph, not inclusive
    assertEquals(IdTreeLib.fromParentMap("b", "a"), tree.ancestorIdGraph(parseSet("c"), false));

    // ancestor graph, inclusive
    assertEquals(idTreeFromParentMap(), tree.ancestorIdGraph(parseSet("d, e, g, h"), true));

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
    assertEquals(parseSet("b, c, d, e, f, g, h"), tree.descendantIdSet("a", false));
    assertEquals(parseSet("c, d, e"), tree.descendantIdSet("b", false));
    assertEquals(parseSet("d"), tree.descendantIdSet("c", false));
    assertEquals(parseSet(""), tree.descendantIdSet("d", false));
    assertEquals(parseSet(""), tree.descendantIdSet("e", false));
    assertEquals(parseSet("g"), tree.descendantIdSet("f", false));
    assertEquals(parseSet(""), tree.descendantIdSet("g", false));
    assertEquals(parseSet(""), tree.descendantIdSet("h", false));

    // descendant graph, not inclusive
    assertEquals(IdDagLib.fromParentMap(parseSet("c, d, e"), "d", "c"),
        tree.descendantIdGraph(parseSet("b"), false));

    // descendant graph, inclusive
    assertEquals(IdDagLib.fromParentMap("d", "c"), tree.descendantIdGraph(parseSet("c"), true));

    // descendant tree
    assertEquals(IdTreeLib.fromParentMap("d", "c"), tree.descendantIdTree("c"));
    assertEquals(idTreeFromParentMap(), tree.descendantIdTree("a"));

    // =================================
    // root (source)
    // =================================

    assertFalse(tree.isRoot("e"));
    assertTrue(tree.isRoot("a"));

    assertEquals("a", tree.rootId());

    // =================================
    // leaves (sinks)
    // =================================

    assertFalse(tree.isLeaf("a"));
    assertTrue(tree.isLeaf("e"));

    assertEquals(parseSet("d, e, g, h"), tree.leafIdSet());

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

    // =================================
    // generic traversal
    // =================================

    // TODO
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
