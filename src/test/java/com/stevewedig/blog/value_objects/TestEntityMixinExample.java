package com.stevewedig.blog.value_objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestEntityMixinExample {

  static boolean LOOK_AT_PRINTING = false;

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
      super();
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
    CompareLib.assertDifferentValueAndSameString(bob1, bob2);

    // mutate
    bob2.setAge(41);

    // now they should't print the same
    CompareLib.assertDifferentValueAndDifferentString(bob1, bob2);

    // nail down the printing behavior
    // (if the Person class wasn't nested, the printed name wouldn't have the
    // "TestEntityMixinExample$" prefix)
    assertEquals("TestEntityMixinExample$Person{name=bob, age=40}", bob1.toString());

  }

}
