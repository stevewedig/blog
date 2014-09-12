package com.stevewedig.blog.symbol.translate;

import java.util.Map;

import com.stevewedig.blog.symbol.*;
import com.stevewedig.blog.translate.*;


/**
 * A Translator that converts between Map&lt;String, String&gt; and SymbolMap.
 */
public interface SymbolTranslator extends Translator<Map<String, String>, SymbolMap>, SymbolParser,
    SymbolWriter {

  interface Builder {

    SymbolTranslator build();

    <Value> Builder add(Symbol<Value> symbol, Format<Value> format);

    Builder add(Symbol<String> symbol);
  }

}

