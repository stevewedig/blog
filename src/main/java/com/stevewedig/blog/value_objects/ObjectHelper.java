package com.stevewedig.blog.value_objects;

import com.google.common.collect.ImmutableMap;

/**
 * An object that helps implement toString(), equals(), and hashCode().
 */
public interface ObjectHelper {

  // ===========================================================================
  // helper methods
  // ===========================================================================

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

  // ===========================================================================
  // exposing state
  // ===========================================================================

  /**
   * Exposed for class and state comparison.
   */
  Object[] classAndFieldValues();

  /**
   * Exposed for state comparison.
   */
  Object[] fieldValues();

  /**
   * Exposed for conveninece.
   */
  ImmutableMap<String, Object> fieldMap();

  // ===========================================================================
  // state comparison
  // ===========================================================================

  /**
   * Used to compare the state of entity objects or value objects with different classes.
   */
  void assertStateEquals(HasObjectHelper other);

  /**
   * Used to compare the state of entity objects or value objects with different classes.
   */
  void assertStateNotEquals(HasObjectHelper other);
}
