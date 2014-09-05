package com.stevewedig.blog.util;

import static com.stevewedig.blog.util.StrLib.classAndStateString;
import static com.stevewedig.blog.util.StrLib.format;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestStrLib {

  // ===========================================================================
  // classAndStateString
  // ===========================================================================

  @Test
  public void testClassAndStateString() {

    assertEquals("Boolean{a=1}", classAndStateString(Boolean.class, "a", 1));

    assertEquals("x{a=1}", classAndStateString("x", "a", 1));

  }

  // ===========================================================================
  // format
  // ===========================================================================

  @Test
  public void testFormat() {

    assertEquals("1 a 2 true 3", format("1 %s 2 %s 3", "a", true));

  }
}
