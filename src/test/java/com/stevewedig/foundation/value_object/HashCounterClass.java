package com.stevewedig.foundation.value_object;

/**
 * Helper for TestValueObjectCaching
 */
public class HashCounterClass {

  public int hashCount = 0;
  
  @Override
  public int hashCode() {
    hashCount++;
    return super.hashCode();
  }
}
