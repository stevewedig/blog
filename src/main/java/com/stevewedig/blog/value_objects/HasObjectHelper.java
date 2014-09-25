package com.stevewedig.blog.value_objects;


/**
 * An object with an ObjectHelper, which will usually be in instance of ValueMixin or EntityMixin.
 */
public interface HasObjectHelper {

  /**
   * Get an object's objectHelper(), for use in implementing toString(), equals(), and hashCode().
   */
  ObjectHelper objectHelper();
}
