package com.stevewedig.blog.digraph;

import static com.stevewedig.blog.translate.FormatLib.parseList;
import static com.stevewedig.blog.translate.FormatLib.parseMultimap;
import static com.stevewedig.blog.digraph.alg.TopsortLib.sort;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class TestDetailsTopsortLib {

  @Test
  public void testTopsort() {

    // empty
    assertEquals(parseList(""), sort(parseMultimap("")).get());

    // 2 chain
    assertEquals(parseList("a, b"), sort(parseMultimap("b = a")).get());

    // 3 chain
    assertEquals(parseList("a, b, c"), sort(parseMultimap("b = a, c = b")).get());

    // dimaond
    assertTrue(ImmutableSet.of(parseList("a, b, c, d, e"), parseList("a, c, b, d, e")).contains(
        sort(parseMultimap("b = a, c = a, d = b, d = c, e = d")).get()));

    // detatched
    @SuppressWarnings("unchecked")
    ImmutableSet<ImmutableList<String>> detached =
        ImmutableSet.of(parseList("a, b, c, d"), parseList("a, c, b, d"), parseList("a, c, d, b"),
            parseList("c, a, b, d"), parseList("c, a, d, b"), parseList("c, d, a, b"));
    assertTrue(detached.contains(sort(parseMultimap("b = a, d = c")).get()));

    // 1 cycle
    assertFalse(sort(parseMultimap("a = a")).isPresent());

    // 2 cycle
    assertFalse(sort(parseMultimap("a = b, b = a")).isPresent());

    // 3 cycle
    assertFalse(sort(parseMultimap("a = b, b = c, c = a")).isPresent());

    // 3 cycle and detatched
    assertFalse(sort(parseMultimap("a = b, b = c, c = a, d = e")).isPresent());

    // 3 cycle and other random junk
    assertFalse(sort(parseMultimap("a = b, b = c, c = a, d = e, a = a, b = e, c = e")).isPresent());

  }
}
