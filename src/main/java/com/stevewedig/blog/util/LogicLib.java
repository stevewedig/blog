package com.stevewedig.blog.util;

import com.google.common.base.Predicate;


public abstract class LogicLib {


  public static boolean xor(boolean... values) {

    int count = 0;

    for (boolean item : values) {
      
      if (item)
        count += 1;
      
      if (count > 1)
        return false;
    }

    return count == 1;
  }

  @SafeVarargs
  public static <Item> boolean xor(Predicate<Item> pred, Item... items) {

    int count = 0;

    for (Item item : items) {

      boolean value = pred.apply(item);

      if (value)
        count += 1;

      if (count > 1)
        return false;
    }

    return count == 1;
  }

}
