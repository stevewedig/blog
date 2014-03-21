package com.stevewedig.foundation.value_object;


/**
 * A library for creating object helpers, but if you use ValueMixin or ReferenceMixin you won't need this.
 */
public abstract class ObjectHelperLib {

  /**
   * Whether ValueMixin and ValueObjectHelper should do caching, which defaults to true.
   */
  public static boolean DO_CACHE = true;
    
  /**
   * Create a helper for ValueObject
   */
  public static ValueObjectHelper valueObjectHelper(Class<?> objectClass, Object... fieldNamesAndValues) {
    return new ObjectHelperClass(DO_CACHE, objectClass, fieldNamesAndValues);
  }
  
  /**
   * Create a helper for ReferenceObject
   */
  public static ObjectHelper objectHelper(Class<?> objectClass, Object... fieldNamesAndValues) {
    return new ObjectHelperClass(false, objectClass, fieldNamesAndValues);
  }
  
  // TODO move this onto StrLib
  /**
   * Quick way to implement toString() when you can't subclass ReferenceMixin or ValueMixin
   */
  public static String strHelper(Class<?> objectClass, Object... fieldNamesAndValues) {
    return objectHelper(objectClass, fieldNamesAndValues).toStringHelper();
  }
}
