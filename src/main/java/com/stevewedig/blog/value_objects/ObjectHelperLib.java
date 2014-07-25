package com.stevewedig.blog.value_objects;



/**
 * Library for creating object helpers, but if you use ValueMixin or EntityMixin you won't need
 * this.
 */
public abstract class ObjectHelperLib {

  public static ObjectHelper helper(Class<?> objectClass, Object... fieldNamesAndValues) {
    return new ObjectHelperClass(objectClass, fieldNamesAndValues);
  }

}
