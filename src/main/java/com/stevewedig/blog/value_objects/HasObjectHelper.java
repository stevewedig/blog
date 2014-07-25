package com.stevewedig.blog.value_objects;


/**
 * An object with an ObjectHelper, which will usually be in instance of ValueMixin or EntityMixin.
 */
public interface HasObjectHelper {

  ObjectHelper objectHelper();
}
