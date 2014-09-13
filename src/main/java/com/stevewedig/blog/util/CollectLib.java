package com.stevewedig.blog.util;

import java.util.Collection;

import com.stevewedig.blog.errors.Bug;

public abstract class CollectLib {

  // ===========================================================================
  // is size even?
  // ===========================================================================

  public static <Item> boolean isSizeEven(Item[] items) {
    return NumLib.isEven(items.length);
  }

  public static <Item> boolean isSizeEven(Collection<Item> items) {
    return NumLib.isEven(items.size());
  }

  public static <Item> void assertSizeIsEven(Item[] items) {
    if (!isSizeEven(items))
      throw new Bug("array size was not even, got %s items", items.length);
  }

  public static <Item> void assertSizeIsEven(Collection<Item> items) {
    if (!isSizeEven(items))
      throw new Bug("collection size was not even, got %s items", items.size());
  }

  // ===========================================================================
  // is empty?
  // ===========================================================================

  public static <Item> void assertNotEmpty(Item[] items) {
    if (items.length == 0)
      throw new Bug("array was empty");
  }

  public static <Item> void assertNotEmpty(Collection<Item> items) {
    if (items.isEmpty())
      throw new Bug("collection was empty");
  }

}
