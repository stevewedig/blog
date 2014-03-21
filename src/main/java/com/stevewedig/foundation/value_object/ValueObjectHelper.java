package com.stevewedig.foundation.value_object;

/**
 * An object that helps with toString, equals, and hashCode.
 */
public interface ValueObjectHelper extends ObjectHelper {

  /**
   * Implements value object equals by comparing class and state (field values).
   */
  boolean equalsHelper(Object otherObject);

  /**
   * Implements value object hashCode by hashing the state (field values).
   */
  int hashCodeHelper();

  /**
   * This is just exposed for implementation reasons (so helpers can test equality with other helpers).
   */
  Object[] fieldValues();

}
