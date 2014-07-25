package com.stevewedig.blog.group.query;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.stevewedig.blog.util.StrLib;

public abstract class SetLib {

  public static <Item> void assertNoOverlap(String message, Set<Item> a, Set<Item> b) {

    Set<Item> overlap = Sets.intersection(a, b);

    if (overlap.isEmpty())
      return;

    message += StrLib.format("Overlapping Items: %s", overlap);

    throw new RuntimeException(message);
  }

  public static <Item> void assertEquals(String message, Set<Item> expected, Set<Item> provided) {

    if (expected.equals(provided))
      return;

    Set<Item> missing = Sets.difference(expected, provided);

    Set<Item> unexpected = Sets.difference(provided, expected);

    if (!missing.isEmpty())
      message += " Missing Items: " + missing + ".";

    if (!unexpected.isEmpty())
      message += " Unexpected Items: " + unexpected + ".";

    throw new RuntimeException(message);

  }

  public static <Item> List<Entry<Item>> multiset__entryListDescCount(Multiset<Item> itemSet) {

    List<Entry<Item>> itemList = Lists.newArrayList(itemSet.entrySet());

    Collections.sort(itemList, new Comparator<Entry<Item>>() {
      @Override
      public int compare(Entry<Item> a, Entry<Item> b) {
        return Ints.compare(b.getCount(), a.getCount());
      }
    });
    
    return itemList;
  }
}
