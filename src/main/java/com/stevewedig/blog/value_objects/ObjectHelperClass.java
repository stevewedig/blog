package com.stevewedig.blog.value_objects;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Objects;

import com.stevewedig.blog.errors.Bug;
import com.stevewedig.blog.util.StrLib;

class ObjectHelperClass implements ObjectHelper {

  private final Class<?> objectClass;
  private final Object[] fieldNamesAndValues;

  public ObjectHelperClass(Class<?> objectClass, Object[] fieldNamesAndValues) {

    this.objectClass = requireNonNull(objectClass);
    this.fieldNamesAndValues = requireNonNull(fieldNamesAndValues);

    if (fieldNamesAndValues.length % 2 != 0)
      throw new Bug(
          "ObjectHelper fieldNamesAndValues must be an even length: alternating pairs of names and values");
  }

  private int fieldCount() {
    return fieldNamesAndValues.length / 2;
  }

  // ===========================================================================
  // classAndFieldValues
  // ===========================================================================

  @Override
  public Object[] classAndFieldValues() {
    if (classAndFieldValues == null) {

      classAndFieldValues = new Object[fieldCount() + 1];

      classAndFieldValues[0] = objectClass;

      for (int i = 0; i < fieldCount(); i++)
        classAndFieldValues[i + 1] = fieldNamesAndValues[i * 2 + 1];
    }

    return classAndFieldValues;
  }

  // created lazily and cached
  private Object[] classAndFieldValues;

  // ===========================================================================
  // objectString
  // ===========================================================================

  @Override
  public String objectString() {
    return StrLib.objectStr(objectClass, fieldNamesAndValues);
  }

  // ===========================================================================
  // classAndStateEquals
  // ===========================================================================

  @Override
  public boolean classAndStateEquals(Object otherObject) {

    if (otherObject == null)
      return false;

    if (!(otherObject instanceof HasObjectHelper))
      return false;

    ObjectHelper otherHelper = ((HasObjectHelper) otherObject).objectHelper();

    return Arrays.equals(classAndFieldValues(), otherHelper.classAndFieldValues());
  }

  // ===========================================================================
  // valueHashCode
  // ===========================================================================

  /*
   * For immutable objects we can safely cache this because ValueObjects are immutable. This is may
   * be unnecessary for small objects, however it may help for large nested objects that are hashed
   * repeatedly.
   */
  @Override
  public int classAndStateHash() {
    if (classAndStateHash == null) {
      // note that this isn't hashing the array, it is using the array's contents as varargs
      classAndStateHash = Objects.hash(classAndFieldValues());
    }
    return classAndStateHash;
  }

  // created lazily and cached
  private Integer classAndStateHash;

}
