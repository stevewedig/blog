package com.stevewedig.blog.value_objects;

/**
 * An object that helps implement toString(), equals(), and hashCode().
 */
public interface ObjectHelper {

  /**
   * Implements toString for both entity and value objects
   */
  String classAndStateString();

  /**
   * Implements equals for value objects
   */
  boolean classAndStateEquals(Object otherObject);

  /**
   * Implements hashCode for value objects
   */
  int classAndStateHash();

  /**
   * This is just exposed so helpers can compare state with each other.
   */
  Object[] classAndFieldValues();

}
