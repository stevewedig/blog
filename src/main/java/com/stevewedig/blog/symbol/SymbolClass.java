package com.stevewedig.blog.symbol;

import com.stevewedig.blog.value_objects.EntityMixin;

class SymbolClass<Value> extends EntityMixin implements Symbol<Value> {

  // ===========================================================================
  // state
  // ===========================================================================

  protected String name;

  @Override
  public Object[] fields() {
    return array("name", name);
  }

  // ===========================================================================
  // constructors
  // ===========================================================================

  public SymbolClass() {}

  public SymbolClass(String name) {
    this.name = name;
  }

  // ===========================================================================
  // properties
  // ===========================================================================

  @Override
  public String name() {
    return name;
  }

}
