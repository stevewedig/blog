package com.stevewedig.blog.util;

import java.util.*;

import com.google.common.collect.Sets;

/**
 * Set related utilities.
 */
public abstract class SetLib {

  /**
   * Return the union of a variable number of sets.
   * 
   * @param sets Variable number of sets.
   * @return The union.
   */
  @SafeVarargs
  public static <Item> Set<Item> union(Set<Item>... sets) {

    Set<Item> items = new HashSet<>();

    for (Set<Item> set : sets)
      items = Sets.union(items, set);

    return items;
  }

  /**
   * Assert that two sets are equal, identifying missing and unexpected items if they are not.
   * 
   * @param expected The expected items.
   * @param provided The provided items.
   * @throws AssertionError
   */
  public static <Item> void assertEquals(Set<Item> expected, Set<Item> provided)
      throws AssertionError {

    if (expected.equals(provided))
      return;

    Set<Item> missing = Sets.difference(expected, provided);

    Set<Item> unexpected = Sets.difference(provided, expected);

    String message = "Sets were not equal.";

    if (!missing.isEmpty())
      message += " Missing items: " + missing + ".";

    if (!unexpected.isEmpty())
      message += " Unexpected items: " + unexpected + ".";

    throw new AssertionError(message);

  }
}
