package com.stevewedig.blog.symbol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.stevewedig.blog.errors.NotThrown;
import com.stevewedig.blog.util.CastLib;

public class TestGetAndCast {

  @Test
  public void testGetAndCast() {

    Map<String, Object> map = new HashMap<>();

    map.put("name", "Bob");
    map.put("age", 20);
    map.put("null", null);

    // =================================
    // basic usage
    // =================================

    // with type inference (of the generic method's parameter)
    String name = CastLib.get(map, "name");
    assertEquals("Bob", name);

    // without type interference (manually providing the generic method's parameter)
    assertEquals((Integer) 20, CastLib.<Integer>get(map, "age"));

    // =================================
    // invalid cast demonstrating that getAndCast is not type safe
    // =================================

    try {

      @SuppressWarnings("unused")
      Integer fail = CastLib.get(map, "name");

      throw new NotThrown(ClassCastException.class);

    } catch (ClassCastException e) {
    }

    // =================================
    // null can be casted to any type
    // =================================

    String nullStr = CastLib.get(map, "null");
    assertNull(nullStr);

    Integer nullInt = CastLib.get(map, "null");
    assertNull(nullInt);
  }

}
