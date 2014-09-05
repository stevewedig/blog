package com.stevewedig.blog.digraph;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.digraph.node.DownNodeLib;
import com.stevewedig.blog.digraph.node.UpNodeLib;

public class TestDetailsNodeCreate {

  @Test
  public void testNodeCreation() {

    assertEquals(UpNodeLib.upNode("a", "b"), UpNodeLib.upNode("a", ImmutableSet.of("b")));

    assertEquals(DownNodeLib.downNode("a", "b"), DownNodeLib.downNode("a", ImmutableSet.of("b")));
  }


}
