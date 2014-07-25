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
    
    // double check that we have different values but they print the smae
    CompareLib.assertDifferentValueAndSameString(bob1, bob2);

    assertEquals(bob1.toString(), bob2.toString());

    // mutate
    bob2.setAge(41);

    // now these bobs should't print the same
    CompareLib.assertDifferentValueAndDifferentString(bob1, bob2);

    // look at printing
    if (LOOK_AT_PRINTING) {

      System.out.println("printing print objects...");
      System.out.println(bob1);
      System.out.println(bob2);

      System.out.println("\nprinting object state...");
      System.out.println(((EntityMixin) bob1).fields());
      System.out.println(((EntityMixin) bob2).fields());

      throw new RuntimeException("check console to see what print object's toString() looks like");
    }
  }

}
