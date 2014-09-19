package com.stevewedig.blog.digraph;

import static com.stevewedig.blog.digraph.node.DownNodeLib.downNode;
import static com.stevewedig.blog.digraph.node.UpNodeLib.upNode;
import static com.stevewedig.blog.translate.FormatLib.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.node.*;
import com.stevewedig.blog.digraph.node_graph.*;

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
public class TestSampleNodeDag {

  // ===========================================================================
  // tests
  // ===========================================================================

  @Test
  public void testDagWithUpNodes() {

    // a ->
    // b -> a
    // c -> a
    // d -> b, c
    // e -> d
    Dag<String, UpNode<String>> dag =
        DagLib.up(upNode("a"), upNode("b", "a"), upNode("c", "a"), upNode("d", "b", "c"),
            upNode("e", "d"));

    verifyNodeDag(dag);
  }

  @Test
  public void testExampleDag__DownNodes() {

    // b, c <- a
    // d <- b
    // d <- c
    // e <- d
    // <- e
    Dag<String, DownNode<String>> dag =
        DagLib.down(downNode("a", "b", "c"), downNode("b", "d"), downNode("c", "d"),
            downNode("d", "e"), downNode("e"));

    verifyNodeDag(dag);
  }

  // ===========================================================================
  // verify dag
  // ===========================================================================

  private <Node> void verifyNodeDag(Dag<String, Node> dag) {

    // =================================
    // idDag
    // =================================

    // verify the nested IdDag
    TestSampleIdDag.verifyIdDag(dag.idGraph());

    // verify that Dag properly implements IdDag
    TestSampleIdDag.verifyIdDag(dag);

    // =================================
    // node map
    // =================================

    Node a = dag.getNode("a");
    Node b = dag.getNode("b");
    Node c = dag.getNode("c");
    Node d = dag.getNode("d");
    Node e = dag.getNode("e");

    assertEquals("a", dag.getId(a));

    assertTrue(dag.containsNodeForId("a"));

    // node map
    ImmutableBiMap<String, Node> id__node =
        ImmutableBiMap.<String, Node>of("a", a, "b", b, "c", c, "d", d, "e", e);
    assertEquals(id__node, dag.id__node());

    // unboundIds (ids without nodes)
    assertEquals(ImmutableSet.of(), dag.unboundIdSet());

    // =================================
    // nodes
    // =================================

    ImmutableSet<Node> nodeSet = ImmutableSet.of(a, b, c, d, e);

    assertEquals(nodeSet, dag.nodeSet());

    assertEquals(nodeSet.size(), dag.nodeSize());

    // =================================
    // parents
    // =================================

    // a ->
    // b -> a
    // c -> a
    // d -> b, c
    // e -> d

    assertEquals(ImmutableSet.of(), dag.parentNodeSet("a"));
    assertEquals(ImmutableSet.of(a), dag.parentNodeSet("b"));
    assertEquals(ImmutableSet.of(a), dag.parentNodeSet("c"));
    assertEquals(ImmutableSet.of(b, c), dag.parentNodeSet("d"));
    assertEquals(ImmutableSet.of(d), dag.parentNodeSet("e"));

    // =================================
    // children
    // =================================

    // b, c <- a
    // d <- b
    // d <- c
    // e <- d
    // <- e

    assertEquals(ImmutableSet.of(b, c), dag.childNodeSet("a"));
    assertEquals(ImmutableSet.of(d), dag.childNodeSet("b"));
    assertEquals(ImmutableSet.of(d), dag.childNodeSet("c"));
    assertEquals(ImmutableSet.of(e), dag.childNodeSet("d"));
    assertEquals(ImmutableSet.of(), dag.childNodeSet("e"));

    // =================================
    // ancestors
    // =================================

    // a ->
    // b -> a
    // c -> a
    // d -> b, c
    // e -> d

    // not inclusive
    assertEquals(ImmutableSet.of(), dag.ancestorNodeSet("a", false));
    assertEquals(ImmutableSet.of(a), dag.ancestorNodeSet("b", false));
    assertEquals(ImmutableSet.of(a), dag.ancestorNodeSet("c", false));
    assertEquals(ImmutableSet.of(a, b, c), dag.ancestorNodeSet("d", false));
    assertEquals(ImmutableSet.of(a, b, c, d), dag.ancestorNodeSet("e", false));

    // inclusive
    assertEquals(ImmutableSet.of(a), dag.ancestorNodeSet("a", true));
    assertEquals(ImmutableSet.of(a, b), dag.ancestorNodeSet("b", true));
    assertEquals(ImmutableSet.of(a, c), dag.ancestorNodeSet("c", true));
    assertEquals(ImmutableSet.of(a, b, c, d), dag.ancestorNodeSet("d", true));
    assertEquals(ImmutableSet.of(a, b, c, d, e), dag.ancestorNodeSet("e", true));

    // =================================
    // descendants
    // =================================

    // b, c <- a
    // d <- b
    // d <- c
    // e <- d
    // <- e

    // not inclusive
    assertEquals(ImmutableSet.of(b, c, d, e), dag.descendantNodeSet("a", false));
    assertEquals(ImmutableSet.of(d, e), dag.descendantNodeSet("b", false));
    assertEquals(ImmutableSet.of(d, e), dag.descendantNodeSet("c", false));
    assertEquals(ImmutableSet.of(e), dag.descendantNodeSet("d", false));
    assertEquals(ImmutableSet.of(), dag.descendantNodeSet("e", false));

    // inclusive
    assertEquals(ImmutableSet.of(a, b, c, d, e), dag.descendantNodeSet("a", true));
    assertEquals(ImmutableSet.of(b, d, e), dag.descendantNodeSet("b", true));
    assertEquals(ImmutableSet.of(c, d, e), dag.descendantNodeSet("c", true));
    assertEquals(ImmutableSet.of(d, e), dag.descendantNodeSet("d", true));
    assertEquals(ImmutableSet.of(e), dag.descendantNodeSet("e", true));

    // =================================
    // roots (sources)
    // =================================

    assertEquals(ImmutableSet.of(a), dag.rootNodeSet());

    // =================================
    // leaves (sinks)
    // =================================

    assertEquals(ImmutableSet.of(e), dag.leafNodeSet());

    // =================================
    // topological sort
    // =================================

    ImmutableSet<ImmutableList<Node>> topsortNodeLists =
        ImmutableSet.of(ImmutableList.of(a, b, c, d, e), ImmutableList.of(a, c, b, d, e));

    assertTrue(topsortNodeLists.contains(dag.topsortNodeList()));

    // same instance
    assertTrue(dag.topsortNodeList() == dag.optionalTopsortNodeList().get());

    // =================================
    // depth first
    // =================================

    ImmutableSet<ImmutableList<Node>> depthNodeLists =
        ImmutableSet.of(ImmutableList.of(a, b, d, e, c), ImmutableList.of(a, c, d, e, b));

    assertTrue(depthNodeLists.contains(dag.depthNodeList()));

    // =================================
    // breadth first
    // =================================

    ImmutableSet<ImmutableList<Node>> breadthNodeLists =
        ImmutableSet.of(ImmutableList.of(a, b, c, d, e), ImmutableList.of(a, c, b, d, e));

    assertTrue(breadthNodeLists.contains(dag.breadthNodeList()));

    // =================================
    // generic traversal
    // =================================

    // TODO

    // =================================
    // transforming ids into nodes
    // =================================

    assertEquals(ImmutableSet.of(a, b), dag.transformSet(parseSet("a, b")));

    assertEquals(ImmutableList.of(a, b), dag.transformList(parseList("a, b")));

    assertEquals(Optional.of(a), dag.transformOptional(Optional.of("a")));
    assertEquals(Optional.absent(), dag.transformOptional(Optional.<String>absent()));

    // transformIterator & transformIterable not shown here

    // =================================
    // implementing set
    // =================================

    assertEquals(nodeSet.size(), dag.size());
    assertFalse(dag.isEmpty());

    assertTrue(dag.contains(a));
    assertFalse(dag.contains(new Object()));

    assertTrue(dag.containsAll(nodeSet));
    assertFalse(dag.containsAll(ImmutableSet.of(a, new Object())));

    assertEquals(dag.nodeSet(), ImmutableSet.copyOf(dag.iterator()));
    assertEquals(dag.nodeSet(), ImmutableSet.copyOf(dag.toArray()));
    assertEquals(dag.nodeSet(), ImmutableSet.copyOf(dag));
  }

}
