package com.stevewedig.blog.symbol;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

class SymbolMapClassMutable extends SymbolMapClassMixin implements SymbolMap.Mutable {

  // ===========================================================================
  // copied from EntityMixin
  // ===========================================================================

  @Override
  protected boolean isEntity() {
    return true;
  }

  // ===========================================================================
  // constructor
  // ===========================================================================

  public SymbolMapClassMutable() {
    super();
  }

  public SymbolMapClassMutable(Map<Symbol<?>, Object> state) {
    super(state);
  }

  // ===========================================================================
  // implementing SymbolMap
  // ===========================================================================

  @Override
  public ImmutableSet<Symbol<?>> symbols() {
    return ImmutableSet.copyOf(state().keySet());
  }

  @Override
  public ImmutableMap<Symbol<?>, Object> immutableStateCopy() {
    return ImmutableMap.copyOf(state());
  }

  // ===========================================================================
  // implementing SymbolMap.Mutable
  // ===========================================================================

  @Override
  public SymbolMap immutable() {
    return SymbolLib.immutableMap(state());
  }

  @Override
  public <Value> Mutable put(Symbol<Value> symbol, Value value) {

    state().put(symbol, value);

    return this;
  }

  // TODO untested
  @Override
  public Mutable putAll(SymbolMap delta) {
    
    for (Entry<Symbol<?>, Object> entry : delta) {
      Symbol<?> symbol = entry.getKey();
      Object value = entry.getValue();

      state().put(symbol, value);
    }
    
    return this;
  }

}
