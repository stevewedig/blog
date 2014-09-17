package com.stevewedig.blog.util;

import com.google.common.collect.*;

/**
 * Multimap related utilities.
 */
public abstract class MultimapLib {

  /**
   * Collecting all of the keys and values in a multimap with keys and values of the same type.
   * 
   * @param key__values The multimap.
   * @return The set of keys and values.
   */
  public static <Item> ImmutableSet<Item> keysAndValues(Multimap<Item, Item> key__values) {

    ImmutableSet.Builder<Item> items = ImmutableSet.builder();

    items.addAll(key__values.keySet());

    items.addAll(key__values.values());

    return items.build();
  }

  /**
   * Creating a multimap with keys and values of the same type.
   * 
   * @param entries Alternating keys and values.
   * @return The multimap.
   */
  @SafeVarargs
  public static <Item> ImmutableSetMultimap<Item, Item> of(Item... entries) {

    CollectLib.assertSizeIsEven(entries);

    ImmutableSetMultimap.Builder<Item, Item> key__values = ImmutableSetMultimap.builder();

    for (int i = 0; i < entries.length; i += 2) {

      Item key = entries[i];
      Item value = entries[i + 1];

      key__values.put(key, value);
    }

    return key__values.build();
  }

}
