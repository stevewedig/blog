package com.stevewedig.blog.symbol;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.value_objects.HasObjectHelper;

class SymbolMapClassImmutable extends SymbolMapClassMixin implements HasObjectHelper {

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

  public SymbolMapClassImmutable() {
    super();
  }

  public SymbolMapClassImmutable(Map<Symbol<?>, Object> state) {
    super(state);
  }

  // ===========================================================================
  // implementing SymbolMap
  // ===========================================================================

  @Override
  public SymbolMap immutable() {
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
