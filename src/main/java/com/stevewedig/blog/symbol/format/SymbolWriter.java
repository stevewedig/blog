package com.stevewedig.blog.symbol.format;

import java.util.Map;

import com.stevewedig.blog.symbol.Symbol;
import com.stevewedig.blog.symbol.SymbolMap;
import com.stevewedig.blog.translate.FormatWriter;
import com.stevewedig.blog.translate.Writer;

public interface SymbolWriter extends Writer<Map<String, String>, SymbolMap> {

  <Value> String write(Symbol<Value> symbol, Value value);

  interface Builder {

    SymbolWriter build();

    <Value> Builder add(Symbol<Value> symbol, FormatWriter<Value> Writer);

    Builder add(Symbol<String> symbol);
  }

}
