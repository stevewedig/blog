package com.stevewedig.blog.util;

import static com.stevewedig.blog.util.PropLib.format;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class TestPropLib {

  @Test
  public void testSymbol__key() {

    ImmutableMap<String, String> map = ImmutableMap.of("a", "b", "c", "d");

    String content = "a = b\nc = d";

    assertEquals(map, format.parse(content));

    // writing is non-deterministic, so just test it by parsing it back out
    assertEquals(map, format.parse(format.write(map)));

  }

}
