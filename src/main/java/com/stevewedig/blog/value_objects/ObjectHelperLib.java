package com.stevewedig.blog.value_objects;



/**
 * Library for working with ObjectHelpers.
 */
public abstract class ObjectHelperLib {

  public static ObjectHelper helper(Class<?> objectClass, Object... fieldNamesAndValues) {
    return new ObjectHelperClass(objectClass, fieldNamesAndValues);
  }
  
  // ===========================================================================

  public static void assertStateEquals(HasObjectHelper a, HasObjectHelper b) {
    a.objectHelper().assertStateEquals(b);
  }

  public static void assertStateNotEquals(HasObjectHelper a, HasObjectHelper b) {
    a.objectHelper().assertStateNotEquals(b);
  }

}
