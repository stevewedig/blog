package com.stevewedig.blog.symbol.translate;

import java.util.Map;

import com.stevewedig.blog.symbol.*;
import com.stevewedig.blog.translate.*;

/**
 * A Parser that converts from Map&lt;String, String&gt; to SymbolMap.
 */
public interface SymbolParser extends Parser<Map<String, String>, SymbolMap> {

  <Value> Value parse(Symbol<Value> symbol, String valueStr);

  interface Builder {

    SymbolParser build();

    <Value> Builder add(Symbol<Value> symbol, FormatParser<Value> parser);

    Builder add(Symbol<String> symbol);
  }

}
