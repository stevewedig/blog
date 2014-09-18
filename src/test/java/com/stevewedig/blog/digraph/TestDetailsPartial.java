package com.stevewedig.blog.digraph;

import static com.stevewedig.blog.translate.FormatLib.*;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.node.*;
import com.stevewedig.blog.digraph.node_graph_partial.*;
import com.stevewedig.blog.errors.*;
import com.stevewedig.blog.util.LambdaLib.Fn1;

public class TestDetailsPartial {

  @Test
  public void testPartialGraph() {

    UpNode<String> b = UpNodeLib.upNode("b", "a");

    final PartialTree<String, UpNode<String>> tree = PartialTreeLib.up(b);

    // =========================================================================
    // graph attributes
    // =========================================================================

    // =================================
    // unbound ids
    // =================================

    assertEquals(parseSet("a"), tree.unboundIdSet());

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
      tree.getNode("a");
      throw new NotThrown(NotContained.class);
    } catch (NotContained e) {
    }

    assertEquals(b, tree.getNode("b"));

    // =================================
    // parents
    // =================================

    assertEquals(parseSet(""), tree.parentIdSet("a"));
    assertEquals(parseSet("a"), tree.parentIdSet("b"));

    // =================================
    // children
    // =================================

    assertEquals(parseSet("b"), tree.childIdSet("a"));
    assertEquals(parseSet(""), tree.childIdSet("b"));

    // =================================
    // ancestors
    // =================================

    assertEquals(parseSet(""), tree.ancestorIdSet("a", false));
    assertEquals(parseSet("a"), tree.ancestorIdSet("b", false));

    // =================================
    // descendants
    // =================================

    assertEquals(parseSet("b"), tree.descendantIdSet("a", false));
    assertEquals(parseSet(""), tree.descendantIdSet("b", false));

    // =================================
    // roots
    // =================================

    assertEquals(parseSet("a"), tree.rootIdSet());

    // =================================
    // leaves
    // =================================

    assertEquals(parseSet("b"), tree.leafIdSet());

    // =================================
    // optional topsort
    // =================================

    assertEquals(parseList("a, b"), tree.optionalTopsortIdList().get());

    // =================================
    // id traversal
    // =================================

    Fn1<String, List<String>> expandId = new Fn1<String, List<String>>() {
      @Override
      public List<String> apply(String id) {
        return ImmutableList.copyOf(tree.parentIdSet(id));
      }
    };

    assertEquals(parseList("b, a"), tree.traverseIdList(true, true, ImmutableList.of("b"), expandId));

    // =========================================================================
    // dag attributes
    // =========================================================================

    // =================================
    // topsort
    // =================================

    assertEquals(parseList("a, b"), tree.topsortIdList());

    // =================================
    // depth first
    // =================================

    assertEquals(parseList("a, b"), tree.depthIdList());

    // =================================
    // breadth first
    // =================================

    assertEquals(parseList("a, b"), tree.breadthIdList());

    // =========================================================================
    // tree attributes
    // =========================================================================

    // =================================
    // root
    // =================================

    assertEquals("a", tree.rootId());

    // =================================
    // parent
    // =================================

    assertEquals(Optional.absent(), tree.parentId("a"));
    assertEquals(Optional.of("a"), tree.parentId("b"));

    // =================================
    // ancestor list
    // =================================

    assertEquals(parseList(""), tree.ancestorIdList("a", false));
    assertEquals(parseList("a"), tree.ancestorIdList("b", false));

    // =================================
    // depth
    // =================================

    assertEquals(0, tree.depth("a"));
    assertEquals(1, tree.depth("b"));

    assertEquals(1, tree.maxDepth());

  }

}
