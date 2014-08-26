package com.stevewedig.blog.symbol;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableSet;

class SymbolMapFluidClass extends SymbolMapMixin implements SymbolMap.Fluid {

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

  public SymbolMapFluidClass() {
    super();
  }

  public SymbolMapFluidClass(Map<Symbol<?>, Object> state) {
    super(state);
  }

  // ===========================================================================
  // implementing SymbolMap
  // ===========================================================================

  @Override
  public ImmutableSet<Symbol<?>> symbols() {
    return ImmutableSet.copyOf(state().keySet());
  }

  // ===========================================================================
  // implementing SymbolMap.Fluid
  // ===========================================================================

  @Override
  public SymbolMap.Solid solid() {
    return SymbolLib.solidMap(state());
  }

  @Override
  public <Value> Fluid put(Symbol<Value> symbol, Value value) {

    state().put(symbol, value);

    return this;
  }

  @Override
  public Fluid putAll(SymbolMap updates) {
    
    for (Entry<Symbol<?>, Object> entry : updates) {
      Symbol<?> symbol = entry.getKey();
      Object value = entry.getValue();

      state().put(symbol, value);
    }
    
    return this;
  }

}
