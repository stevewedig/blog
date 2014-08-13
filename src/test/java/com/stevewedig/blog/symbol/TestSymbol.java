package com.stevewedig.blog.symbol;

import static com.stevewedig.blog.symbol.SymbolLib.symbol;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Test;

public class TestSymbol {

  @Test
  public void testSymbol__name() {

    // The type parameter is the whole point of symbols, so you would never use them like this.

    assertThat(symbol("a").name(), equalTo("a"));

    assertThat(symbol(Integer.class).name(), equalTo("java.lang.Integer"));
  }

  @Test
  public void testSymbol__beanVsPropertySyntax() {

    Symbol<Integer> symbol = symbol("a");

    assertEquals(symbol.getName(), symbol.name());
  }

  @Test
  public void testSymbol__value() {

    // The type parameter is the whole point of symbols, so you would never use them like this.

    assertThat(symbol(Integer.class), equalTo(symbol(Integer.class)));

    assertThat(symbol("a"), equalTo(symbol("a")));

    assertThat(symbol("a"), not(equalTo(symbol("b"))));
  }

  @Test
  public void testSymbol__sameTypesDifferentNames() {

    // This allows you to have multiple symbols with the same type. This isn't possible if types are
    // used directly.

    Symbol<Boolean> a = symbol(Boolean.class);
    Symbol<Boolean> b = symbol("b");

    assertThat(a, not(equalTo(b)));
    assertThat(a.name(), not(equalTo(b.name())));
  }

  @Test
  public void testSymbol__differentTypesSameNames() {

    // Java generics use erasure, so the type parameters are not available at runtime. This means
    // using these symbols in the same context would be a bug that would ruin type safety.

    Symbol<Boolean> s1 = symbol("s");
    Symbol<Integer> s2 = symbol("s");

    // this is unfortunate
    assertEquals(s1, s2);
  }


  @Test
  public void testSymbol__genericType() {

    // This allows you to have symbols for generic types. This isn't possible if types are used
    // directly, because you can't say Set<Boolean>.class

    Symbol<Set<Boolean>> a = symbol("a");

  }
}
