package com.stevewedig.blog.symbol;

import static com.stevewedig.blog.symbol.SymbolLib.symbol;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;

public class TestSymbol {

  @Test
  public void testSymbol__name() {

    Symbol<String> $a = symbol("a");
    assertEquals("a", $a.name());
  }

  @Test
  public void testSymbol__entityObject() {

    // same name, but not equal
    Symbol<String> $a1 = symbol("a");
    Symbol<String> $a2 = symbol("a");

    assertNotEquals($a1, $a2);

  }

  @Test
  public void testSymbol__differentNamesAndSameValueTypes() {

    // different symbols with the same Value type
    Symbol<Boolean> $a = symbol("a");
    Symbol<Boolean> $b = symbol("b");

    assertNotEquals($a, $b);
    assertNotEquals($a.name(), $b.name());
  }

  @Test
  public void testSymbol__genericValueType() {

    @SuppressWarnings("unused")
    Symbol<List<Integer>> a = symbol("a");

  }
}
