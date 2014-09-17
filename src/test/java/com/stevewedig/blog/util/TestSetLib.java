package com.stevewedig.blog.util;

import static com.stevewedig.blog.translate.FormatLib.parseSet;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.stevewedig.blog.errors.NotThrown;

public class TestSetLib {

  // ===========================================================================
  // union
  // ===========================================================================

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

  // ===========================================================================
  // assertEquals
  // ===========================================================================

  @Test
  public void testAssertEquals() {

    // two hash sets
    SetLib.assertEquals(Sets.newHashSet(), Sets.newHashSet());

    // hash set and immutable set
    SetLib.assertEquals(Sets.newHashSet(), ImmutableSet.of());

    // one item
    SetLib.assertEquals(Sets.newHashSet("a"), ImmutableSet.of("a"));

    // multiple items
    SetLib.assertEquals(Sets.newHashSet("a", "b"), ImmutableSet.of("a", "b"));

    // missing items
    try {
      SetLib.assertEquals(Sets.newHashSet("a", "b"), ImmutableSet.of("a"));
      throw new NotThrown(AssertionError.class);
    } catch (AssertionError e) {
    }

    // unexpected items
    try {
      SetLib.assertEquals(Sets.newHashSet("a"), ImmutableSet.of("a", "b"));
      throw new NotThrown(AssertionError.class);
    } catch (AssertionError e) {
    }

    // missing and unexpected items
    try {
      SetLib.assertEquals(Sets.newHashSet("a", "b"), ImmutableSet.of("a", "c"));
      throw new NotThrown(AssertionError.class);
    } catch (AssertionError e) {
    }
  }
}
