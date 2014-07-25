package com.stevewedig.blog.value_objects;

import com.stevewedig.blog.util.StrLib;

class PersonClassWithoutEntityMixin implements Person {
  private final String name;
  private int age;

  public PersonClassWithoutEntityMixin(String name, int age) {
    super();
    this.name = name;
    this.age = age;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getAge() {
    return age;
  }

  @Override
  public void setAge(int age) {
    this.age = age;
  }

  // ===========================================================================
  // toString() and valueEquals without EntityMixin
  // ===========================================================================

  @Override
  public String toString() {
    return StrLib.objectString(getClass(), "name", name, "age", age);
  }

}
