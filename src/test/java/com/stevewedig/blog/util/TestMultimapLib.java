package com.stevewedig.blog.util;

import static com.stevewedig.blog.translate.FormatLib.parseMultimap;
import static com.stevewedig.blog.translate.FormatLib.parseSet;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestMultimapLib {

  @Test
  public void testKeysAndValues() {

    assertEquals(parseSet(""), MultimapLib.keysAndValues(parseMultimap("")));

    assertEquals(parseSet("a, b"), MultimapLib.keysAndValues(parseMultimap("a = b")));

    assertEquals(parseSet("a, b, c, d"), MultimapLib.keysAndValues(parseMultimap("a = b, c = d")));

    assertEquals(parseSet("a, b, c"), MultimapLib.keysAndValues(parseMultimap("a = b, a = c")));

  }

  @Test
  public void testOf() {

    assertEquals(parseMultimap(""), MultimapLib.of());

    assertEquals(parseMultimap("a = b"), MultimapLib.of("a", "b"));

    assertEquals(parseMultimap("a = b, a = c"), MultimapLib.of("a", "b", "a", "c"));

    assertEquals(parseMultimap("a = b, a = c, b = c"), MultimapLib.of("a", "b", "a", "c", "b", "c"));
  }

}
