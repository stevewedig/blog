package com.stevewedig.foundation.value_object;


/**
 * An object with a ValueObjectHelper, usually a sublcass of ValueMixin.
 */
public interface ValueObject extends ReferenceObject {

  @Override
  ValueObjectHelper objectHelper();
}
