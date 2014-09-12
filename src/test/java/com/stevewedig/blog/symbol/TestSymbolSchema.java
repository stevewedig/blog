package com.stevewedig.blog.symbol;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.errors.*;

public class TestSymbolSchema {

  static Symbol<Integer> $a = SymbolLib.symbol("a");
  static Symbol<Boolean> $b = SymbolLib.symbol("b");
  static Symbol<Boolean> $c = SymbolLib.symbol("c");
  static Symbol<Float> d = SymbolLib.symbol("d");

  @Test
  public void testSymbolSchema() {

    SymbolSchema schema = SymbolLib.schema($a, $b).withOptional($c);

    // =================================
    // symbols
    // =================================

    assertEquals(schema.symbols(), ImmutableSet.<Symbol<?>>of($a, $b, $c));

    assertEquals(schema.requiredSymbols(), ImmutableSet.<Symbol<?>>of($a, $b));

    assertEquals(schema.optionalSymbols(), ImmutableSet.<Symbol<?>>of($c));

    // =================================
    // validate ok
    // =================================

    schema.validate($a, $b, $c); // with opt c
    schema.validate($a, $b); // without opt c

    // =================================
    // validate missing symbol
    // =================================

    try {
      schema.validate($b, $c);
      throw new NotThrown(InvalidSymbols.class);
    } catch (InvalidSymbols e) {
    }

    // =================================
    // validate unexpected symbol
    // =================================

    try {
      schema.validate($a, $b, $c, d);
      throw new NotThrown(InvalidSymbols.class);
    } catch (InvalidSymbols e) {
    }

    // =================================
    // validate the symbols in a map
    // =================================
    
    schema.validate(SymbolLib.map().put($a, 1).put($b, true));

  }
  
  @Test
  public void testInvalidSymbolSchema() {

    try {
      SymbolLib.schema($a).withOptional($a);
      throw new NotThrown(Bug.class);
    }
    catch(Bug e) {
    }
  }

}
