package com.stevewedig.blog.symbol;

import static com.stevewedig.blog.util.StrLib.format;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.stevewedig.blog.errors.NotContained;
import com.stevewedig.blog.util.CastLib;
import com.stevewedig.blog.value_objects.ObjectMixin;

abstract class SymbolMapMixin extends ObjectMixin implements SymbolMap {

  // ===========================================================================
  // state
  // ===========================================================================

  private final Map<Symbol<?>, Object> state;

  @Override
  protected Object[] fields() {

    Object[] fields = new Object[state.size() * 2];

    int i = 0;
    for (Symbol<?> symbol : SymbolLib.sortSymbols(symbols())) {
      Object value = state().get(symbol);

      fields[i] = symbol.name();
      fields[i + 1] = value;

      i += 2;
    }

    return fields;
  }

  // ===========================================================================
  // constructor
  // ===========================================================================

  public SymbolMapMixin() {
    this.state = new HashMap<>();
  }

  public SymbolMapMixin(Map<Symbol<?>, Object> state) {
    this.state = Maps.newHashMap(state); // defensive copy
  }

  // ===========================================================================
  // state
  // ===========================================================================

  protected Map<Symbol<?>, Object> state() {
    return state;
  }

  @Override
  public Map<Symbol<?>, Object> stateCopy() {
    return Maps.newHashMap(state());
  }

  // ===========================================================================
  // get
  // ===========================================================================

  @Override
  public <Value> Value get(Symbol<Value> symbol) {

    if (!contains(symbol))
      throw new NotContained(format("SymbolMap does not contain symbol: %s", symbol));

    // this is type safe as long as items were added via SymbolMap.put()
    return CastLib.get(state(), symbol);
  }

  // ===================================

  @Override
  public <Value> Value getNullable(Symbol<Value> symbol) {
    if (contains(symbol))
      return get(symbol);
    else
      return null;
  }

  // ===================================

  @Override
  public <Value> Optional<Value> getOptional(Symbol<Value> symbol) {
    return getOptional(symbol, true);
  }

  @Override
  public <Value> Optional<Value> getOptional(Symbol<Value> symbol, boolean adaptNull) {
    if (!contains(symbol))
      return Optional.absent();

    Value value = get(symbol);

    if (value == null)
      if (adaptNull)
        return Optional.absent();
      else
        throw new NullPointerException(format("SymbolMap found null for symbol %s", symbol));

    return Optional.of(value);
  }

  // ===================================

  @Override
  public <Value> Value getDefault(Symbol<Value> symbol, Value defaultValue) {
    return getDefault(symbol, defaultValue, true);
  }

  @Override
  public <Value> Value getDefault(Symbol<Value> symbol, Value defaultValue, boolean adaptNull) {

    if (!contains(symbol))
      return defaultValue;

    Value value = get(symbol);

    if (value == null && adaptNull)
      return defaultValue;

    return value;
  }

  // ===========================================================================
  // other behavior
  // ===========================================================================

  @Override
  public boolean contains(Symbol<?> symbol) {
    return state().containsKey(symbol);
  }

  @Override
  public Fluid fluid() {
    return SymbolLib.map(state());
  }

  // ===========================================================================
  // implementing entry iterable
  // ===========================================================================

  @Override
  public Iterator<Entry<Symbol<?>, Object>> iterator() {
    return state().entrySet().iterator();
  }

}
