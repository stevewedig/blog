package com.stevewedig.foundation.value_object;


/**
 * A mixin for any object to implement toString by delegating to a ObjectHelper.
 */
public abstract class ReferenceMixin implements ReferenceObject {
  
  // ===========================================================================
  // creating the helper
  // ===========================================================================
  
  protected ObjectHelper objectHelper(Object... fieldNamesAndValues) {
    return ObjectHelperLib.objectHelper(getClass(), fieldNamesAndValues);
  }

  // ===========================================================================
  // delegate to helper
  // ===========================================================================

  @Override
  final public String toString() {
    return objectHelper().toStringHelper();
  }


}
