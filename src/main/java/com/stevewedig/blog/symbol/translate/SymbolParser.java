package com.stevewedig.blog.symbol.translate;

import java.util.Map;

import com.stevewedig.blog.symbol.Symbol;
import com.stevewedig.blog.symbol.SymbolMap;
import com.stevewedig.blog.translate.FormatParser;
import com.stevewedig.blog.translate.Parser;

public interface SymbolParser extends Parser<Map<String, String>, SymbolMap> {

  <Value> Value parse(Symbol<Value> symbol, String valueStr);

  interface Builder {

    SymbolParser build();

    <Value> Builder add(Symbol<Value> symbol, FormatParser<Value> parser);

    Builder add(Symbol<String> symbol);
  }

}
