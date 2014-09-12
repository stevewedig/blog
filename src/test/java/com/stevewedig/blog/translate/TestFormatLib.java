package com.stevewedig.blog.translate;

import static com.stevewedig.blog.translate.FormatLib.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.*;

public class TestFormatLib {

  // ===========================================================================
  // basic formats
  // ===========================================================================

  @Test
  public void testStrFormat() {
    FormatVerifyLib.verify(strFormat, "a", "a"); // no op
  }

  @Test
  public void testIntFormat() {
    FormatVerifyLib.verify(intFormat, 1, "1", "1.1");
  }

  @Test
  public void testFloatFormat() {
    FormatVerifyLib.verify(floatFormat, 1.1f, "1.1", "a");
  }

  @Test
  public void testDoubleFormat() {
    FormatVerifyLib.verify(doubleFormat, 1.1d, "1.1", "a");
  }

  // ===========================================================================
  // bool formats
  // ===========================================================================

  @Test
  public void testBoolJson() {
    FormatVerifyLib.verify(boolJsonFormat, true, "true", "1");
    FormatVerifyLib.verify(boolJsonFormat, false, "false", "0");
  }

  @Test
  public void testBoolFlag() {
    FormatVerifyLib.verify(boolFlagFormat, true, "1", "true");
    FormatVerifyLib.verify(boolFlagFormat, false, "0", "false");
  }

  // ===========================================================================
  // set format
  // ===========================================================================

  @Test
  public void testStrCommaSet() {
    ImmutableSet<String> model = ImmutableSet.of("a", "b");
    String syntax = "a, b";
    FormatVerifyLib.verify(strCommaSetFormat, model, syntax);

    // empty
    FormatVerifyLib.verify(strCommaSetFormat, ImmutableSet.<String>of(), "");
  }

  @Test
  public void testIntCommaSet() {
    ImmutableSet<Integer> model = ImmutableSet.of(1, 2);
    String syntax = "1, 2";
    FormatVerifyLib.verify(intCommaSetFormat, model, syntax, "a, b");
  }

  // ===========================================================================
  // list format
  // ===========================================================================

  @Test
  public void testStrCommaList() {
    ImmutableList<String> model = ImmutableList.of("a", "b");
    String syntax = "a, b";
    FormatVerifyLib.verify(strCommaListFormat, model, syntax);

    // empty
    FormatVerifyLib.verify(strCommaListFormat, ImmutableList.<String>of(), "");
  }

  @Test
  public void testIntCommaList() {
    ImmutableList<Integer> model = ImmutableList.of(1, 2);
    String syntax = "1, 2";
    FormatVerifyLib.verify(intCommaListFormat, model, syntax, "a, b");
  }

  // ===========================================================================
  // multimap format
  // ===========================================================================

  @Test
  public void testStrMultimap() {
    ImmutableSetMultimap<String, String> model = ImmutableSetMultimap.of("a", "b");
    String syntax = "a = b";
    FormatVerifyLib.verify(strMultimapFormat, model, syntax);

    // empty
    FormatVerifyLib.verify(strMultimapFormat, ImmutableSetMultimap.<String, String>of(), "");
    
    // parse more than one entry (string is non-deterministic)
    assertEquals(ImmutableSetMultimap.of("a", "b", "a", "c", "b", "d"), strMultimapFormat.parse("a = b, a = c, b = d"));
  }

  // ===========================================================================
  // chain
  // ===========================================================================

  @Test
  public void testChain() {

    Translator<Float, Integer> rounder = new Translator<Float, Integer>() {
      @Override
      public Integer parse(Float syntax) throws ParseError {

        if (Math.ceil(syntax) != Math.floor(syntax))
          throw new ParseError();

        return Math.round(syntax);
      }

      @Override
      public Float write(Integer model) {
        return (float) model;
      }
    };

    Format<Integer> chained = FormatLib.chain(FormatLib.floatFormat, rounder);

    FormatVerifyLib.verify(chained, 1, "1.0", "1.1");
  }

}
