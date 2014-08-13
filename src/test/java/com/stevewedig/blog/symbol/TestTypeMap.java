package com.stevewedig.blog.symbol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.stevewedig.blog.errors.NotContained;
import com.stevewedig.blog.errors.NotThrown;

public class TestTypeMap {

  @Test
  public void testTypeMapExample() {

    TypeMap map = SymbolLib.typeMap();

    // initial state
    assertTrue(map.isEmpty());
    assertEquals(0, map.size());
    assertFalse(map.containsKey(Integer.class));
    assertFalse(map.containsKey(Boolean.class));

    // not contained
    try {
      map.get(Integer.class);
      throw new NotThrown(NotContained.class);
    } catch (NotContained e) {
    }

    // insert int
    map.put(Integer.class, 1);
    assertFalse(map.isEmpty());
    assertEquals(1, map.size());
    assertTrue(map.containsKey(Integer.class));
    assertEquals((Integer) 1, map.get(Integer.class));
    assertFalse(map.containsKey(Boolean.class));

    // insert bool
    map.put(Boolean.class, true);
    assertFalse(map.isEmpty());
    assertEquals(2, map.size());
    assertTrue(map.containsKey(Integer.class));
    assertEquals((Integer) 1, map.get(Integer.class));
    assertTrue(map.containsKey(Boolean.class));
    assertEquals((Boolean) true, map.get(Boolean.class));

    // update int
    map.put(Integer.class, 2);
    assertFalse(map.isEmpty());
    assertEquals(2, map.size());
    assertTrue(map.containsKey(Integer.class));
    assertEquals((Integer) 2, map.get(Integer.class));
    assertTrue(map.containsKey(Boolean.class));
    assertEquals((Boolean) true, map.get(Boolean.class));

    // remove int
    map.remove(Integer.class);
    assertFalse(map.containsKey(Integer.class));
    assertFalse(map.isEmpty());
    assertEquals(1, map.size());
    assertFalse(map.containsKey(Integer.class));
    assertTrue(map.containsKey(Boolean.class));
    assertEquals((Boolean) true, map.get(Boolean.class));
    
    // clear
    map.clear();
    assertTrue(map.isEmpty());
    assertEquals(0, map.size());
    assertFalse(map.containsKey(Integer.class));
    assertFalse(map.containsKey(Boolean.class));
  }

  // ===========================================================================

  @Test
  public void testTypeMapKeySubtypes() {

    TypeMap map = SymbolLib.typeMap();

    C c1 = new C();
    C c2 = new C();
    C c3 = new C();
    
    map.put(Object.class, c1); // super class
    map.put(I.class, c2); // super interface
    map.put(C.class, c3); // class
    
    assertEquals(c1, map.get(Object.class));
    assertEquals(c2, map.get(I.class));
    assertEquals(c3, map.get(C.class));
    
  }

  interface I {
  }
  
  static class C implements I {
  }

}
