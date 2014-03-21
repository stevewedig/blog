package com.stevewedig.foundation.value_object;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

/**
 * Helper for implementing toString, equals, and hashCode.
 */
class ObjectHelperClass extends ValueMixin implements ValueObjectHelper {

  private final boolean doCache;
  private final Class<?> objectClass;
  private final Object[] fieldNamesAndValues;
  private Object[] fieldValues;

  public ObjectHelperClass(boolean doCache, Class<?> objectClass, Object[] fieldNamesAndValues) {
    this.doCache = doCache;
    this.objectClass = requireNonNull(objectClass);
    this.fieldNamesAndValues = requireNonNull(fieldNamesAndValues);

    if (fieldNamesAndValues.length % 2 != 0)
      throw new RuntimeException(
          "ValueObjectHelper fieldNamesAndValues must be an even length, pairs of names and values");
  }

  @Override
  public Object[] fieldValues() {
    if (fieldValues == null) {
      fieldValues = new Object[fieldCount()];

      for (int i = 0; i < fieldCount(); i++)
        fieldValues[i] = fieldNamesAndValues[i * 2 + 1];
    }
    return fieldValues;
  }

  private int fieldCount() {
    return fieldNamesAndValues.length / 2;
  }

  // ===========================================================================
  // ValueObjectHelper.toStringHelper
  // ===========================================================================

  @Override
  public String toStringHelper() {
    ToStringHelper builder = Objects.toStringHelper(simpleClassName());

    for (int i = 0; i < fieldCount(); i++) {
      String name = (String) fieldNamesAndValues[i * 2];
      Object value = fieldNamesAndValues[i * 2 + 1];
      builder.add(name, value);
    }

    return builder.toString();
  }

  // objectClass.getSimpleName() is not implemented in GWT
  private String simpleClassName() {    
    String dottedPath = objectClass.getName();
    ImmutableList<String> parts = ImmutableList.copyOf(Splitter.on('.').split(dottedPath));
    return parts.get(parts.size() - 1);
  }

  // ===========================================================================
  // ValueObjectHelper.equalsHelper
  // ===========================================================================

  /*
   * Even if two objects are ValueObjects with identical state, if they have different classes, then
   * they can have different behavior and derived attributes. So in the general case they are not
   * indistinguishable from each other. Holding a reference to one may have a different
   * impact/outcome than holding a reference to the other. They are not the same value. The are not
   * equal.
   */
  @Override
  public boolean equalsHelper(Object otherObject) {

    if (otherObject == null)
      return false;

    if (objectClass != otherObject.getClass())
      return false;

    // this cast is safe because we know otherObject's class matches
    ValueObject otherValueObject = (ValueObject) otherObject;

    ValueObjectHelper otherState = otherValueObject.objectHelper();

    Object[] otherFieldValues = otherState.fieldValues();

    return Arrays.equals(fieldValues(), otherFieldValues);
  }

  // ===========================================================================
  // ValueObjectHelper.hashCodeHelper
  // ===========================================================================

  /*
   * For immutable objects we can safely cache this because ValueObjects are immutable. This is
   * probably unnecessary for small objects, however it may help for large nested objects.
   * Regardless of an Entity's size, computing its hashCode is always fast because it is derived
   * from the object's memory location. In contrast, computing a ValueObject's hashCode requires
   * traversing all nested ValueObjects. Caching this computation is helpful because its cost may
   * not be trivial, and because hashCode can be called quite often if you are putting ValueObjects
   * into Sets or using them as Map keys.
   */
  @Override
  public int hashCodeHelper() {
    if (cachedHashCode != null)
      return cachedHashCode;

    int hashCode = java.util.Objects.hash(fieldValues());

    if (doCache)
      cachedHashCode = hashCode;

    return hashCode;
  }

  private Integer cachedHashCode;

  // ===========================================================================
  // ValueObject.objectHelper
  // ===========================================================================

  /*
   * http://en.wikipedia.org/wiki/Turtles_all_the_way_down
   */
  @Override
  public ValueObjectHelper objectHelper() {

    // Lists print nested objects better than Arrays do
    return objectHelper("doCache", doCache, "objectClass", objectClass, "fieldNamesAndValues",
        Arrays.asList(fieldNamesAndValues));
  }

}
