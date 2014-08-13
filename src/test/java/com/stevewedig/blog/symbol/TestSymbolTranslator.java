package com.stevewedig.blog.symbol;

import static com.stevewedig.blog.symbol.SymbolLib.map;
import static com.stevewedig.blog.symbol.SymbolLib.symbol;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.stevewedig.blog.errors.NotContained;
import com.stevewedig.blog.symbol.format.SymbolFormatLib;
import com.stevewedig.blog.symbol.format.SymbolParser;
import com.stevewedig.blog.symbol.format.SymbolTranslator;
import com.stevewedig.blog.symbol.format.SymbolWriter;
import com.stevewedig.blog.translate.FormatLib;
import com.stevewedig.blog.translate.ParseError;

public class TestSymbolTranslator {

  private static Symbol<Object> $missing = symbol("missing");

  private static Symbol<Integer> $age = symbol("age");
  private static Symbol<String> $name = symbol("name");

  private static ImmutableMap<String, String> strMap = ImmutableMap.of("name", "bob", "age", "9");

  private static SymbolMap symbolMap = map().put($name, "bob").put($age, 9).immutable();

  // TODO include enum, bool, float
  
  // ===========================================================================
  // tests
  // ===========================================================================

  @Test
  public void testSymbolParser() {

    SymbolParser parser = SymbolFormatLib.parser().add($age, FormatLib.intFormat).add($name).build();

    verifyParser(parser);
  }

  @Test
  public void testSymbolWriter() {

    SymbolWriter writer = SymbolFormatLib.writer().add($age, FormatLib.intFormat).add($name).build();

    verifyWriter(writer);
  }

  @Test
  public void testSymbolTranslator() {

    SymbolTranslator translator =
        SymbolFormatLib.translator().add($age, FormatLib.intFormat).add($name).build();

    verifyParser(translator);

    verifyWriter(translator);
  }

  // ===========================================================================
  // verify parser
  // ===========================================================================

  private void verifyParser(SymbolParser parser) {

    // =================================
    // parse str (passthru)
    // =================================

    assertEquals("bob", parser.parse($name, "bob"));

    // =================================
    // parse 1
    // =================================

    assertEquals((Integer) 9, parser.parse($age, "9"));

    try {
      parser.parse($age, "x");
      throw new AssertionError("expecting ParseError");
    } catch (ParseError e) {
    }

    // =================================
    // missing symbol
    // =================================

    try {
      parser.parse($missing, "...");
      throw new AssertionError("expecting NotContained");
    } catch (NotContained e) {
    }

    // =================================
    // parse n
    // =================================

    assertEquals(symbolMap, parser.parse(strMap));
  }

  // ===========================================================================
  // verify writer
  // ===========================================================================
  
  private void verifyWriter(SymbolWriter writer) {
    
    // =================================
    // write str (passthru)
    // =================================
    
    assertEquals("bob", writer.write($name, "bob"));
    
    // =================================
    // write int
    // =================================
    
    assertEquals("9", writer.write($age, 9));
        
    // =================================
    // missing symbol
    // =================================
    
    try {
      writer.write($missing, "...");
      throw new AssertionError("expecting NotContained");
    } catch (NotContained e) {
    }
    
    // =================================
    // write n
    // =================================
    
    assertEquals(strMap, writer.write(symbolMap));
  }

}
