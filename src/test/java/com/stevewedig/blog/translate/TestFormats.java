package com.stevewedig.blog.translate;

import static com.stevewedig.blog.translate.FormatLib.*;

import org.junit.Test;

import com.google.common.collect.*;

public class TestFormats {

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
  
  @Test
  public void testStrCommaSet() {
    ImmutableSet<String> model = ImmutableSet.of("a", "b");
    String syntax = "a, b";
    FormatVerifyLib.verify(strCommaSetFormat, model, syntax);
  }

  @Test
  public void testIntCommaSet() {
    ImmutableSet<Integer> model = ImmutableSet.of(1, 2);
    String syntax = "1, 2";
    FormatVerifyLib.verify(intCommaSetFormat, model, syntax, "a, b");
  }
  
  // ===========================================================================
  
  @Test
  public void testStrCommaList() {
    ImmutableList<String> model = ImmutableList.of("a", "b");
    String syntax = "a, b";
    FormatVerifyLib.verify(strCommaListFormat, model, syntax);
  }
  
  @Test
  public void testIntCommaList() {
    ImmutableList<Integer> model = ImmutableList.of(1, 2);
    String syntax = "1, 2";
    FormatVerifyLib.verify(intCommaListFormat, model, syntax, "a, b");
  }

}
