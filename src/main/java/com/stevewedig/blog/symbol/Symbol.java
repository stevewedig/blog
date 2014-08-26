package com.stevewedig.blog.symbol;

import java.io.Serializable;

import com.stevewedig.blog.value_objects.ValueMixin;

/**
 * A name combined with a Value parameter, for use in type safe heterogeneous containers.
 */
public class Symbol<Value> extends ValueMixin implements Serializable {
  
  private static final long serialVersionUID = 1L;

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

  public Symbol() {}

  public Symbol(String name) {
    this.name = name;
  }

  // ===========================================================================
  // required bean syntax
  // ===========================================================================

  public String getName() {
    return name;
  }

  // ===========================================================================
  // convenient property syntax
  // ===========================================================================

  public String name() {
    return getName();
  }

}
