package com.stevewedig.blog.util;

import static com.stevewedig.blog.util.StrLib.classAndStateString;
import static com.stevewedig.blog.util.StrLib.format;
import static com.stevewedig.blog.util.StrLib.isBlank;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestStrLib {

  // ===========================================================================
  // isBlank
  // ===========================================================================

  @Test
  public void testIsBlank() {

    assertTrue(isBlank(""));
    assertTrue(isBlank(" "));
    assertTrue(isBlank("\t"));
    assertTrue(isBlank(" \t\n"));

    assertFalse(isBlank("x"));
    assertFalse(isBlank(" x"));
    assertFalse(isBlank("x "));
    assertFalse(isBlank(" x "));
    assertFalse(isBlank("\tx"));

  }

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
