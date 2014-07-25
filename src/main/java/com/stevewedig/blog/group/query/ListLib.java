package com.stevewedig.blog.group.query;

import java.util.List;

public abstract class ListLib {

  public static <Item> void limitInPlace(List<Item> items, int sizeLimit) {

    while (items.size() > sizeLimit)
      items.remove(items.size() - 1);

  }

}
