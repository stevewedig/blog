package com.stevewedig.blog.symbol.translate;

import java.util.Map;

import com.stevewedig.blog.symbol.*;
import com.stevewedig.blog.translate.*;

/**
 * A Parser that converts from Map&lt;String, String&gt; to SymbolMap.
 */
public interface SymbolParser extends Parser<Map<String, String>, SymbolMap> {

  /**
   * Parse a string associated with a symbol.
   */
  <Value> Value parse(Symbol<Value> symbol, String valueStr);

  /**
   * Builder for creating SymbolParsers.
   */
  interface Builder {

    /**
     * Create the SymbolParser.
     */
    SymbolParser build();

    /**
     * Add a symbol and associated parser.
     */
    <Value> Builder add(Symbol<Value> symbol, FormatParser<Value> parser);

    /**
     * Add a String symbol and the default no-op parser.
     */
    Builder add(Symbol<String> symbol);
  }

}
