package com.stevewedig.blog.util;

import java.util.Map.Entry;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

/**
 * Multimap related utilities.
 */
public abstract class MultimapLib {

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
   * Filtering the entries in a multimap containing keys and values of the same type.
   * 
   * @param key__values The multimap.
   * @param keep The predicate for filtering the keys and values.
   * @return A filtered multimap.
   */
  public static <Item> SetMultimap<Item, Item> filterKeysAndValues(
      SetMultimap<Item, Item> key__values, final Predicate<Item> predicate) {

    return Multimaps.filterEntries(key__values, new Predicate<Entry<Item, Item>>() {
      @Override
      public boolean apply(Entry<Item, Item> entry) {

        if (!predicate.apply(entry.getKey()))
          return false;

        if (!predicate.apply(entry.getValue()))
          return false;

        return true;
      }
    });
  }

}
