package com.stevewedig.foundation.value_object;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.stevewedig.foundation.value_object.ValueMixin;

/**
 * Verify that ValueObject helpers and hashes are cached 
 */
public class TestValueObjectCaching {

  @Test
  public void testCaching() {
    
    HashCounterContainerClass container = new HashCounterContainerClass();
    
    // make sure the cachedObjectHelper is actually cached
    // - note that .objectHelper() is not cached
    assertThat(((ValueMixin) container).cachedObjectHelper(), sameInstance(((ValueMixin) container).cachedObjectHelper()));
    
    assertEquals(container.hashCounter.hashCount, 0);
    
    container.hashCode();
    assertEquals(container.hashCounter.hashCount, 1);

    // count stays at 1 forever
    container.hashCode();
    assertEquals(container.hashCounter.hashCount, 1);
    container.hashCode();
    assertEquals(container.hashCounter.hashCount, 1);
  }
  
}
