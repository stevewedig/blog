package com.stevewedig.blog.digraph;

import static com.stevewedig.blog.digraph.node.DownNodeLib.downNode;
import static com.stevewedig.blog.digraph.node.UpNodeLib.upNode;
import static com.stevewedig.blog.translate.FormatLib.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.node.*;
import com.stevewedig.blog.digraph.node_graph.*;
import com.stevewedig.blog.util.LambdaLib.Fn1;

// example graph containing cycles (a->b->c->d->a, and a->e->a)
//
// id -> parentIdSet...
// a -> d, e
// b -> a
// c -> b
// d -> c
// e -> a
// f ->
//
// childIdSet <- id...
// b, e <- a
// c <- b
// d <- c
// a <- d
// a <- e
// <- f
public class TestSampleNodeGraph {

  // ===========================================================================
  // tests
  // ===========================================================================

  @Test
  public void testGraphWithUpNodes() {

    // a -> d, e
    // b -> a
    // c -> b
    // d -> c
    // e -> a
    // f ->
    Graph<String, UpNode<String>> graph =
        GraphLib.up(upNode("a", "d", "e"), upNode("b", "a"), upNode("c", "b"), upNode("d", "c"),
            upNode("e", "a"), upNode("f"));

    verifyNodeGraph(graph);
  }

  @Test
  public void testGraphWithDownNodes() {

    // b, e <- a
    // c <- b
    // d <- c
    // a <- d
    // a <- e
    // <- f
    Graph<String, DownNode<String>> graph =
        GraphLib.down(downNode("a", "b", "e"), downNode("b", "c"), downNode("c", "d"),
            downNode("d", "a"), downNode("e", "a"), downNode("f"));

    verifyNodeGraph(graph);
  }

  // ===========================================================================
  // verify graph
  // ===========================================================================

  private <Node> void verifyNodeGraph(Graph<String, Node> graph) {

    // =================================
    // nested idGraph
    // =================================

    // verify the nested IdGraph
    TestSampleIdGraph.verifyIdGraph(graph.idGraph());

    // verify that Graph properly implements IdGraph
    TestSampleIdGraph.verifyIdGraph(graph);

    // =================================
    // node map
    // =================================

    Node a = graph.getNode("a");
    Node b = graph.getNode("b");
    Node c = graph.getNode("c");
    Node d = graph.getNode("d");
    Node e = graph.getNode("e");
    Node f = graph.getNode("f");

    assertEquals("a", graph.getId(a));

    assertTrue(graph.containsNodeForId("a"));

    // node map
    ImmutableBiMap<String, Node> id__node =
        ImmutableBiMap.<String, Node>builder().put("a", a).put("b", b).put("c", c).put("d", d)
            .put("e", e).put("f", f).build();
    assertEquals(id__node, graph.id__node());

    // unboundIds (nodes without ids)
    assertEquals(ImmutableSet.of(), graph.unboundIdSet());

    // =================================
    // nodes
    // =================================

    @SuppressWarnings("unchecked")
    ImmutableSet<Node> nodeSet = ImmutableSet.of(a, b, c, d, e, f);

    assertEquals(nodeSet, graph.nodeSet());

    assertEquals(nodeSet.size(), graph.nodeSize());

    // =================================
    // parents
    // =================================

    // a -> d, e
    // b -> a
    // c -> b
    // d -> c
    // e -> a
    // f ->

    assertEquals(ImmutableSet.of(d, e), graph.parentNodeSet("a"));
    assertEquals(ImmutableSet.of(a), graph.parentNodeSet("b"));
    assertEquals(ImmutableSet.of(b), graph.parentNodeSet("c"));
    assertEquals(ImmutableSet.of(c), graph.parentNodeSet("d"));
    assertEquals(ImmutableSet.of(a), graph.parentNodeSet("e"));
    assertEquals(ImmutableSet.of(), graph.parentNodeSet("f"));

    // =================================
    // children
    // =================================

    // b, e <- a
    // c <- b
    // d <- c
    // a <- d
    // a <- e
    // <- f

    assertEquals(ImmutableSet.of(b, e), graph.childNodeSet("a"));
    assertEquals(ImmutableSet.of(c), graph.childNodeSet("b"));
    assertEquals(ImmutableSet.of(d), graph.childNodeSet("c"));
    assertEquals(ImmutableSet.of(a), graph.childNodeSet("d"));
    assertEquals(ImmutableSet.of(a), graph.childNodeSet("e"));
    assertEquals(ImmutableSet.of(), graph.childNodeSet("f"));

    // =================================
    // ancestors
    // =================================

    // a -> d, e
    // b -> a
    // c -> b
    // d -> c
    // e -> a
    // f ->

    // not inclusive
    assertEquals(ImmutableSet.of(b, c, d, e), graph.ancestorNodeSet("a", false));
    assertEquals(ImmutableSet.of(a, c, d, e), graph.ancestorNodeSet("b", false));
    assertEquals(ImmutableSet.of(a, b, d, e), graph.ancestorNodeSet("c", false));
    assertEquals(ImmutableSet.of(a, b, c, e), graph.ancestorNodeSet("d", false));
    assertEquals(ImmutableSet.of(a, b, c, d), graph.ancestorNodeSet("e", false));
    assertEquals(ImmutableSet.of(), graph.ancestorNodeSet("f", false));

    // inclusive
    assertEquals(ImmutableSet.of(a, b, c, d, e), graph.ancestorNodeSet("a", true));
    assertEquals(ImmutableSet.of(a, b, c, d, e), graph.ancestorNodeSet("b", true));
    assertEquals(ImmutableSet.of(a, b, c, d, e), graph.ancestorNodeSet("c", true));
    assertEquals(ImmutableSet.of(a, b, c, d, e), graph.ancestorNodeSet("d", true));
    assertEquals(ImmutableSet.of(a, b, c, d, e), graph.ancestorNodeSet("e", true));
    assertEquals(ImmutableSet.of(f), graph.ancestorNodeSet("f", true));

    // =================================
    // descendants
    // =================================

    // b, e <- a
    // c <- b
    // d <- c
    // a <- d
    // a <- e
    // <- f

    // not inclusive
    assertEquals(ImmutableSet.of(b, c, d, e), graph.descendantNodeSet("a", false));
    assertEquals(ImmutableSet.of(a, c, d, e), graph.descendantNodeSet("b", false));
    assertEquals(ImmutableSet.of(a, b, d, e), graph.descendantNodeSet("c", false));
    assertEquals(ImmutableSet.of(a, b, c, e), graph.descendantNodeSet("d", false));
    assertEquals(ImmutableSet.of(a, b, c, d), graph.descendantNodeSet("e", false));
    assertEquals(ImmutableSet.of(), graph.descendantNodeSet("f", false));

    // inclusive
    assertEquals(ImmutableSet.of(a, b, c, d, e), graph.descendantNodeSet("a", true));
    assertEquals(ImmutableSet.of(a, b, c, d, e), graph.descendantNodeSet("b", true));
    assertEquals(ImmutableSet.of(a, b, c, d, e), graph.descendantNodeSet("c", true));
    assertEquals(ImmutableSet.of(a, b, c, d, e), graph.descendantNodeSet("d", true));
    assertEquals(ImmutableSet.of(a, b, c, d, e), graph.descendantNodeSet("e", true));
    assertEquals(ImmutableSet.of(f), graph.descendantNodeSet("f", true));

    // =================================
    // roots (sources)
    // =================================

    assertEquals(ImmutableSet.of(f), graph.rootNodeSet());

    // =================================
    // leaves (sinks)
    // =================================

    assertEquals(ImmutableSet.of(f), graph.leafNodeSet());

    // =================================
    // topological sort
    // =================================

    assertFalse(graph.optionalTopsortNodeList().isPresent());

    // =================================
    // generic traversal
    // =================================

    verifyGenericTraversal(graph, a, b, c, d, e);

    // =================================
    // transforming ids into nodes
    // =================================

    assertEquals(ImmutableSet.of(a, b), graph.transformSet(parseSet("a, b")));

    assertEquals(ImmutableList.of(a, b), graph.transformList(parseList("a, b")));

    assertEquals(Optional.of(a), graph.transformOptional(Optional.of("a")));
    assertEquals(Optional.absent(), graph.transformOptional(Optional.<String>absent()));

    // transformIterator & transformIterable not shown here

    // =================================
    // implementing set
    // =================================

    assertEquals(nodeSet.size(), graph.size());
    assertFalse(graph.isEmpty());

    assertTrue(graph.contains(a));
    assertFalse(graph.contains(new Object()));

    assertTrue(graph.containsAll(nodeSet));
    assertFalse(graph.containsAll(ImmutableSet.of(a, new Object())));

    assertEquals(graph.nodeSet(), ImmutableSet.copyOf(graph.iterator()));
    assertEquals(graph.nodeSet(), ImmutableSet.copyOf(graph.toArray()));
    assertEquals(graph.nodeSet(), ImmutableSet.copyOf(graph));

  }

  static <Node> void verifyGenericTraversal(final Graph<String, Node> graph, Node a, Node b,
      Node c, Node d, Node e) {

    Fn1<Node, List<String>> expand = new Fn1<Node, List<String>>() {
      @Override
      public List<String> apply(Node node) {
        switch (graph.getId(node)) {
          case "a":
            return parseList("b, e");
          case "b":
            return parseList("c");
          case "e":
            return parseList("a");
          default:
            return parseList("");
        }
      }
    };

    // depthFirst, inclusive, 1 start
    assertEquals(ImmutableList.of(a, b, c, e), graph.traverseNodeList(true, true, "a", expand));

    // depthFirst, not inclusive, 1 start
    assertEquals(ImmutableList.of(b, c, e), graph.traverseNodeList(true, false, "a", expand));;

    // breadthFirst, inclusive, 1 start
    assertEquals(ImmutableList.of(a, b, e, c), graph.traverseNodeList(false, true, "a", expand));

    // breadthFirst, not inclusive, 1 start
    assertEquals(ImmutableList.of(b, e, c), graph.traverseNodeList(false, false, "a", expand));

    // depthFirst, inclusive, n starts
    assertEquals(ImmutableList.of(d, a, b, c, e),
        graph.traverseNodeList(true, true, parseList("d, a"), expand));

    // depthFirst, not inclusive, n starts
    assertEquals(ImmutableList.of(b, c, e),
        graph.traverseNodeList(true, false, parseList("d, a"), expand));

    // breadthFirst, inclusive, n starts
    assertEquals(ImmutableList.of(d, a, b, e, c),
        graph.traverseNodeList(false, true, parseList("d, a"), expand));

    // breadthFirst, not inclusive, n starts
    assertEquals(ImmutableList.of(b, e, c),
        graph.traverseNodeList(false, false, parseList("d, a"), expand));

  }

}
