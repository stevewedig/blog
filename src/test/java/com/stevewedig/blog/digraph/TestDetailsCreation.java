package com.stevewedig.blog.digraph;

import static com.stevewedig.blog.digraph.node.DownNodeLib.downNode;
import static com.stevewedig.blog.digraph.node.UpNodeLib.upNode;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.*;
import com.stevewedig.blog.digraph.id_graph.*;
import com.stevewedig.blog.digraph.node_graph.*;
import com.stevewedig.blog.digraph.node_graph_partial.*;

public class TestDetailsCreation {

  // ===========================================================================
  // test various graph creation methods
  // ===========================================================================

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
    assertEquals(idGraph, IdGraphLib.fromChildMap(ids, parentId__childId));
    assertEquals(idGraph, IdGraphLib.fromChildMap(parentId__childId));

    // =================================
    // id dag
    // =================================

    IdDag<String> idDag = IdDagLib.fromParentMap(ids, childId__parentId);
    assertEquals(idDag, IdDagLib.fromParentMap(childId__parentId));
    assertEquals(idDag, IdDagLib.fromChildMap(ids, parentId__childId));
    assertEquals(idDag, IdDagLib.fromChildMap(parentId__childId));

    // =================================
    // id tree
    // =================================

    IdTree<String> idTree = IdTreeLib.fromParentMap(ids, childId__parentId);
    assertEquals(idTree, IdTreeLib.fromParentMap(childId__parentId));
    assertEquals(idTree, IdTreeLib.fromChildMap(ids, parentId__childId));
    assertEquals(idTree, IdTreeLib.fromChildMap(parentId__childId));

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
            GraphLib.down(downNode("c"))).idGraph());

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
    assertEquals(idDag,
        DagLib.up(DagLib.up(upNode("a"), upNode("b", "a")), PartialDagLib.up(upNode("c", "a")))
            .idGraph());

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
            DagLib.down(downNode("c"))).idGraph());

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
    assertEquals(idTree,
        TreeLib.up(TreeLib.up(upNode("a"), upNode("b", "a")), PartialTreeLib.up(upNode("c", "a")))
            .idGraph());

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
            TreeLib.down(downNode("c"))).idGraph());

  }

}
