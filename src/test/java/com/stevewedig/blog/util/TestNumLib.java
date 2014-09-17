package com.stevewedig.blog.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestNumLib {


  @Test
  public void testIsEven() {
    assertTrue(NumLib.isEven(-2));
    assertFalse(NumLib.isEven(-1));
    assertTrue(NumLib.isEven(0));
    assertFalse(NumLib.isEven(1));
    assertTrue(NumLib.isEven(2));
  }

  @Test
  public void testIsOdd() {
    assertFalse(NumLib.isOdd(-2));
    assertTrue(NumLib.isOdd(-1));
    assertFalse(NumLib.isOdd(0));
    assertTrue(NumLib.isOdd(1));
    assertFalse(NumLib.isOdd(2));
  }


}
