package com.stevewedig.blog.digraph;

import static com.stevewedig.blog.digraph.node.DownNodeLib.downNode;
import static com.stevewedig.blog.digraph.node.UpNodeLib.upNode;
import static com.stevewedig.blog.translate.FormatLib.parseSet;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.stevewedig.blog.digraph.id_graph.IdDag;
import com.stevewedig.blog.digraph.id_graph.IdDagLib;
import com.stevewedig.blog.digraph.id_graph.IdGraph;
import com.stevewedig.blog.digraph.id_graph.IdGraphLib;
import com.stevewedig.blog.digraph.id_graph.IdTree;
import com.stevewedig.blog.digraph.id_graph.IdTreeLib;
import com.stevewedig.blog.digraph.node_graph.DagLib;
import com.stevewedig.blog.digraph.node_graph.GraphLib;
import com.stevewedig.blog.digraph.node_graph.TreeLib;
import com.stevewedig.blog.digraph.node_graph_partial.PartialDagLib;
import com.stevewedig.blog.digraph.node_graph_partial.PartialGraphLib;
import com.stevewedig.blog.digraph.node_graph_partial.PartialTreeLib;

public class TestDetailsCreation {

  // this tests all the ways to create graphs, which unfortunately
  // involves a combinatorial explosion of:
  // A. graph vs. dag vs. tree
  // B. up node vs. down node
  // C. complete graph vs. partial graph
  @Test
  public void testGraphCreation() {

    Set<String> ids = Sets.newHashSet("a", "b", "c");

    // b -> a
    // c -> a
    Multimap<String, String> childId__parentId = HashMultimap.create();
    childId__parentId.put("b", "a");
    childId__parentId.put("c", "a");

    // b <- a
    // c <- a
    Multimap<String, String> parentId__childId = HashMultimap.create();
    parentId__childId.put("a", "b");
    parentId__childId.put("a", "c");

    // =================================
    // id graph
    // =================================

    IdGraph<String> idGraph = IdGraphLib.fromParentMap(ids, childId__parentId);
    assertEquals(idGraph, IdGraphLib.fromParentMap(childId__parentId));
    assertEquals(idGraph, IdGraphLib.fromParentMap("b", "a", "c", "a"));
    assertEquals(idGraph, IdGraphLib.fromParentMap(parseSet("a, b, c"), "b", "a", "c", "a"));
    assertEquals(idGraph, IdGraphLib.fromChildMap(ids, parentId__childId));
    assertEquals(idGraph, IdGraphLib.fromChildMap(parentId__childId));
    assertEquals(idGraph, IdGraphLib.fromChildMap("a", "b", "a", "c"));
    assertEquals(idGraph, IdGraphLib.fromChildMap(parseSet("a, b, c"), "a", "b", "a", "c"));

    // =================================
    // id dag
    // =================================

    IdDag<String> idDag = IdDagLib.fromParentMap(ids, childId__parentId);
    assertEquals(idDag, IdDagLib.fromParentMap(childId__parentId));
    assertEquals(idDag, IdDagLib.fromParentMap("b", "a", "c", "a"));
    assertEquals(idDag, IdDagLib.fromParentMap(parseSet("a, b, c"), "b", "a", "c", "a"));
    assertEquals(idDag, IdDagLib.fromChildMap(ids, parentId__childId));
    assertEquals(idDag, IdDagLib.fromChildMap(parentId__childId));
    assertEquals(idDag, IdDagLib.fromChildMap("a", "b", "a", "c"));
    assertEquals(idDag, IdDagLib.fromChildMap(parseSet("a, b, c"), "a", "b", "a", "c"));

    // =================================
    // id tree
    // =================================

    IdTree<String> idTree = IdTreeLib.fromParentMap(ids, childId__parentId);
    assertEquals(idTree, IdTreeLib.fromParentMap(childId__parentId));
    assertEquals(idTree, IdTreeLib.fromParentMap("b", "a", "c", "a"));
    assertEquals(idTree, IdTreeLib.fromParentMap(parseSet("a, b, c"), "b", "a", "c", "a"));
    assertEquals(idTree, IdTreeLib.fromChildMap(ids, parentId__childId));
    assertEquals(idTree, IdTreeLib.fromChildMap(parentId__childId));
    assertEquals(idTree, IdTreeLib.fromChildMap("a", "b", "a", "c"));
    assertEquals(idTree, IdTreeLib.fromChildMap(parseSet("a, b, c"), "a", "b", "a", "c"));

    // =================================
    // node graph (up)
    // =================================

    // node var args
    assertEquals(idGraph, GraphLib.up(upNode("a"), upNode("b", "a"), upNode("c", "a")).idGraph());

    // node set
    assertEquals(idGraph,
        GraphLib.up(ImmutableSet.of(upNode("a"), upNode("b", "a"), upNode("c", "a"))).idGraph());

    // node iterable
    assertEquals(idGraph,
        GraphLib.up(ImmutableList.of(upNode("a"), upNode("b", "a"), upNode("c", "a"))).idGraph());

    // union of node sets
    assertEquals(
        idGraph,
        GraphLib.up(ImmutableSet.of(upNode("a"), upNode("b", "a")),
            ImmutableSet.of(upNode("c", "a"))).idGraph());

    // union of graphs
    assertEquals(
        idGraph,
        GraphLib.up(GraphLib.up(upNode("a"), upNode("b", "a")),
            PartialGraphLib.up(upNode("c", "a"))).idGraph());

    // =================================
    // node graph (down)
    // =================================

    // node var args
    assertEquals(idGraph, GraphLib.down(downNode("a", "b", "c"), downNode("b"), downNode("c"))
        .idGraph());

    // node set
    assertEquals(idGraph,
        GraphLib.down(ImmutableSet.of(downNode("a", "b", "c"), downNode("b"), downNode("c")))
            .idGraph());

    // node iterable
    assertEquals(idGraph,
        GraphLib.down(ImmutableList.of(downNode("a", "b", "c"), downNode("b"), downNode("c")))
            .idGraph());

    // union of node sets
    assertEquals(
        idGraph,
        GraphLib.down(ImmutableSet.of(downNode("a", "b", "c"), downNode("b")),
            ImmutableSet.of(downNode("c"))).idGraph());

    // union of graphs
    assertEquals(
        idGraph,
        GraphLib.down(PartialGraphLib.down(downNode("a", "b", "c"), downNode("b")),
            PartialGraphLib.down(downNode("c"))).idGraph());

    // =================================
    // node dag (up)
    // =================================

    // node var args
    assertEquals(idDag, DagLib.up(upNode("a"), upNode("b", "a"), upNode("c", "a")).idGraph());

    // node set
    assertEquals(idDag, DagLib.up(ImmutableSet.of(upNode("a"), upNode("b", "a"), upNode("c", "a")))
        .idGraph());

    // node iterable
    assertEquals(idDag, DagLib
        .up(ImmutableList.of(upNode("a"), upNode("b", "a"), upNode("c", "a"))).idGraph());

    // union of node sets
    assertEquals(idDag,
        DagLib
            .up(ImmutableSet.of(upNode("a"), upNode("b", "a")), ImmutableSet.of(upNode("c", "a")))
            .idGraph());

    // union of graphs
    assertEquals(
        idDag,
        DagLib.up(PartialDagLib.up(upNode("a"), upNode("b", "a")),
            PartialDagLib.up(upNode("c", "a"))).idGraph());

    // =================================
    // node dag (down)
    // =================================

    // node var args
    assertEquals(idDag, DagLib.down(downNode("a", "b", "c"), downNode("b"), downNode("c"))
        .idGraph());

    // node set
    assertEquals(idDag,
        DagLib.down(ImmutableSet.of(downNode("a", "b", "c"), downNode("b"), downNode("c")))
            .idGraph());

    // node iterable
    assertEquals(idDag,
        DagLib.down(ImmutableList.of(downNode("a", "b", "c"), downNode("b"), downNode("c")))
            .idGraph());

    // union of node sets
    assertEquals(
        idDag,
        DagLib.down(ImmutableSet.of(downNode("a", "b", "c"), downNode("b")),
            ImmutableSet.of(downNode("c"))).idGraph());

    // union of graphs
    assertEquals(
        idDag,
        DagLib.down(PartialDagLib.down(downNode("a", "b", "c"), downNode("b")),
            PartialDagLib.down(downNode("c"))).idGraph());

    // =================================
    // node tree (up)
    // =================================

    // node var args
    assertEquals(idTree, TreeLib.up(upNode("a"), upNode("b", "a"), upNode("c", "a")).idGraph());

    // node set
    assertEquals(idTree,
        TreeLib.up(ImmutableSet.of(upNode("a"), upNode("b", "a"), upNode("c", "a"))).idGraph());

    // node iterable
    assertEquals(idTree,
        TreeLib.up(ImmutableList.of(upNode("a"), upNode("b", "a"), upNode("c", "a"))).idGraph());

    // union of node sets
    assertEquals(
        idTree,
        TreeLib.up(ImmutableSet.of(upNode("a"), upNode("b", "a")),
            ImmutableSet.of(upNode("c", "a"))).idGraph());

    // union of graphs
    assertEquals(
        idTree,
        TreeLib.up(PartialTreeLib.up(upNode("a"), upNode("b", "a")),
            PartialTreeLib.up(upNode("c", "a"))).idGraph());

    // =================================
    // node tree (down)
    // =================================

    // node var args
    assertEquals(idTree, TreeLib.down(downNode("a", "b", "c"), downNode("b"), downNode("c"))
        .idGraph());

    // node set
    assertEquals(idTree,
        TreeLib.down(ImmutableSet.of(downNode("a", "b", "c"), downNode("b"), downNode("c")))
            .idGraph());

    // node iterable
    assertEquals(idTree,
        TreeLib.down(ImmutableList.of(downNode("a", "b", "c"), downNode("b"), downNode("c")))
            .idGraph());

    // union of node sets
    assertEquals(
        idTree,
        TreeLib.down(ImmutableSet.of(downNode("a", "b", "c"), downNode("b")),
            ImmutableSet.of(downNode("c"))).idGraph());

    // union of graphs
    assertEquals(
        idTree,
        TreeLib.down(PartialTreeLib.down(downNode("a", "b", "c"), downNode("b")),
            PartialTreeLib.down(downNode("c"))).idGraph());

    // =================================
    // partial node graph (up)
    // =================================

    // node var args
    assertEquals(idGraph, PartialGraphLib.up(upNode("a"), upNode("b", "a"), upNode("c", "a"))
        .idGraph());

    // node set
    assertEquals(idGraph,
        PartialGraphLib.up(ImmutableSet.of(upNode("a"), upNode("b", "a"), upNode("c", "a")))
            .idGraph());

    // node iterable
    assertEquals(idGraph,
        PartialGraphLib.up(ImmutableList.of(upNode("a"), upNode("b", "a"), upNode("c", "a")))
            .idGraph());

    // union of node sets
    assertEquals(
        idGraph,
        PartialGraphLib.up(ImmutableSet.of(upNode("a"), upNode("b", "a")),
            ImmutableSet.of(upNode("c", "a"))).idGraph());

    // union of graphs
    assertEquals(
        idGraph,
        PartialGraphLib.up(PartialGraphLib.up(upNode("a"), upNode("b", "a")),
            PartialGraphLib.up(upNode("c", "a"))).idGraph());

    // =================================
    // partial node graph (down)
    // =================================

    // node var args
    assertEquals(idGraph,
        PartialGraphLib.down(downNode("a", "b", "c"), downNode("b"), downNode("c")).idGraph());

    // node set
    assertEquals(idGraph,
        PartialGraphLib
            .down(ImmutableSet.of(downNode("a", "b", "c"), downNode("b"), downNode("c"))).idGraph());

    // node iterable
    assertEquals(
        idGraph,
        PartialGraphLib.down(
            ImmutableList.of(downNode("a", "b", "c"), downNode("b"), downNode("c"))).idGraph());

    // union of node sets
    assertEquals(
        idGraph,
        PartialGraphLib.down(ImmutableSet.of(downNode("a", "b", "c"), downNode("b")),
            ImmutableSet.of(downNode("c"))).idGraph());

    // union of graphs
    assertEquals(
        idGraph,
        PartialGraphLib.down(PartialGraphLib.down(downNode("a", "b", "c"), downNode("b")),
            PartialGraphLib.down(downNode("c"))).idGraph());

    // =================================
    // partial node dag (up)
    // =================================

    // node var args
    assertEquals(idDag, PartialDagLib.up(upNode("a"), upNode("b", "a"), upNode("c", "a")).idGraph());

    // node set
    assertEquals(idDag,
        PartialDagLib.up(ImmutableSet.of(upNode("a"), upNode("b", "a"), upNode("c", "a")))
            .idGraph());

    // node iterable
    assertEquals(idDag,
        PartialDagLib.up(ImmutableList.of(upNode("a"), upNode("b", "a"), upNode("c", "a")))
            .idGraph());

    // union of node sets
    assertEquals(
        idDag,
        PartialDagLib.up(ImmutableSet.of(upNode("a"), upNode("b", "a")),
            ImmutableSet.of(upNode("c", "a"))).idGraph());

    // union of graphs
    assertEquals(
        idDag,
        PartialDagLib.up(PartialDagLib.up(upNode("a"), upNode("b", "a")),
            PartialDagLib.up(upNode("c", "a"))).idGraph());

    // =================================
    // partial node dag (down)
    // =================================

    // node var args
    assertEquals(idDag, PartialDagLib.down(downNode("a", "b", "c"), downNode("b"), downNode("c"))
        .idGraph());

    // node set
    assertEquals(idDag,
        PartialDagLib.down(ImmutableSet.of(downNode("a", "b", "c"), downNode("b"), downNode("c")))
            .idGraph());

    // node iterable
    assertEquals(idDag,
        PartialDagLib.down(ImmutableList.of(downNode("a", "b", "c"), downNode("b"), downNode("c")))
            .idGraph());

    // union of node sets
    assertEquals(
        idDag,
        PartialDagLib.down(ImmutableSet.of(downNode("a", "b", "c"), downNode("b")),
            ImmutableSet.of(downNode("c"))).idGraph());

    // union of graphs
    assertEquals(
        idDag,
        PartialDagLib.down(PartialDagLib.down(downNode("a", "b", "c"), downNode("b")),
            PartialDagLib.down(downNode("c"))).idGraph());

    // =================================
    // partial node tree (up)
    // =================================

    // node var args
    assertEquals(idTree, PartialTreeLib.up(upNode("a"), upNode("b", "a"), upNode("c", "a"))
        .idGraph());

    // node set
    assertEquals(idTree,
        PartialTreeLib.up(ImmutableSet.of(upNode("a"), upNode("b", "a"), upNode("c", "a")))
            .idGraph());

    // node iterable
    assertEquals(idTree,
        PartialTreeLib.up(ImmutableList.of(upNode("a"), upNode("b", "a"), upNode("c", "a")))
            .idGraph());

    // union of node sets
    assertEquals(
        idTree,
        PartialTreeLib.up(ImmutableSet.of(upNode("a"), upNode("b", "a")),
            ImmutableSet.of(upNode("c", "a"))).idGraph());

    // union of graphs
    assertEquals(
        idTree,
        PartialTreeLib.up(PartialTreeLib.up(upNode("a"), upNode("b", "a")),
            PartialTreeLib.up(upNode("c", "a"))).idGraph());

    // =================================
    // partial node tree (down)
    // =================================

    // node var args
    assertEquals(idTree, PartialTreeLib.down(downNode("a", "b", "c"), downNode("b"), downNode("c"))
        .idGraph());

    // node set
    assertEquals(idTree,
        PartialTreeLib.down(ImmutableSet.of(downNode("a", "b", "c"), downNode("b"), downNode("c")))
            .idGraph());

    // node iterable
    assertEquals(idTree,
        PartialTreeLib
            .down(ImmutableList.of(downNode("a", "b", "c"), downNode("b"), downNode("c")))
            .idGraph());

    // union of node sets
    assertEquals(
        idTree,
        PartialTreeLib.down(ImmutableSet.of(downNode("a", "b", "c"), downNode("b")),
            ImmutableSet.of(downNode("c"))).idGraph());

    // union of graphs
    assertEquals(
        idTree,
        PartialTreeLib.down(PartialTreeLib.down(downNode("a", "b", "c"), downNode("b")),
            PartialTreeLib.down(downNode("c"))).idGraph());

  }

}
