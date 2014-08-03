package com.stevewedig.blog.value_objects;

import static com.stevewedig.blog.util.StrLib.format;
import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Objects;

import com.google.common.collect.ImmutableMap;
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
  // fieldValues
  // ===========================================================================

  @Override
  public Object[] fieldValues() {
    if (fieldValues == null) {

      fieldValues = new Object[fieldCount()];

      for (int i = 0; i < fieldCount(); i++)
        fieldValues[i] = fieldNamesAndValues[i * 2 + 1];
    }

    return fieldValues;
  }

  // created lazily and cached
  private Object[] fieldValues;

  // ===========================================================================
  // fieldMap
  // ===========================================================================

  @Override
  public ImmutableMap<String, Object> fieldMap() {
    if (fieldMap == null) {

      ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();

      for (int i = 0; i < fieldNamesAndValues.length; i += 2) {
        String name = (String) fieldNamesAndValues[i];
        Object value = fieldNamesAndValues[i + 1];
        builder.put(name, value);
      }

      this.fieldMap = builder.build();
    }

    return fieldMap;
  }

  // created lazily and cached
  private ImmutableMap<String, Object> fieldMap;

  // ===========================================================================
  // objectString
  // ===========================================================================

  @Override
  public String classAndStateString() {
    return StrLib.classAndStateString(objectClass, fieldNamesAndValues);
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

  // ===========================================================================
  // state equals
  // ===========================================================================

  @Override
  public void assertStateEquals(HasObjectHelper other) {
    assertStateEquals(other.objectHelper());
  }

  private void assertStateEquals(ObjectHelper other) {

    if (Arrays.equals(fieldValues(), other.fieldValues()))
      return;

    // should specify which fields are different
    throw new AssertionError(format("Object states were not equal. Expecting: %s, but got %s",
        fieldValues(), other.fieldValues()));
  }

  // ===================================

  @Override
  public void assertStateNotEquals(HasObjectHelper other) {
    assertStateNotEquals(other.objectHelper());
  }

  private void assertStateNotEquals(ObjectHelper other) {
    if (!Arrays.equals(fieldValues(), other.fieldValues()))
      return;

    throw new AssertionError(format("Object states were equal, both were: %s", fieldValues()));
  }

}
