package com.stevewedig.blog.util;

import com.google.common.collect.*;

/**
 * Multimap related utilities.
 */
public abstract class MultimapLib {

  // ===========================================================================
  // ===========================================================================

  public static <Item> ImmutableSet<Item> keysAndValues(Multimap<Item, Item> key__values) {

    ImmutableSet.Builder<Item> items = ImmutableSet.builder();

    items.addAll(key__values.keySet());

    items.addAll(key__values.values());

    return items.build();
  }

  // ===========================================================================
  // ===========================================================================

  @SafeVarargs
  public static <Item> ImmutableSetMultimap<Item, Item> setMultimap(Item... entries) {

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
