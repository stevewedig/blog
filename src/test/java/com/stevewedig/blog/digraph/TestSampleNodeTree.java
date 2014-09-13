package com.stevewedig.blog.digraph;

import static com.stevewedig.blog.digraph.node.DownNodeLib.downNode;
import static com.stevewedig.blog.digraph.node.UpNodeLib.upNode;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.node.*;
import com.stevewedig.blog.digraph.node_graph.*;

// example dag with varying branch factors and path depths
//
// id -> parentIdSet...
// a ->
// b -> a
// c -> b
// d -> c
// e -> b
// f -> a
// g -> f
// h -> a
//
// childIdSet <- id...
// b, f, h <- a
// c, e <- b
// d <- c
// <- d
// <- e
// g <- f
// <- g
// <- h
public class TestSampleNodeTree {

  // ===========================================================================
  // tests
  // ===========================================================================

  @Test
  public void testTreeWithUpNodes() {

    // a ->
    // b -> a
    // c -> b
    // d -> c
    // e -> b
    // f -> a
    // g -> f
    // h -> a
    Tree<String, UpNode<String>> tree =
        TreeLib.up(upNode("a"), upNode("b", "a"), upNode("c", "b"), upNode("d", "c"),
            upNode("e", "b"), upNode("f", "a"), upNode("g", "f"), upNode("h", "a"));

    verifyNodeTree(tree);
  }

  @Test
  public void testTreeWithDownNodes() {

    // b, f, h <- a
    // c, e <- b
    // d <- c
    // <- d
    // <- e
    // g <- f
    // <- g
    // <- h
    Tree<String, DownNode<String>> tree =
        TreeLib.down(downNode("a", "b", "f", "h"), downNode("b", "c", "e"), downNode("c", "d"),
            downNode("d"), downNode("e"), downNode("f", "g"), downNode("g"), downNode("h"));

    verifyNodeTree(tree);

  }

  // ===========================================================================
  // verify Tree
  // ===========================================================================

  private <Node extends BaseNode<String>> void verifyNodeTree(Tree<String, Node> tree) {

    // =================================
    // idTree
    // =================================

    // verify the nested IdTree
    TestSampleIdTree.verifyIdTree(tree.idGraph());

    // verify that Tree properly implements IdTree
    TestSampleIdTree.verifyIdTree(tree);

    // =================================
    // id -> nodes
    // =================================

    Node a = tree.node("a");
    Node b = tree.node("b");
    Node c = tree.node("c");
    Node d = tree.node("d");
    Node e = tree.node("e");
    Node f = tree.node("f");
    Node g = tree.node("g");
    Node h = tree.node("h");

    ImmutableBiMap<String, Node> id__node =
        ImmutableBiMap.<String, Node>builder().put("a", a).put("b", b).put("c", c).put("d", d)
            .put("e", e).put("f", f).put("g", g).put("h", h).build();

    assertEquals(id__node, tree.id__node());

    // =================================
    // nodeSet
    // =================================

    @SuppressWarnings("unchecked")
    ImmutableSet<Node> nodeSet = ImmutableSet.of(a, b, c, d, e, f, g, h);

    assertEquals(nodeSet, tree.nodeSet());

    assertEquals(nodeSet.size(), tree.nodeSize());

    // =================================
    // unboundIds
    // =================================

    assertEquals(ImmutableSet.of(), tree.unboundIdSet());

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
    assertEquals(Optional.absent(), tree.parentNode("a"));
    assertEquals(Optional.of(a), tree.parentNode("b"));
    assertEquals(Optional.of(b), tree.parentNode("c"));
    assertEquals(Optional.of(c), tree.parentNode("d"));
    assertEquals(Optional.of(b), tree.parentNode("e"));
    assertEquals(Optional.of(a), tree.parentNode("f"));
    assertEquals(Optional.of(f), tree.parentNode("g"));
    assertEquals(Optional.of(a), tree.parentNode("h"));

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
    assertEquals(ImmutableSet.of(b, f, h), tree.childNodeSet("a"));
    assertEquals(ImmutableSet.of(c, e), tree.childNodeSet("b"));
    assertEquals(ImmutableSet.of(d), tree.childNodeSet("c"));
    assertEquals(ImmutableSet.of(), tree.childNodeSet("d"));
    assertEquals(ImmutableSet.of(), tree.childNodeSet("e"));
    assertEquals(ImmutableSet.of(g), tree.childNodeSet("f"));
    assertEquals(ImmutableSet.of(), tree.childNodeSet("g"));
    assertEquals(ImmutableSet.of(), tree.childNodeSet("h"));

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
    assertEquals(ImmutableSet.of(), tree.ancestorNodeSet("a", false));
    assertEquals(ImmutableSet.of(a), tree.ancestorNodeSet("b", false));
    assertEquals(ImmutableSet.of(a, b), tree.ancestorNodeSet("c", false));
    assertEquals(ImmutableSet.of(a, b, c), tree.ancestorNodeSet("d", false));
    assertEquals(ImmutableSet.of(a, b), tree.ancestorNodeSet("e", false));
    assertEquals(ImmutableSet.of(a), tree.ancestorNodeSet("f", false));
    assertEquals(ImmutableSet.of(a, f), tree.ancestorNodeSet("g", false));
    assertEquals(ImmutableSet.of(a), tree.ancestorNodeSet("h", false));

    // ancestor list
    assertEquals(ImmutableList.of(), tree.ancestorNodeList("a", false));
    assertEquals(ImmutableList.of(a), tree.ancestorNodeList("b", false));
    assertEquals(ImmutableList.of(a, b), tree.ancestorNodeList("c", false));
    assertEquals(ImmutableList.of(a, b, c), tree.ancestorNodeList("d", false));
    assertEquals(ImmutableList.of(a, b), tree.ancestorNodeList("e", false));
    assertEquals(ImmutableList.of(a), tree.ancestorNodeList("f", false));
    assertEquals(ImmutableList.of(a, f), tree.ancestorNodeList("g", false));
    assertEquals(ImmutableList.of(a), tree.ancestorNodeList("h", false));

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
    @SuppressWarnings("unchecked")
    ImmutableSet<Node> aDescendants = ImmutableSet.of(b, c, d, e, f, g, h);
    assertEquals(aDescendants, tree.descendantNodeSet("a", false));
    assertEquals(ImmutableSet.of(c, d, e), tree.descendantNodeSet("b", false));
    assertEquals(ImmutableSet.of(d), tree.descendantNodeSet("c", false));
    assertEquals(ImmutableSet.of(), tree.descendantNodeSet("d", false));
    assertEquals(ImmutableSet.of(), tree.descendantNodeSet("e", false));
    assertEquals(ImmutableSet.of(g), tree.descendantNodeSet("f", false));
    assertEquals(ImmutableSet.of(), tree.descendantNodeSet("g", false));
    assertEquals(ImmutableSet.of(), tree.descendantNodeSet("h", false));

    // =================================
    // root (source)
    // =================================

    assertEquals(a, tree.rootNode());

    // =================================
    // leaves (sinks)
    // =================================

    assertEquals(ImmutableSet.of(d, e, g, h), tree.leafNodeSet());

    // =================================
    // topological sort
    // =================================

    verifyTopsort(tree);

    // =================================
    // depth first
    // =================================

    @SuppressWarnings("unchecked")
    ImmutableSet<ImmutableList<Node>> depthNodeLists =
        ImmutableSet.of(ImmutableList.of(a, b, c, d, e, f, g, h),
            ImmutableList.of(a, b, c, d, e, h, f, g), ImmutableList.of(a, b, e, c, d, f, g, h),
            ImmutableList.of(a, b, e, c, d, h, f, g), ImmutableList.of(a, f, g, b, c, d, e, h),
            ImmutableList.of(a, f, g, b, e, c, d, h), ImmutableList.of(a, f, g, h, b, c, d, e),
            ImmutableList.of(a, f, g, h, b, e, c, e), ImmutableList.of(a, h, b, c, d, e, f, g),
            ImmutableList.of(a, h, b, e, c, d, f, g), ImmutableList.of(a, h, f, a, b, c, d, e),
            ImmutableList.of(a, h, f, a, b, e, c, d));

    assertTrue(depthNodeLists.contains(tree.depthNodeList()));

    // =================================
    // breadth first
    // =================================

    verifyBreadth(tree);

    // =================================
    // implementing set
    // =================================

    assertEquals(nodeSet.size(), tree.size());
    assertFalse(tree.isEmpty());

    assertTrue(tree.contains(a));
    assertFalse(tree.contains(new Object()));

    assertTrue(tree.containsAll(nodeSet));
    assertFalse(tree.containsAll(ImmutableSet.of(a, new Object())));

    assertEquals(tree.nodeSet(), ImmutableSet.copyOf(tree.iterator()));
    assertEquals(tree.nodeSet(), ImmutableSet.copyOf(tree.toArray()));
    assertEquals(tree.nodeSet(), ImmutableSet.copyOf(tree));
  }

  // ===========================================================================
  // helpers
  // ===========================================================================

  private static <Id, Node extends BaseNode<Id>> void verifyTopsort(Tree<Id, Node> tree) {

    Set<Id> found = new HashSet<>();

    for (Node node : tree.topsortNodeList()) {

      assertTrue(Sets.difference(tree.parentIdSet(node.id()), found).isEmpty());

      found.add(node.id());
    }
  }

  private static <Id, Node extends BaseNode<Id>> void verifyBreadth(Tree<Id, Node> tree) {

    int currentDepth = 0;

    for (Node node : tree.breadthNodeList()) {

      int depth = tree.depth(node.id());

      assertTrue(depth >= currentDepth);

      currentDepth = depth;
    }
  }
}
