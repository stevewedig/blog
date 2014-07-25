package com.stevewedig.blog.value_objects;


/**
 * Mixin for entity objects that implements toString()
 */
public abstract class EntityMixin extends ObjectMixin {

  @Override
  protected boolean isEntity() {
    return true;
  }

}
