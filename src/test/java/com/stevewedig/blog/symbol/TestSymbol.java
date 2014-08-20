package com.stevewedig.blog.symbol;

import static com.stevewedig.blog.symbol.SymbolLib.symbol;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;

public class TestSymbol {

  @Test
  public void testSymbol__name() {

    // explicit name
    Symbol<String> $a = symbol("a");
    assertEquals("a", $a.name());

    // generating name from type object
    Symbol<Integer> $int = symbol(Integer.class);
    assertEquals("java.lang.Integer", $int.name());

    // name() and getName() are synonyms
    assertEquals($a.getName(), $a.name());
  }

  @Test
  public void testSymbol__valueObject() {

    // equal with same name
    Symbol<String> $a1 = symbol("a");
    Symbol<String> $a2 = symbol("a");
    assertEquals($a1, $a2);

    // unequal with different name
    Symbol<String> $b = symbol("b");
    assertNotEquals($a1, $b);
  }

  @Test
  public void testSymbol__differentNamesAndSameValueTypes() {

    // different symbols with the same Value type
    Symbol<Boolean> $a = symbol(Boolean.class);
    Symbol<Boolean> $b = symbol("b");

    assertNotEquals($a, $b);
    assertNotEquals($a.name(), $b.name());
  }

  @Test
  public void testSymbol__sameNamesAndDifferentValueTYpes() {

    // don't do this
    Symbol<Boolean> $a1 = symbol("a");
    Symbol<Integer> $a2 = symbol("a");

    // this is unfortunate
    assertEquals($a1, $a2);
  }


  @Test
  public void testSymbol__genericValueType() {

    @SuppressWarnings("unused")
    Symbol<List<Integer>> a = symbol("a");

  }
}
