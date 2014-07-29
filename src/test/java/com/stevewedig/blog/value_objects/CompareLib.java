package com.stevewedig.blog.value_objects;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

abstract class CompareLib {

  public static void assertEqualObjectsAndStrings(Object a, Object b) {
    assertThat(a, not(sameInstance(b)));

    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());

    assertEquals(a.toString(), b.toString());
  }

  public static void assertUnequalObjectsAndStrings(Object a, Object b) {
    assertThat(a, not(sameInstance(b)));

    assertNotEquals(a, b);

    if (b != null) {
      assertNotEquals(a.hashCode(), b.hashCode());
      assertNotEquals(a.toString(), b.toString());
    }
  }

  public static void assertUnequalObjectsButEqualStrings(Object a, Object b) {
    assertThat(a, not(sameInstance(b)));

    assertNotEquals(a, b);
    assertNotEquals(a.hashCode(), b.hashCode());

    assertEquals(a.toString(), b.toString());
  }
}
