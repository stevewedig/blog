package com.stevewedig.blog.symbol;

import static com.stevewedig.blog.symbol.SymbolLib.map;
import static com.stevewedig.blog.symbol.SymbolLib.symbol;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.errors.NotContained;

public class TestSymbolMap {

  // symbols
  static Symbol<Integer> $a = symbol(Integer.class);
  static Symbol<Boolean> $b = symbol("mybool");
  static Symbol<Boolean> $c = symbol(Boolean.class);

  // values
  static Integer a = 1;
  static Boolean b = true;

  @Test
  public void testSymbolMapExample() {

    SymbolMap.Fluid mutable = map();
    mutable.put($a, a);
    mutable.put($b, b);
    verifyExampleMap(mutable);

    SymbolMap immutable = mutable.solid();
    verifyExampleMap(immutable);

    // fluent builder syntax
    SymbolMap immutable2 = map().put($a, a).put($b, b).solid();
    verifyExampleMap(immutable2);

    // immutable are values
    assertThat(immutable, equalTo(immutable2));

    // mutable are entities
    SymbolMap mutable2 = map().put($a, a).put($b, b);
    assertThat(mutable, not(equalTo(mutable2)));

  }

  private void verifyExampleMap(SymbolMap map) {

    // contains
    assertTrue(map.contains($a));
    assertTrue(map.contains($b));
    assertFalse(map.contains($c));

    // get hit
    assertThat(map.get($a), equalTo(a));
    assertThat(map.get($b), equalTo(b));

    // get miss
    try {
      map.get($c);
      throw new RuntimeException("expecting MapDoesNotContainSymbol");
    } catch (NotContained e) {
    }

    // symbols
    assertThat(map.symbols(), equalTo(ImmutableSet.<Symbol<?>>of($a, $b)));

    // transitions
    assertThat(map.fluid().solid(), equalTo(map.fluid().solid()));

    // innerMap
    assertThat(map.immutableStateCopy(), equalTo(ImmutableMap.<Symbol<?>, Object>of($a, a, $b, b)));
  }

  // ===========================================================================

  @Test
  public void testSymbolMapCanContainNull() {

    SymbolMap.Fluid map = map().put($a, null);

    verifyMapWithNull(map);

    verifyMapWithNull(map.solid());

  }

  private void verifyMapWithNull(SymbolMap map) {

    map.symbols();

    map.stateCopy();

    // transitions

    map.fluid();

    map.fluid().solid();

    // TODO fill this out better
  }

}
