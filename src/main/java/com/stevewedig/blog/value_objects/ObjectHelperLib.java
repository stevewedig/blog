package com.stevewedig.blog.value_objects;



/**
 * Library for working with ObjectHelpers.
 */
public abstract class ObjectHelperLib {

  /**
   * Create an object helper.
   */
  public static ObjectHelper helper(Class<?> objectClass, Object... fieldNamesAndValues) {
    return new ObjectHelperClass(objectClass, fieldNamesAndValues);
  }
  
  // ===========================================================================

  /**
   * Assert that two objects with helpers have the same state.
   */
  public static void assertStateEquals(HasObjectHelper a, HasObjectHelper b) {
    a.objectHelper().assertStateEquals(b);
  }

  /**
   * Assert that two objects with helpers have different state.
   */
  public static void assertStateNotEquals(HasObjectHelper a, HasObjectHelper b) {
    a.objectHelper().assertStateNotEquals(b);
  }

}
