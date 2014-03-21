package com.stevewedig.foundation.value_object;

import com.stevewedig.foundation.value_object.ValueMixin;
import com.stevewedig.foundation.value_object.ValueObjectHelper;

/**
 * Helper for TestValueObjectCaching
 */
public class HashCounterContainerClass extends ValueMixin {
  public HashCounterClass hashCounter = new HashCounterClass();

  @Override
  public ValueObjectHelper objectHelper() {
    return objectHelper("hashCounter", hashCounter);
  } 
  
}
