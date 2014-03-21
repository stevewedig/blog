package com.stevewedig.foundation.value_object;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

/**
 * Helpers for verifying whether .equals(), .hashCode(), and .toString() match
 */
public abstract class TestHelpers {

  public static void assertSameValueAndSameString(Object a, Object b) {
    assertThat(a, not(sameInstance(b)));
    
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
    
    assertEquals(a.toString(), b.toString());
  }

  public static void assertDifferentValueAndDifferentString(Object a, Object b) {
    assertThat(a, not(sameInstance(b)));
    
    assertNotEquals(a, b);
    assertNotEquals(a.hashCode(), b.hashCode());
    
    assertNotEquals(a.toString(), b.toString());    
  }

  public static void assertDifferentValueAndSameString(Object a, Object b) {
    assertThat(a, not(sameInstance(b)));
    
    assertNotEquals(a, b);
    assertNotEquals(a.hashCode(), b.hashCode());
    
    assertEquals(a.toString(), b.toString());    
  }
}
