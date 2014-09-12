package com.stevewedig.blog.digraph;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.*;
import com.stevewedig.blog.digraph.errors.*;
import com.stevewedig.blog.digraph.id_graph.*;
import com.stevewedig.blog.digraph.node.*;
import com.stevewedig.blog.digraph.node_graph.*;
import com.stevewedig.blog.errors.NotThrown;

public class TestDetailsValidation {

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
  public void testValidation__GraphIsMissingNodes() {

    UpNode<String> b = UpNodeLib.upNode("b", "a");

    try {
      GraphLib.up(b);
      throw new NotThrown(GraphIsMissingNodes.class);
    } catch (GraphIsMissingNodes e) {
    }

    try {
      DagLib.up(b);
      throw new NotThrown(GraphIsMissingNodes.class);
    } catch (GraphIsMissingNodes e) {
    }

    try {
      TreeLib.up(b);
      throw new NotThrown(GraphIsMissingNodes.class);
    } catch (GraphIsMissingNodes e) {
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

}
