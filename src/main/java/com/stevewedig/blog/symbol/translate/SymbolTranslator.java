package com.stevewedig.blog.symbol.translate;

import java.util.Map;

import com.stevewedig.blog.symbol.*;
import com.stevewedig.blog.translate.*;


/**
 * A Translator that converts between Map&lt;String, String&gt; and SymbolMap.
 */
public interface SymbolTranslator extends Translator<Map<String, String>, SymbolMap>, SymbolParser,
    SymbolWriter {

  /**
   * Builder for creating SymbolTranslators.
   */
  interface Builder {

    /**
     * Create the SymbolTranslator.
     */
    SymbolTranslator build();

    /**
     * Add a symbol and associated format.
     */
    <Value> Builder add(Symbol<Value> symbol, Format<Value> format);

    /**
     * Add a String symbol and the default no-op format.
     */
    Builder add(Symbol<String> symbol);
  }

}

