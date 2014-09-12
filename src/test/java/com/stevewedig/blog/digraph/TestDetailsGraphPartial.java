package com.stevewedig.blog.digraph;

import static com.stevewedig.blog.translate.FormatLib.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.errors.NotAllowedForPartialGraphs;
import com.stevewedig.blog.digraph.node.*;
import com.stevewedig.blog.digraph.node_graph.*;
import com.stevewedig.blog.errors.*;
import com.stevewedig.blog.util.LambdaLib.Fn1;

public class TestDetailsGraphPartial {

  @Test
  public void testPartialGraph() {

    UpNode<String> b = UpNodeLib.upNode("b", "a");

    final Tree<String, UpNode<String>> tree = TreeLib.up(b);

    // =========================================================================
    // graph attributes
    // =========================================================================

    // =================================
    // unbound ids
    // =================================

    assertEquals(parseSet("a"), tree.unboundIdSet());

    assertTrue(tree.isPartial());
    assertFalse(tree.isComplete());

    // =================================
    // ids
    // =================================

    assertEquals(parseSet("a, b"), tree.idSet());

    assertEquals(2, tree.idSize());

    // =================================
    // nodes
    // =================================

    assertEquals(ImmutableSet.of(b), tree.nodeSet());

    assertEquals(1, tree.nodeSize());

    // =================================
    // node lookup
    // =================================

    try {
      tree.node("a");
      throw new NotThrown(NotContained.class);
    } catch (NotContained e) {
    }

    assertEquals(b, tree.node("b"));

    // =================================
    // parents
    // =================================

    assertEquals(parseSet(""), tree.parentIdSet("a"));
    assertEquals(parseSet("a"), tree.parentIdSet("b"));

    try {
      tree.parentNodeSet("a");
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    try {
      tree.parentNodeSet("b");
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    // =================================
    // children
    // =================================

    assertEquals(parseSet("b"), tree.childIdSet("a"));
    assertEquals(parseSet(""), tree.childIdSet("b"));

    try {
      tree.childNodeSet("a");
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    try {
      tree.childNodeSet("b");
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    // =================================
    // ancestors
    // =================================

    assertEquals(parseSet(""), tree.ancestorIdSet("a"));
    assertEquals(parseSet("a"), tree.ancestorIdSet("b"));

    try {
      tree.ancestorNodeSet("a");
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    try {
      tree.ancestorNodeSet("b");
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    // =================================
    // descendants
    // =================================

    assertEquals(parseSet("b"), tree.descendantIdSet("a"));
    assertEquals(parseSet(""), tree.descendantIdSet("b"));

    try {
      tree.descendantNodeSet("a");
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    try {
      tree.descendantNodeSet("b");
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    // =================================
    // roots
    // =================================

    assertEquals(parseSet("a"), tree.rootIdSet());

    try {
      tree.rootNodeSet();
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    // =================================
    // leaves
    // =================================

    assertEquals(parseSet("b"), tree.leafIdSet());

    try {
      tree.leafNodeSet();
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    // =================================
    // optional topsort
    // =================================

    assertEquals(parseList("a, b"), tree.optionalTopsortIdList().get());

    try {
      tree.optionalTopsortNodeList();
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    // =================================
    // id traversal
    // =================================

    Fn1<String, List<String>> expandId = new Fn1<String, List<String>>() {
      @Override
      public List<String> apply(String id) {
        return ImmutableList.copyOf(tree.parentIdSet(id));
      }
    };

    assertEquals(parseList("b, a"), tree.idList(true, true, ImmutableList.of("b"), expandId));

    // =================================
    // node traversal (even though we don't have all the nodes
    // =================================

    Fn1<UpNode<String>, List<String>> expandNode = new Fn1<UpNode<String>, List<String>>() {
      @Override
      public List<String> apply(UpNode<String> node) {
        return ImmutableList.copyOf(node.parentIds());
      }
    };

    try {
      tree.nodeList(true, true, ImmutableList.of("b"), expandNode);
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    // =========================================================================
    // dag attributes
    // =========================================================================

    // =================================
    // topsort
    // =================================

    assertEquals(parseList("a, b"), tree.topsortIdList());

    try {
      tree.topsortNodeList();
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    // =================================
    // depth first
    // =================================

    assertEquals(parseList("a, b"), tree.depthIdList());

    try {
      tree.depthNodeList();
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    // =================================
    // breadth first
    // =================================

    assertEquals(parseList("a, b"), tree.breadthIdList());

    try {
      tree.breadthNodeList();
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    // =========================================================================
    // tree attributes
    // =========================================================================

    // =================================
    // root
    // =================================

    assertEquals("a", tree.rootId());

    try {
      tree.rootNode();
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    // =================================
    // parent
    // =================================

    assertEquals(Optional.absent(), tree.parentId("a"));
    assertEquals(Optional.of("a"), tree.parentId("b"));

    try {
      tree.parentNode("a");
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    try {
      tree.parentNode("b");
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    // =================================
    // ancestor list
    // =================================

    assertEquals(parseList(""), tree.ancestorIdList("a"));
    assertEquals(parseList("a"), tree.ancestorIdList("b"));

    try {
      tree.ancestorNodeList("a");
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }
    
    try {
      tree.ancestorNodeList("b");
      throw new NotThrown(NotAllowedForPartialGraphs.class);
    } catch (NotAllowedForPartialGraphs e) {
    }

    // =================================
    // depth
    // =================================

    assertEquals(0, tree.depth("a"));
    assertEquals(1, tree.depth("b"));

    assertEquals(1, tree.maxDepth());

  }

}
