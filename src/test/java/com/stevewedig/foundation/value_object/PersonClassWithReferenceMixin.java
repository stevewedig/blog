package com.stevewedig.foundation.value_object;

import com.stevewedig.foundation.value_object.ObjectHelper;
import com.stevewedig.foundation.value_object.ReferenceMixin;

/**
 * A Reference Object implementing toString with ReferenceMixin  
 */
class PersonClassWithReferenceMixin extends ReferenceMixin implements Person {
  private final String name;
  private int age;

  public PersonClassWithReferenceMixin(String name, int age) {
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
  // ReferenceMixin
  // ===========================================================================

  @Override
  public ObjectHelper objectHelper() {
    return objectHelper("name", name, "age", age);    
  }
}
