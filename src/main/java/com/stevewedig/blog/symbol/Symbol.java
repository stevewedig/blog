package com.stevewedig.blog.symbol;

/**
 * A key with a Value parameter, for use in type safe heterogeneous containers.
 */
public interface Symbol<Value> {
  
  public String name();

}
