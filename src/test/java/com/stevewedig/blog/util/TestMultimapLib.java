package com.stevewedig.blog.util;

import static com.stevewedig.blog.translate.FormatLib.*;
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

}
