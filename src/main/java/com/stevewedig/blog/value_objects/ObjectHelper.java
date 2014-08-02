package com.stevewedig.blog.value_objects;

import com.google.common.collect.ImmutableMap;

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
   * Exposed so helpers can compare class and fields with each other.
   */
  Object[] classAndFieldValues();

  /**
   * Exposed for conveninece and state comparison.
   */
  ImmutableMap<String, Object> fieldMap();

  /**
   * Used to compare the state of entity objects or value objects with different classes.
   */
  void assertStateEquals(HasObjectHelper other);

  /**
   * Used to compare the state of entity objects or value objects with different classes.
   */
  void assertStateNotEquals(HasObjectHelper other);
}
