package com.stevewedig.blog.util;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * Set related utilities.
 */
public abstract class SetLib {

  @SafeVarargs
  public static <Item> Set<Item> union(Set<Item>... sets) {

    Set<Item> items = new HashSet<>();

    for (Set<Item> set : sets)
      items = Sets.union(items, set);

    return items;
  }

}
