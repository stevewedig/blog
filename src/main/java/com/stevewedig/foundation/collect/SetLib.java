package com.stevewedig.foundation.collect;

import java.util.Set;

import com.google.common.collect.Sets;

public abstract class SetLib {

  public static <Item> void assertEquals(String message, Set<Item> expected, Set<Item> provided) {

    if(expected.equals(provided))
      return;
    
    Set<Item> missing = Sets.difference(expected, provided);
    
    Set<Item> unexpected = Sets.difference(provided, expected);
    
    if(! missing.isEmpty())
      message += " Missing: " + missing + ".";

    if(! unexpected.isEmpty())
      message += " Unexpected: " + unexpected + ".";
    
    throw new RuntimeException(message);
    
  }
}
