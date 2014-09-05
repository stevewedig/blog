package com.stevewedig.blog.util;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

public abstract class SetLib {

  @SafeVarargs
  public static <Item> Set<Item> union(Set<Item>... sets) {

    Set<Item> items = new HashSet<>();

    for (Set<Item> set : sets)
      items = Sets.union(items, set);

    return items;
  }

  public static <Item> Set<Item> union(Iterable<Set<Item>> sets) {
    
    Set<Item> items = new HashSet<>();
    
    for (Set<Item> set : sets)
      items = Sets.union(items, set);
    
    return items;
  }

}
