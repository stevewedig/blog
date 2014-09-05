package com.stevewedig.blog.digraph;

import static com.stevewedig.blog.digraph.node.UpNodeLib.upNode;
import static com.stevewedig.blog.digraph.node.DownNodeLib.downNode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.stevewedig.blog.digraph.id_graph.GraphValidationErrors.DagCannotHaveCycle;
import com.stevewedig.blog.digraph.id_graph.GraphValidationErrors.GraphContainedUnexpectedIds;
import com.stevewedig.blog.digraph.id_graph.GraphValidationErrors.TreeCannotBeEmpty;
import com.stevewedig.blog.digraph.id_graph.GraphValidationErrors.TreeCannotHaveMultipleRoots;
import com.stevewedig.blog.digraph.id_graph.GraphValidationErrors.TreeNodesCannotHaveMultipleParents;
import com.stevewedig.blog.digraph.id_graph.IdDag;
import com.stevewedig.blog.digraph.id_graph.IdDagLib;
import com.stevewedig.blog.digraph.id_graph.IdGraph;
import com.stevewedig.blog.digraph.id_graph.IdGraphLib;
import com.stevewedig.blog.digraph.id_graph.IdTree;
import com.stevewedig.blog.digraph.id_graph.IdTreeLib;
import com.stevewedig.blog.digraph.node.UpNode;
import com.stevewedig.blog.digraph.node_graph.Dag;
import com.stevewedig.blog.digraph.node_graph.DagLib;
import com.stevewedig.blog.digraph.node_graph.Graph;
import com.stevewedig.blog.digraph.node_graph.GraphLib;
import com.stevewedig.blog.digraph.node_graph.Tree;
import com.stevewedig.blog.digraph.node_graph.TreeLib;
import com.stevewedig.blog.errors.NotThrown;

public class TestDetailsGraphCreate {

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
    assertEquals(idGraph,
        GraphLib.up(GraphLib.up(upNode("a"), upNode("b", "a")), GraphLib.up(upNode("c", "a")))
            .idGraph());

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
        GraphLib.down(GraphLib.down(downNode("a", "b", "c"), downNode("b")),
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
        DagLib.up(DagLib.up(upNode("a"), upNode("b", "a")), DagLib.up(upNode("c", "a"))).idGraph());

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
    assertEquals(idDag,
        DagLib
            .down(DagLib.down(downNode("a", "b", "c"), downNode("b")), DagLib.down(downNode("c")))
            .idGraph());

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
        TreeLib.up(TreeLib.up(upNode("a"), upNode("b", "a")), TreeLib.up(upNode("c", "a")))
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
        TreeLib.down(TreeLib.down(downNode("a", "b", "c"), downNode("b")),
            TreeLib.down(downNode("c"))).idGraph());

  }

  // ===========================================================================
  // test various validation errors
  // ===========================================================================

  @Test
  public void testValidation__GraphContainedUnexpectedIds__unexpectedInArcMap() {

    Set<String> ids = Sets.newHashSet("a");

    Multimap<String, String> childId__parentId = ImmutableSetMultimap.of("a", "missing");

    try {
      IdGraphLib.fromParentMap(ids, childId__parentId);

      throw new NotThrown(GraphContainedUnexpectedIds.class);
    } catch (GraphContainedUnexpectedIds e) {
    }
  }


  @Test
  public void testValidation__GraphContainedUnexpectedIds__unexpectedInNodeMap() {

    IdGraph<String> idGraph = IdGraphLib.fromParentMap(ImmutableSetMultimap.of("b", "a"));

    ImmutableBiMap<String, Object> id__node =
        ImmutableBiMap.of("a", new Object(), "b", new Object(), "missing", new Object());

    try {
      GraphLib.graph(idGraph, id__node);

      throw new NotThrown(GraphContainedUnexpectedIds.class);
    } catch (GraphContainedUnexpectedIds e) {
    }
  }

  @Test
  public void testValidation__DagCannotHaveCycle() {

    try {
      IdDagLib.fromParentMap(ImmutableSetMultimap.of("a", "a"));

      throw new NotThrown(DagCannotHaveCycle.class);
    } catch (DagCannotHaveCycle e) {
    }
  }


  @Test
  public void testValidation__TreeCannotHaveMultipleRoots() {

    try {
      IdTreeLib.fromParentMap(ImmutableSetMultimap.of("child1", "root1", "child2", "root2"));

      throw new NotThrown(TreeCannotHaveMultipleRoots.class);
    } catch (TreeCannotHaveMultipleRoots e) {
    }
  }


  @Test
  public void testValidation__TreeCannotBeEmpty() {

    try {
      IdTreeLib.fromParentMap(ImmutableSetMultimap.of());

      throw new NotThrown(TreeCannotBeEmpty.class);
    } catch (TreeCannotBeEmpty e) {
    }
  }


  @Test
  public void testValidation__TreeNodesCannotHaveMultipleParents() {

    try {
      IdTreeLib.fromChildMap(ImmutableSetMultimap.of("parent1", "child", "parent2", "child",
          "root", "parent1", "root", "parent2"));

      throw new NotThrown(TreeNodesCannotHaveMultipleParents.class);
    } catch (TreeNodesCannotHaveMultipleParents e) {
    }
  }

  // ===========================================================================
  // test various corner cases
  // http://en.wikipedia.org/wiki/Corner_case
  // ===========================================================================

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

  @Test
  public void testCornerCase__partial() {

    Graph<String, UpNode<String>> graph = GraphLib.up(upNode("b", "missing"));
    assertEquals(ImmutableSet.of("missing"), graph.unboundIdSet());
    assertTrue(graph.isPartial());
    assertFalse(graph.isComplete());

    Dag<String, UpNode<String>> dag = DagLib.up(upNode("b", "missing"));
    assertEquals(ImmutableSet.of("missing"), dag.unboundIdSet());
    assertTrue(dag.isPartial());
    assertFalse(dag.isComplete());

    Tree<String, UpNode<String>> tree = TreeLib.up(upNode("b", "missing"));
    assertEquals(ImmutableSet.of("missing"), tree.unboundIdSet());
    assertTrue(tree.isPartial());
    assertFalse(tree.isComplete());

  }

}
