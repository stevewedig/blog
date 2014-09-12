package com.stevewedig.blog.util;

import static com.stevewedig.blog.translate.FormatLib.parseSet;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class TestSetLib {

  @Test
  public void testUnionFull() {

    ImmutableSet<String> a = parseSet("1, 2");

    ImmutableSet<String> b = parseSet("2, 3");

    ImmutableSet<String> c = parseSet("4");

    ImmutableSet<String> union = parseSet("1, 2, 3, 4");
    
    assertEquals(union, SetLib.union(a, b, c));
  }
  
  @Test
  public void testUnionEmpty() {
    
    assertEquals(parseSet(""), SetLib.union(parseSet(""), parseSet("")));
    
  }

}
