package com.stevewedig.foundation.value_object;


/**
 * A mutable Reference Object for demonstrating the toString() helper and mixin 
 */
public interface Person {
  String getName();

  int getAge();

  void setAge(int age);
}
