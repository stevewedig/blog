package com.stevewedig.foundation.collect;

import java.util.Collection;

public abstract class CollectLib {
  
  public static <X> X onlyItem(Collection<X> items) {
    if (items.size() != 1)
      throw new RuntimeException("onlyItem called when collection size != 1");
    return anyItem(items);
  }

  
  public static <X> X anyItem(Collection<X> items) {
    if(items.isEmpty())
      throw new RuntimeException("anyItem called on empty collection");
    
    return items.iterator().next();
  }
  
}
