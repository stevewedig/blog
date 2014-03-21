package com.stevewedig.foundation.value_object;

/**
 * Library for creating Persons
 */
public abstract class PersonLib {

  public static Person personWithReferenceMixin(String name, int age) {
    return new PersonClassWithReferenceMixin(name, age);
  }

  public static Person personWithoutReferenceMixin(String name, int age) {
    return new PersonClassWithoutReferenceMixin(name, age);
  }
}
