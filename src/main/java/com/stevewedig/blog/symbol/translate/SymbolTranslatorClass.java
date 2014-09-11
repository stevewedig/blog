package com.stevewedig.blog.symbol.translate;

import java.util.*;
import java.util.Map.Entry;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.stevewedig.blog.errors.*;
import com.stevewedig.blog.symbol.*;
import com.stevewedig.blog.translate.*;
import com.stevewedig.blog.value_objects.ValueMixin;

class SymbolTranslatorClass extends ValueMixin implements SymbolTranslator {

  // ===========================================================================
  // state
  // ===========================================================================

  private final Optional<ImmutableMap<Symbol<?>, FormatParser<?>>> symbol__parser;
  private final Optional<ImmutableMap<Symbol<?>, FormatWriter<?>>> symbol__writer;

  @Override
  protected Object[] fields() {
    return array("symbol__parser", symbol__parser, "symbol__writer", symbol__writer);
  }

  // ===========================================================================
  // constructor
  // ===========================================================================

  public SymbolTranslatorClass(ImmutableMap<Symbol<?>, FormatParser<?>> symbol__parser,
      ImmutableMap<Symbol<?>, FormatWriter<?>> symbol__writer) {

    this.symbol__parser = Optional.fromNullable(symbol__parser);
    this.symbol__writer = Optional.fromNullable(symbol__writer);

    initNameMap();
  }

  private void initNameMap() {

    ImmutableMap.Builder<String, Symbol<?>> name__symbol = ImmutableMap.builder();

    if (symbol__parser.isPresent())
      for (Symbol<?> symbol : symbol__parser.get().keySet())
        name__symbol.put(symbol.name(), symbol);

    else if (symbol__writer.isPresent())
      for (Symbol<?> symbol : symbol__writer.get().keySet())
        name__symbol.put(symbol.name(), symbol);

    else
      throw new Bug("translator needs writers or parsers");

    this.name__symbol = name__symbol.build();
  }

  private ImmutableMap<String, Symbol<?>> name__symbol;

  // ===========================================================================
  // checking whether we can parse/write
  // ===========================================================================

  private ImmutableMap<Symbol<?>, FormatParser<?>> symbol__parser() {

    if (!symbol__parser.isPresent())
      throw new NotImplemented("Cannot do parsing on a SymbolWriter");

    return symbol__parser.get();
  }

  private ImmutableMap<Symbol<?>, FormatWriter<?>> symbol__writer() {

    if (!symbol__writer.isPresent())
      throw new NotImplemented("Cannot do parsing on a SymbolParser");

    return symbol__writer.get();
  }


  // ===========================================================================
  // parse 1
  // ===========================================================================

  @Override
  public <Value> Value parse(Symbol<Value> symbol, String valueStr) {

    if (!symbol__parser().containsKey(symbol))
      throw new NotContained("unexpected symbol key: \"%s\", possible keys: %s", symbol.name(),
          symbol__parser().keySet());

    // the type safety of this is enforced by the builders
    @SuppressWarnings("unchecked")
    FormatParser<Value> parser = (FormatParser<Value>) symbol__parser().get(symbol);

    Value value = parser.parse(valueStr);

    return value;
  }

  // ===========================================================================
  // write 1
  // ===========================================================================

  @Override
  public <Value> String write(Symbol<Value> symbol, Value value) {

    if (!symbol__writer().containsKey(symbol))
      throw new NotContained("unexpected symbol key: %s", symbol.name());

    // the type safety of this is enforced by the builders
    @SuppressWarnings("unchecked")
    FormatWriter<Value> writer = (FormatWriter<Value>) symbol__writer().get(symbol);

    String valueStr = writer.write(value);

    return valueStr;

  }

  // ===========================================================================
  // parse n
  // ===========================================================================

  @Override
  public SymbolMap parse(Map<String, String> strMap) {

    SymbolMap.Fluid symbolMap = SymbolLib.map();

    for (Entry<String, String> entry : strMap.entrySet())
      parseEntry(symbolMap, entry.getKey(), entry.getValue());

    return symbolMap.solid();
  }

  private void parseEntry(SymbolMap.Fluid symbolMap, String keyStr, String valueStr) {

    /*
     * We upcast the symbol's Value param to Object, and we upcast the value to Object. This
     * effectively turns off static type checking for agreement between symbol and value, enabling
     * us to sneak past symbolMap.put(). This is ok to do at parse time because type safety is
     * guarnatee at parser creation time by the signature of SymbolParser.Builder.add()
     */

    // symbols are value objects so we can conveniently create a new one here
    // http://stevewedig.com/2014/07/31/value-objects-in-java-and-python/

    if (!name__symbol.containsKey(keyStr))
      throw new NotContained("Symbol with name: %s", keyStr);

    @SuppressWarnings("unchecked")
    Symbol<Object> symbol = (Symbol<Object>) name__symbol.get(keyStr);

    Object value = parse(symbol, valueStr);

    symbolMap.put(symbol, value);
  }

  // ===========================================================================
  // write n
  // ===========================================================================

  @Override
  public Map<String, String> write(SymbolMap symbolMap) {

    ImmutableMap.Builder<String, String> strMap = ImmutableMap.builder();

    for (Entry<Symbol<?>, Object> entry : symbolMap)
      writeEntry(strMap, entry.getKey(), entry.getValue());

    return strMap.build();
  }

  private void writeEntry(ImmutableMap.Builder<String, String> strMap, Symbol<?> symbol,
      Object value) {

    @SuppressWarnings("unchecked")
    Symbol<Object> upcasted = (Symbol<Object>) symbol;

    String valueStr = write(upcasted, value);

    strMap.put(symbol.name(), valueStr);
  }

  // ===========================================================================
  // TranslatorBuilder
  // ===========================================================================

  static class TranslatorBuilder implements SymbolTranslator.Builder {

    private final ImmutableMap.Builder<Symbol<?>, FormatParser<?>> symbol__parser = ImmutableMap
        .builder();
    private final ImmutableMap.Builder<Symbol<?>, FormatWriter<?>> symbol__writer = ImmutableMap
        .builder();

    @Override
    public SymbolTranslator build() {
      return new SymbolTranslatorClass(symbol__parser.build(), symbol__writer.build());
    }

    @Override
    public <Value> SymbolTranslator.Builder add(Symbol<Value> symbol, Format<Value> format) {
      symbol__parser.put(symbol, format);
      symbol__writer.put(symbol, format);
      return this;
    }

    @Override
    public SymbolTranslator.Builder add(Symbol<String> symbol) {
      return add(symbol, FormatLib.strFormat); // strFormat is a passthrough
    }

  }

  // ===========================================================================
  // ParserBuilder
  // ===========================================================================

  static class ParserBuilder implements SymbolParser.Builder {

    private final ImmutableMap.Builder<Symbol<?>, FormatParser<?>> symbol__parser = ImmutableMap
        .builder();

    @Override
    public SymbolParser build() {
      return new SymbolTranslatorClass(symbol__parser.build(), null);
    }

    @Override
    public <Value> SymbolParser.Builder add(Symbol<Value> symbol, FormatParser<Value> parser) {
      symbol__parser.put(symbol, parser);
      return this;
    }

    @Override
    public SymbolParser.Builder add(Symbol<String> symbol) {
      return add(symbol, FormatLib.strFormat); // strFormat is a passthrough
    }

  }

  // ===========================================================================
  // WriterBuilder
  // ===========================================================================

  static class WriterBuilder implements SymbolWriter.Builder {

    private final ImmutableMap.Builder<Symbol<?>, FormatWriter<?>> symbol__writer = ImmutableMap
        .builder();

    @Override
    public SymbolWriter build() {
      return new SymbolTranslatorClass(null, symbol__writer.build());
    }

    @Override
    public <Value> SymbolWriter.Builder add(Symbol<Value> symbol, FormatWriter<Value> writer) {
      symbol__writer.put(symbol, writer);
      return this;
    }

    @Override
    public SymbolWriter.Builder add(Symbol<String> symbol) {
      return add(symbol, FormatLib.strFormat); // strFormat is a passthrough
    }

  }
}
