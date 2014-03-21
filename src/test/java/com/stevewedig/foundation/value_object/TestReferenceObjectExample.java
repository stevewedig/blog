package com.stevewedig.foundation.value_object;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.stevewedig.foundation.value_object.ReferenceObject;

/**
 * Verifying and demonstrating the toString() helper and ReferenceMixin
 */
public class TestReferenceObjectExample {

  static boolean LOOK_AT_PRINTING = false;

  @Test
  public void testWithReferenceMixin() {

    Person bob1 = PersonLib.personWithReferenceMixin("bob", 40);
    Person bob2 = PersonLib.personWithReferenceMixin("bob", 40);

    // verify that the objectHelpers are not cached (as they are with ValueObjects)
    assertThat(((ReferenceObject) bob1).objectHelper(),
        not(sameInstance(((ReferenceObject) bob1).objectHelper())));

    verify(bob1, bob2);
  }

  @Test
  public void testWithOutReferenceMixin() {

    Person bob1 = PersonLib.personWithoutReferenceMixin("bob", 40);
    Person bob2 = PersonLib.personWithoutReferenceMixin("bob", 40);

    verify(bob1, bob2);
  }

  private void verify(Person bob1, Person bob2) {
    // double check that we have different values but they print the smae
    TestHelpers.assertDifferentValueAndSameString(bob1, bob2);

    assertEquals(bob1.toString(), bob2.toString());

    // mutate
    bob2.setAge(41);

    // now these bobs should't print the same
    TestHelpers.assertDifferentValueAndDifferentString(bob1, bob2);

    // look at printing
    if (LOOK_AT_PRINTING) {

      System.out.println("printing print objects...");
      System.out.println(bob1);
      System.out.println(bob2);

      System.out.println("\nprinting object helpers...");
      System.out.println(((ReferenceObject) bob1).objectHelper());
      System.out.println(((ReferenceObject) bob2).objectHelper());

      throw new RuntimeException("check console to see what print object's toString() looks like");
    }
  }

}
