package com.stevewedig.blog.digraph.alg;

import java.util.*;

import com.google.common.collect.*;
import com.stevewedig.blog.errors.*;
import com.stevewedig.blog.util.LambdaLib.Fn1;

/**
 * Generic digraph traversal.
 */
public abstract class TraverseLib {

  // ===========================================================================
  // iterable
  // ===========================================================================

  /**
   * Generic digraph traversal as an id iterable.
   * 
   * @param depthFirst Whether to traverse depth first or breadth first.
   * @param inclusive Whether to include the start ids in the traversal.
   * @param startId The initial id.
   * @param expand A function mapping an id to the next ids.
   * @return An id iterable corresponding to the traversal.
   */
  public static <Id> Iterable<Id> idIterable(final boolean depthFirst, final boolean inclusive,
      final ImmutableList<Id> startIds, final Fn1<Id, List<Id>> expand) {

    Fn1<Id, Id> lookup = new Fn1<Id, Id>() {
      @Override
      public Id apply(Id id) {
        return id;
      }
    };

    return new IterableClass<Id, Id>(depthFirst, inclusive, startIds, expand, lookup);
  }

  /**
   * Generic digraph traversal as a node iterable.
   * 
   * @param depthFirst Whether to traverse depth first or breadth first.
   * @param inclusive Whether to include the start nodes in the traversal.
   * @param startId The initial id.
   * @param expand A function mapping a node to the next ids.
   * @return A node iterable corresponding to the traversal.
   */
  public static <Id, Node> Iterable<Node> nodeIterable(boolean depthFirst, boolean inclusive,
      ImmutableList<Id> startIds, Fn1<Node, List<Id>> expand, Fn1<Id, Node> lookup) {

    return new IterableClass<Id, Node>(depthFirst, inclusive, startIds, expand, lookup);
  }

  // ===========================================================================
  // IterableClass
  // ===========================================================================

  private static class IterableClass<Id, Node> implements Iterable<Node> {

    private final boolean depthFirst;
    private final boolean inclusive;
    private final ImmutableList<Id> startIds;
    private final Fn1<Node, List<Id>> expand;
    private final Fn1<Id, Node> lookup;

    public IterableClass(boolean depthFirst, boolean inclusive, ImmutableList<Id> startIds,
        Fn1<Node, List<Id>> expand, Fn1<Id, Node> lookup) {

      this.depthFirst = depthFirst;
      this.inclusive = inclusive;
      this.startIds = startIds;
      this.expand = expand;
      this.lookup = lookup;
    }

    @Override
    public Iterator<Node> iterator() {
      return TraverseLib.nodeIterator(depthFirst, inclusive, startIds, expand, lookup);
    }

  }

  // ===========================================================================
  // iterator
  // ===========================================================================

  /**
   * Generic digraph traversal as an id iterator.
   * 
   * @param depthFirst Whether to traverse depth first or breadth first.
   * @param inclusive Whether to include the start ids in the traversal.
   * @param startId The initial id.
   * @param expand A function mapping an id to the next ids.
   * @return An id iterable corresponding to the traversal.
   */
  public static <Id> Iterator<Id> idIterator(boolean depthFirst, boolean inclusive,
      ImmutableList<Id> startIds, Fn1<Id, List<Id>> expand) {

    Fn1<Id, Id> lookup = new Fn1<Id, Id>() {
      @Override
      public Id apply(Id id) {
        return id;
      }
    };

    return new IteratorClass<Id, Id>(depthFirst, inclusive, startIds, expand, lookup);
  }

  /**
   * Generic digraph traversal as a node iterator.
   * 
   * @param depthFirst Whether to traverse depth first or breadth first.
   * @param inclusive Whether to include the start nodes in the traversal.
   * @param startId The initial id.
   * @param expand A function mapping a node to the next ids.
   * @return A node iterable corresponding to the traversal.
   */
  public static <Id, Node> Iterator<Node> nodeIterator(boolean depthFirst, boolean inclusive,
      ImmutableList<Id> startIds, Fn1<Node, List<Id>> expand, Fn1<Id, Node> lookup) {

    return new IteratorClass<Id, Node>(depthFirst, inclusive, startIds, expand, lookup);
  }

  // ===========================================================================
  // IteratorClass
  // ===========================================================================

  private static class IteratorClass<Id, Node> implements Iterator<Node> {

    // =================================
    // inputs
    // =================================

    private final boolean depthFirst;
    private final boolean inclusive;
    private final Fn1<Node, List<Id>> expand;
    private final Fn1<Id, Node> lookup;
    private final ImmutableSet<Id> startSet;

    // =================================
    // state
    // =================================

    // LinkedList has tail pointer, so should be fast as FIFO (breadth first) and LIFO (depth first)
    // http://stackoverflow.com/a/25207657
    private final LinkedList<Id> open = new LinkedList<Id>();

    // for deduplication
    private final Set<Id> closed = new HashSet<>();

    // lookahead for hasNext()
    private Node nextNode = null;

    // =================================
    // constructor
    // =================================

    public IteratorClass(boolean depthFirst, boolean inclusive, ImmutableList<Id> startIds,
        Fn1<Node, List<Id>> expand, Fn1<Id, Node> lookup) {

      this.depthFirst = depthFirst;
      this.inclusive = inclusive;
      this.expand = expand;
      this.lookup = lookup;

      startSet = ImmutableSet.copyOf(startIds);

      pushN(startIds);
    }

    // =================================
    // hasNext
    // =================================

    @Override
    public boolean hasNext() {

      findNext();

      return nextNode != null;
    }

    // =================================
    // next
    // =================================

    @Override
    public Node next() {

      if (!hasNext())
        throw new NoSuchElementException();

      Node node = nextNode;

      nextNode = null;

      return node;
    }

    // =================================
    // findNext
    // =================================

    private void findNext() {

      while (nextNode == null && !open.isEmpty()) {

        Id id = open.removeFirst();

        Node node = lookup.apply(id);

        expand(node);

        if (!inclusive && startSet.contains(id))
          continue;

        nextNode = node;
      }
    }

    // =================================
    // expand
    // =================================

    private void expand(Node node) {

      List<Id> expanded = expand.apply(node);

      pushN(expanded);
    }

    // =================================
    // pushN
    // =================================

    private void pushN(List<Id> ids) {

      if (depthFirst)
        // reversed because we want ids.first to end up as open.first
        for (int i = ids.size() - 1; i >= 0; i--)
          push1(ids.get(i));
      else
        // forward because we want ids.first to end up as open.last
        for (Id id : ids)
          push1(id);
    }

    // =================================
    // push1
    // =================================

    private void push1(Id id) {

      if (closed.contains(id))
        return;

      closed.add(id);

      if (depthFirst)
        open.addFirst(id);
      else
        open.addLast(id);
    }

    // =================================
    // remove
    // =================================

    @Override
    public void remove() {
      throw new NotImplemented();
    }

  }
}
