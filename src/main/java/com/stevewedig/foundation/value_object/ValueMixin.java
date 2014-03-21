package com.stevewedig.foundation.value_object;


/**
 * A mixin for value objects to implement toString, equals, and hashCode by delegating to a ValueObjectHelper.
 */
public abstract class ValueMixin implements ValueObject {

  // ===========================================================================
  // creating the helper
  // ===========================================================================

  protected ValueObjectHelper objectHelper(Object... fieldNamesAndValues) {
    return ObjectHelperLib.valueObjectHelper(getClass(), fieldNamesAndValues);
  }

  // ===========================================================================
  // caching the helper
  // ===========================================================================

  public ValueObjectHelper cachedObjectHelper() {

    if (cachedObjectHelper != null)
      return cachedObjectHelper;

    ValueObjectHelper objectHelper = objectHelper();

    if (ObjectHelperLib.DO_CACHE)
      cachedObjectHelper = objectHelper;

    return objectHelper;
  }

  private ValueObjectHelper cachedObjectHelper;

  // ===========================================================================
  // delegate to helper
  // ===========================================================================
  
  @Override
  final public String toString() {
    return cachedObjectHelper().toStringHelper();
  }

  @Override
  final public int hashCode() {
    return cachedObjectHelper().hashCodeHelper();
  }

  @Override
  final public boolean equals(Object other) {
    return cachedObjectHelper().equalsHelper(other);
  }

}
