package com.stevewedig.blog.value_objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.stevewedig.blog.value_objects.EntityMixin;

/**
 * Verifying and demonstrating the toString() helper and EntityMixin
 */
public class TestEntityExampleBasic {

  static boolean LOOK_AT_PRINTING = false;

  @Test
  public void testWithEntityMixin() {

    Person bob1 = new PersonClassWithEntityMixin("bob", 40);
    Person bob2 = new PersonClassWithEntityMixin("bob", 40);

    verify(bob1, bob2);

  }

  @Test
  public void testWithOutEntityMixin() {

    Person bob1 = new PersonClassWithoutEntityMixin("bob", 40);
    Person bob2 = new PersonClassWithoutEntityMixin("bob", 40);

    verify(bob1, bob2);
  }

  private void verify(Person bob1, Person bob2) {
    
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
