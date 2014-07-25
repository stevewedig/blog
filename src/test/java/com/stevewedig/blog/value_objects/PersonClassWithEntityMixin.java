package com.stevewedig.blog.value_objects;

import com.stevewedig.blog.value_objects.EntityMixin;



class PersonClassWithEntityMixin extends EntityMixin implements Person {
  private final String name;
  private int age;

  public PersonClassWithEntityMixin(String name, int age) {
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
  // EntityMixin
  // ===========================================================================

  @Override
  public Object[] fields() {
    return array("name", name, "age", age);    
  }
}
