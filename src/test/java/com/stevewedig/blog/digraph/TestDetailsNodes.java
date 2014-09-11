package com.stevewedig.blog.digraph;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.digraph.node.*;
import com.stevewedig.blog.errors.NotThrown;

public class TestDetailsNodes {

  @Test
  public void testNodeCreation() {

    assertEquals(UpNodeLib.upNode("a", "b"), UpNodeLib.upNode("a", ImmutableSet.of("b")));

    assertEquals(DownNodeLib.downNode("a", "b"), DownNodeLib.downNode("a", ImmutableSet.of("b")));
  }

  @Test
  public void testNodeConflict() {

    try {
      UpNodeLib.nodes__nodeMap(ImmutableSet.of(UpNodeLib.upNode("a", "b"),
          UpNodeLib.upNode("a", "c")));

      throw new NotThrown(NodeIdConflict.class);
    } catch (NodeIdConflict e) {
    }

    
    try {
      DownNodeLib.nodes__nodeMap(ImmutableSet.of(DownNodeLib.downNode("a", "b"),
          DownNodeLib.downNode("a", "c")));
      
      throw new NotThrown(NodeIdConflict.class);
    } catch (NodeIdConflict e) {
    }
    

  }


}
