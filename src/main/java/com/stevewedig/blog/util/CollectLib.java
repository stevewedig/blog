package com.stevewedig.blog.util;

import java.util.Collection;

/**
 * Collection related utilities.
 */
public abstract class CollectLib {

  // ===========================================================================
  // is empty?
  // ===========================================================================

  public static <Item> void assertNotEmpty(Item[] items) {
    if (items.length == 0)
      throw new AssertionError("array was empty");
  }

  public static <Item> void assertNotEmpty(Collection<Item> items) {
    if (items.isEmpty())
      throw new AssertionError("collection was empty");
  }

  public static <Item> void assertIsEmpty(Item[] items) {
    if (items.length != 0)
      throw new AssertionError("array not empty");
  }

  public static <Item> void assertIsEmpty(Collection<Item> items) {
    if (!items.isEmpty())
      throw new AssertionError("collection not empty");
  }

  // ===========================================================================
  // is size even?
  // ===========================================================================

  public static <Item> boolean isSizeEven(Item[] items) {
    return NumLib.isEven(items.length);
  }

  public static <Item> boolean isSizeEven(Collection<Item> items) {
    return NumLib.isEven(items.size());
  }

  public static <Item> boolean isSizeOdd(Item[] items) {
    return NumLib.isOdd(items.length);
  }
  
  public static <Item> boolean isSizeOdd(Collection<Item> items) {
    return NumLib.isOdd(items.size());
  }

  public static <Item> void assertSizeIsEven(Item[] items) {
    if (!isSizeEven(items))
      throw new AssertionError("array size was not even, size was " + items.length);
  }

  public static <Item> void assertSizeIsEven(Collection<Item> items) {
    if (!isSizeEven(items))
      throw new AssertionError("collection size was not even, size was " + items.size());
  }
  
  public static <Item> void assertSizeIsOdd(Item[] items) {
    if (!isSizeOdd(items))
      throw new AssertionError("array size was not odd, size was " + items.length);
  }
  
  public static <Item> void assertSizeIsOdd(Collection<Item> items) {
    if (!isSizeOdd(items))
      throw new AssertionError("collection size was not odd, size was " + items.size());
  }

}

