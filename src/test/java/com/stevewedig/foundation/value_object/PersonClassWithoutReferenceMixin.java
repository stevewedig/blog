package com.stevewedig.foundation.value_object;

import com.stevewedig.foundation.value_object.ObjectHelperLib;

/**
 * A Reference Object implementing toString with ObjectHelperLib.toStringHelper()  
 */
class PersonClassWithoutReferenceMixin implements Person {
  private final String name;
  private int age;

  public PersonClassWithoutReferenceMixin(String name, int age) {
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
  // toString() without ReferenceMixin
  // ===========================================================================

  @Override
  public String toString() {
    return ObjectHelperLib.strHelper(getClass(), "name", name, "age", age);    
  }
}
