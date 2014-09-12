package com.stevewedig.blog.digraph;

import static com.stevewedig.blog.digraph.node.DownNodeLib.downNode;
import static com.stevewedig.blog.digraph.node.UpNodeLib.upNode;
import static org.junit.Assert.*;

import org.junit.Test;

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
    // id -> nodes
    // =================================

    Node a = dag.node("a");
    Node b = dag.node("b");
    Node c = dag.node("c");
    Node d = dag.node("d");
    Node e = dag.node("e");

    ImmutableBiMap<String, Node> id__node =
        ImmutableBiMap.<String, Node>of("a", a, "b", b, "c", c, "d", d, "e", e);

    assertEquals(id__node, dag.id__node());

    // =================================
    // nodeSet
    // =================================

    ImmutableSet<Node> nodeSet = ImmutableSet.of(a, b, c, d, e);

    assertEquals(nodeSet, dag.nodeSet());

    assertEquals(nodeSet.size(), dag.nodeSize());

    // =================================
    // unboundIds (ids without nodes)
    // =================================

    assertEquals(ImmutableSet.of(), dag.unboundIdSet());

    // =================================
    // parents
    // =================================

    assertEquals(ImmutableSet.of(), dag.parentNodeSet("a"));
    assertEquals(ImmutableSet.of(a), dag.parentNodeSet("b"));
    assertEquals(ImmutableSet.of(a), dag.parentNodeSet("c"));
    assertEquals(ImmutableSet.of(b, c), dag.parentNodeSet("d"));
    assertEquals(ImmutableSet.of(d), dag.parentNodeSet("e"));

    // =================================
    // children
    // =================================

    assertEquals(ImmutableSet.of(b, c), dag.childNodeSet("a"));
    assertEquals(ImmutableSet.of(d), dag.childNodeSet("b"));
    assertEquals(ImmutableSet.of(d), dag.childNodeSet("c"));
    assertEquals(ImmutableSet.of(e), dag.childNodeSet("d"));
    assertEquals(ImmutableSet.of(), dag.childNodeSet("e"));

    // =================================
    // ancestors
    // =================================

    assertEquals(ImmutableSet.of(), dag.ancestorNodeSet("a"));
    assertEquals(ImmutableSet.of(a), dag.ancestorNodeSet("b"));
    assertEquals(ImmutableSet.of(a), dag.ancestorNodeSet("c"));
    assertEquals(ImmutableSet.of(a, b, c), dag.ancestorNodeSet("d"));
    assertEquals(ImmutableSet.of(a, b, c, d), dag.ancestorNodeSet("e"));

    // =================================
    // descendants
    // =================================

    assertEquals(ImmutableSet.of(b, c, d, e), dag.descendantNodeSet("a"));
    assertEquals(ImmutableSet.of(d, e), dag.descendantNodeSet("b"));
    assertEquals(ImmutableSet.of(d, e), dag.descendantNodeSet("c"));
    assertEquals(ImmutableSet.of(e), dag.descendantNodeSet("d"));
    assertEquals(ImmutableSet.of(), dag.descendantNodeSet("e"));

    // =================================
    // roots (sources)
    // =================================

    assertEquals(ImmutableSet.of(a), dag.rootNodeSet());

    // =================================
    // leaves (sinks)
    // =================================

    assertEquals(ImmutableSet.of(e), dag.leafNodeSet());

    // =================================
    // nodeList (topological sort)
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
