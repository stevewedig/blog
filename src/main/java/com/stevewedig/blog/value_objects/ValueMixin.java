package com.stevewedig.blog.value_objects;


/**
 * Mixin for value objects that implements toString(), equals(), and hashCode()
 */
public abstract class ValueMixin extends ObjectMixin {

  @Override
  protected boolean isEntity() {
    return false;
  }

  @Override
  public boolean equals(Object other) {
    return objectHelper().classAndStateEquals(other);
  }

  @Override
  public int hashCode() {
    return objectHelper().classAndStateHash();
  }

}
