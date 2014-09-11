package com.stevewedig.blog.digraph;

import static com.stevewedig.blog.translate.FormatLib.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.node.*;
import com.stevewedig.blog.digraph.node_graph.*;
import com.stevewedig.blog.errors.*;
import com.stevewedig.blog.util.LambdaLib.Fn1;

public class TestDetailsGraphPartial {

  @Test
  public void testCornerCase__partial() {

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

    assertEquals(ImmutableSet.of(), tree.parentNodeSet("a"));

    // skipMissingNode = false
    try {
      tree.parentNodeSet("b");
      throw new NotThrown(NotContained.class);
    } catch (NotContained e) {
    }

    // =================================
    // children
    // =================================

    assertEquals(parseSet("b"), tree.childIdSet("a"));
    assertEquals(parseSet(""), tree.childIdSet("b"));

    // not currently verifying that skipMissingNode = false
    assertEquals(ImmutableSet.of(b), tree.childNodeSet("a"));
    assertEquals(ImmutableSet.of(), tree.childNodeSet("b"));

    // =================================
    // ancestors
    // =================================

    assertEquals(parseSet(""), tree.ancestorIdSet("a"));
    assertEquals(parseSet("a"), tree.ancestorIdSet("b"));

    assertEquals(ImmutableSet.of(), tree.ancestorIdSet("a"));

    // skipMissingNode = false
    try {
      tree.ancestorNodeSet("b");
      throw new NotThrown(NotContained.class);
    } catch (NotContained e) {
    }

    // =================================
    // descendants
    // =================================

    assertEquals(parseSet("b"), tree.descendantIdSet("a"));
    assertEquals(parseSet(""), tree.descendantIdSet("b"));

    // not currently verifying that skipMissingNode = false
    assertEquals(ImmutableSet.of(b), tree.descendantNodeSet("a"));
    assertEquals(ImmutableSet.of(), tree.descendantNodeSet("b"));

    // =================================
    // roots
    // =================================

    assertEquals(parseSet("a"), tree.rootIdSet());

    // skipMissingNode = false
    try {
      tree.rootNodeSet();
      throw new NotThrown(NotContained.class);
    } catch (NotContained e) {
    }

    // =================================
    // leaves
    // =================================

    assertEquals(parseSet("b"), tree.leafIdSet());

    // not currently verifying that skipMissingNode = false
    assertEquals(ImmutableSet.of(b), tree.leafNodeSet());

    // =================================
    // optional topsort
    // =================================

    assertEquals(parseList("a, b"), tree.optionalTopsortIdList().get());

    // skipMissingNode = true
    assertEquals(ImmutableList.of(b), tree.optionalTopsortNodeList().get());

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

    assertEquals(ImmutableList.of(b), tree.nodeList(true, true, ImmutableList.of("b"), expandNode));

    // =========================================================================
    // dag attributes
    // =========================================================================

    // =================================
    // topsort
    // =================================

    assertEquals(parseList("a, b"), tree.topsortIdList());

    // skipMissingNode = true
    assertEquals(ImmutableList.of(b), tree.topsortNodeList());

    // =================================
    // depth first
    // =================================

    assertEquals(parseList("a, b"), tree.depthIdList());

    // skipMissingNode = true
    assertEquals(ImmutableList.of(b), tree.depthNodeList());

    // =================================
    // breadth first
    // =================================

    assertEquals(parseList("a, b"), tree.breadthIdList());

    // skipMissingNode = true
    assertEquals(ImmutableList.of(b), tree.breadthNodeList());

    // =========================================================================
    // tree attributes
    // =========================================================================

    // =================================
    // root
    // =================================

    assertEquals("a", tree.rootId());

    try {
      tree.rootNode();
      throw new NotThrown(NotContained.class);
    } catch (NotContained e) {
    }

    // =================================
    // parent
    // =================================

    assertEquals(Optional.absent(), tree.parentId("a"));
    assertEquals(Optional.absent(), tree.parentNode("a"));
    assertEquals(Optional.of("a"), tree.parentId("b"));

    // this is optional for the root
    // for child nodes, this should throw not contained
    try {
      tree.parentNode("b");
      throw new NotThrown(NotContained.class);
    } catch (NotContained e) {
    }

    // =================================
    // ancestor list
    // =================================

    assertEquals(parseList(""), tree.ancestorIdList("a"));
    assertEquals(parseList("a"), tree.ancestorIdList("b"));

    assertEquals(parseList(""), tree.ancestorNodeList("a"));

    // skipMissingNode = false
    try {
      tree.ancestorNodeList("b");
      throw new NotThrown(NotContained.class);
    } catch (NotContained e) {
    }

    // =================================
    // depth
    // =================================

    assertEquals(0, tree.depth("a"));
    assertEquals(1, tree.depth("b"));

    assertEquals(1, tree.maxDepth());

  }
}
