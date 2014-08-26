package com.stevewedig.blog.translate;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.stevewedig.blog.util.LambdaLib.Fn1;

public class TestTranslatorLib {

  @Test
  public void testParserToFn() {

    Fn1<String, Integer> parse = TranslatorLib.parserFn(FormatLib.intFormat);
    
    assertEquals((Integer) 1, parse.apply("1"));
  }
  
  @Test
  public void testWriterToFn() {
    
    Fn1<Integer, String> write = TranslatorLib.writerFn(FormatLib.intFormat);
    
    assertEquals("1", write.apply(1));
  }

}
