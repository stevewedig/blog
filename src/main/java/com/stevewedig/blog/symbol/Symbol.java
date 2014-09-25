package com.stevewedig.blog.symbol;

/**
 * A key with a Value parameter, for use in type safe heterogeneous containers.
 */
public interface Symbol<Value> {

  /**
   * The name of the symbol, which isn't used but is necessary to make key errors debuggable.
   */
  public String name();

}
