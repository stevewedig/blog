package com.stevewedig.blog.symbol.translate;

import java.util.Map;

import com.stevewedig.blog.symbol.*;
import com.stevewedig.blog.translate.*;


/**
 * A Writer that converts from SymbolMap to Map&lt;String, String&gt;.
 */
public interface SymbolWriter extends Writer<Map<String, String>, SymbolMap> {

  /**
   * Writer a value associated with a symbol.
   */
  <Value> String write(Symbol<Value> symbol, Value value);

  /**
   * Builder for creating SymbolWriters.
   */
  interface Builder {

    /**
     * Create the SymbolWriter.
     */
    SymbolWriter build();

    /**
     * Add a symbol and associated writer.
     */
    <Value> Builder add(Symbol<Value> symbol, FormatWriter<Value> Writer);

    /**
     * Add a String symbol and the default no-op writer.
     */
    Builder add(Symbol<String> symbol);
  }

}
