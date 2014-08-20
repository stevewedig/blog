package com.stevewedig.blog.symbol;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

class SymbolMapSolidClass extends SymbolMapMixin implements SymbolMap.Solid {

  // ===========================================================================
  // copied from ValueMixin
  // ===========================================================================

  @Override
  protected boolean isEntity() {
    return false;
  }

  @Override
  public boolean equals(Object other) {
    return objectHelper().classAndStateEquals(other);
  }

  @Override
  public int hashCode() {
    return objectHelper().classAndStateHash();
  }

  // ===========================================================================
  // constructor
  // ===========================================================================

  public SymbolMapSolidClass() {
    super();
  }

  public SymbolMapSolidClass(Map<Symbol<?>, Object> state) {
    super(state);
  }

  // ===========================================================================
  // implementing SymbolMap
  // ===========================================================================

  @Override
  public SymbolMap.Solid solid() {
    return this;
  }

  // ===================================
  
  @Override
  public ImmutableSet<Symbol<?>> symbols() {
    if (symbols == null)
      symbols = ImmutableSet.copyOf(state().keySet());
    return symbols;
  }

  private ImmutableSet<Symbol<?>> symbols = null;

  // ===================================

  @Override
  public ImmutableMap<Symbol<?>, Object> immutableStateCopy() {
    if (immutableState == null)
      immutableState = ImmutableMap.copyOf(state());
    return immutableState;
  }

  private ImmutableMap<Symbol<?>, Object> immutableState;


}
