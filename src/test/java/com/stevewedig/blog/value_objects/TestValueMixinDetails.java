package com.stevewedig.blog.value_objects;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TestValueMixinDetails {

  // ===========================================================================
  // helpers should be cached on value objects
  // ===========================================================================

  @Test
  public void testCaching() {

    HashCounterContainerClass container = new HashCounterContainerClass();

    // make sure the valueHelper is cached
    assertThat(((HasObjectHelper) container).objectHelper(),
        sameInstance(((HasObjectHelper) container).objectHelper()));

    assertEquals(container.hashCounter.hashCount, 0);

    container.hashCode();
    assertEquals(container.hashCounter.hashCount, 1);

    // count stays at 1 forever
    container.hashCode();
    assertEquals(container.hashCounter.hashCount, 1);
    container.hashCode();
    assertEquals(container.hashCounter.hashCount, 1);
  }

  private static class HashCounterClass {

    public int hashCount = 0;

    @Override
    public int hashCode() {
      hashCount++;
      return super.hashCode();
    }
  }

  private static class HashCounterContainerClass extends ValueMixin {
    public HashCounterClass hashCounter = new HashCounterClass();

    @Override
    public Object[] fields() {
      return array("hashCounter", hashCounter);
    }
  }

  // ===========================================================================
  // making sure behavior (classes) are checked as well as state (fields)
  // ===========================================================================

  @Test
  public void testBehaviorChecking() {

    // same state (fields) but different behavior (classes)
    A a = new A("val");
    B b = new B("val");

    CompareLib.assertUnequalObjectsAndStrings(a, b);
  }

  private static class A extends ValueMixin {

    private final String name;

    public A(String name) {
      super();
      this.name = name;
    }

    @Override
    protected Object[] fields() {
      return array("name", name);
    }
  }

  private static class B extends ValueMixin {

    private final String name;

    public B(String name) {
      super();
      this.name = name;
    }

    @Override
    protected Object[] fields() {
      return array("name", name);
    }
  }

}
