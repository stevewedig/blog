package com.stevewedig.blog.value_objects;

import static com.stevewedig.blog.value_objects.CompareLib.assertUnequalObjectsAndStrings;
import static com.stevewedig.blog.value_objects.CompareLib.assertUnequalObjectsButEqualStrings;

import org.junit.Test;

public class TestEntityMixinExample {

  // ===========================================================================
  // Person
  // ===========================================================================

  static class Person extends EntityMixin {
    private final String name;
    private int age;

    @Override
    public Object[] fields() {
      return array("name", name, "age", age);
    }

    public Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public String name() {
      return name;
    }

    public int age() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

  }

  // ===========================================================================
  // test
  // ===========================================================================

  @Test
  public void testEntityMixinExample() {

    Person bob1 = new Person("bob", 40);
    Person bob2 = new Person("bob", 40);

    // double check that we have different values but they print the same
    assertUnequalObjectsButEqualStrings(bob1, bob2);

    // mutate
    bob2.setAge(41);

    // now they should't print the same
    assertUnequalObjectsAndStrings(bob1, bob2);

  }

}
