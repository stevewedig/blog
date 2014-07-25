package com.stevewedig.blog.group.query;

import static com.stevewedig.blog.util.StrLib.format;

import java.util.Collection;
import java.util.Iterator;

import com.google.common.base.Preconditions;

// TODO look at LINQ and Guava Iterables and itertools for things we need
public abstract class CollectLib {

  public static class CardinalityError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CardinalityError(String message) {
      super(message);
    }
  }

  // TODO replace with guave getOnlyElement?
  public static <Item> Item onlyItem(Collection<Item> items) {
    if (items.size() != 1)
      throw new CardinalityError(format("onlyItem called when collection size != 1, items = %s",
          items));
    return anyItem(items);
  }


  public static <Item> Item anyItem(Iterable<Item> items) {

    Iterator<Item> iterator = items.iterator();
    if (!iterator.hasNext())
      throw new CardinalityError("anyItem called on empty collection");

    return iterator.next();
  }

  public static <Item> Item removeAnyItem(Collection<Item> items) {
    if (items.isEmpty())
      throw new CardinalityError("anyItem called on empty collection");

    Item item = items.iterator().next();

    items.remove(item);

    return item;
  }

  // https://code.google.com/p/guava-libraries/issues/detail?id=796
  public static <T> Iterable<T> loop(final Iterator<T> source) {
    return new Iterable<T>() {
      // private AtomicBoolean exhausted = new AtomicBoolean();
      private Boolean exhausted = false;

      @Override
      public Iterator<T> iterator() {
        // Preconditions.checkState(!exhausted.getAndSet(true));
        Preconditions.checkState(!exhausted);
        exhausted = true;
        return source;
      }
    };
  }

}
