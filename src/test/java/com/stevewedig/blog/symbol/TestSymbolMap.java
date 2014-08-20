package com.stevewedig.blog.symbol;

import static com.stevewedig.blog.symbol.SymbolLib.map;
import static com.stevewedig.blog.symbol.SymbolLib.symbol;
import static com.stevewedig.blog.value_objects.ObjectHelperLib.assertStateEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.errors.NotContained;
import com.stevewedig.blog.errors.NotThrown;

public class TestSymbolMap {

  // symbols
  static Symbol<Boolean> $bool = symbol(Boolean.class);
  static Symbol<Integer> $int = symbol(Integer.class);
  static Symbol<Integer> $unused = symbol("unused");
  static Symbol<Object> $null = symbol(Object.class);

  // values
  static Boolean boolValue = true;
  static Integer intValue = 1;

  // ===========================================================================
  // verify behavior
  // ===========================================================================

  @Test
  public void testSymbolMapExample() {

    // create using one put per line
    // (not using fluent builder syntax)
    SymbolMap.Fluid fluid1 = map();
    fluid1.put($bool, boolValue);
    fluid1.put($int, intValue);
    fluid1.put($null, null);
    verifyExampleMap(fluid1);

    // solid copy
    SymbolMap solid1 = fluid1.solid();
    verifyExampleMap(solid1);

    // create using the fluent builder syntax
    SymbolMap solid2 = map().put($bool, boolValue).put($int, intValue).put($null, null).solid();
    verifyExampleMap(solid2);

    // verify solid are values
    assertEquals(solid1, solid2);

    // verify fluid are entities
    SymbolMap.Fluid fluid2 = map().put($bool, boolValue).put($int, intValue).put($null, null);
    assertNotEquals(fluid1, fluid2);

  }

  private void verifyExampleMap(SymbolMap map) {

    // symbols
    assertEquals(map.symbols(), ImmutableSet.<Symbol<?>>of($int, $bool, $null));
    assertTrue(map.contains($bool));
    assertTrue(map.contains($int));
    assertTrue(map.contains($null));
    assertFalse(map.contains($unused));

    // get hit
    assertEquals(boolValue, map.get($bool));
    assertEquals(intValue, map.get($int));
    assertNull(map.get($null));

    // get miss
    try {
      map.get($unused);
      throw new NotThrown(NotContained.class);
    } catch (NotContained e) {
    }

    // getDefault
    assertEquals(intValue, map.getDefault($int, 999));
    assertEquals((Integer) 999, map.getDefault($unused, 999));

    // getOptional
    assertEquals(Optional.of(intValue), map.getOptional($int));
    assertEquals(Optional.absent(), map.getOptional($unused));


    // getNullable
    assertEquals(intValue, map.getNullable($int));
    assertNull(map.getNullable($unused));

    // getOptional with adaptNull = False

    // getNullable without adaptNull = false

    // transitions
    assertEquals(map.fluid().solid(), map.fluid().solid());
    assertStateEquals(map, map.fluid());

    // stateCopy
    Map<Symbol<?>, Object> state = new HashMap<>();
    state.put($bool, boolValue);
    state.put($int, intValue);
    state.put($null, null);
    assertEquals(state, map.stateCopy());
  }

}
