package com.stevewedig.blog.digraph.alg;

import java.util.*;

import com.google.common.collect.*;
import com.stevewedig.blog.errors.*;
import com.stevewedig.blog.util.LambdaLib.Fn1;

public abstract class TraverseLib {

  // ===========================================================================
  // iterable
  // ===========================================================================

  public static <Id> Iterable<Id> idIterable(final boolean depthFirst, final boolean includeStarts,
      final ImmutableList<Id> startIds, final Fn1<Id, List<Id>> expand) {

    Fn1<Id, Id> lookup = new Fn1<Id, Id>() {
      @Override
      public Id apply(Id id) {
        return id;
      }
    };

    return new IterableClass<Id, Id>(depthFirst, includeStarts, startIds, expand, lookup);
  }

  public static <Id, Node> Iterable<Node> nodeIterable(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Node, List<Id>> expand, Fn1<Id, Node> lookup) {

    return new IterableClass<Id, Node>(depthFirst, includeStarts, startIds, expand, lookup);
  }

  // ===========================================================================
  // IterableClass
  // ===========================================================================

  private static class IterableClass<Id, Node> implements Iterable<Node> {

    private final boolean depthFirst;
    private final boolean includeStarts;
    private final ImmutableList<Id> startIds;
    private final Fn1<Node, List<Id>> expand;
    private final Fn1<Id, Node> lookup;

    public IterableClass(boolean depthFirst, boolean includeStarts, ImmutableList<Id> startIds,
        Fn1<Node, List<Id>> expand, Fn1<Id, Node> lookup) {

      this.depthFirst = depthFirst;
      this.includeStarts = includeStarts;
      this.startIds = startIds;
      this.expand = expand;
      this.lookup = lookup;
    }

    @Override
    public Iterator<Node> iterator() {
      return TraverseLib.nodeIterator(depthFirst, includeStarts, startIds, expand, lookup);
    }

  }

  // ===========================================================================
  // iterator
  // ===========================================================================

  public static <Id> Iterator<Id> idIterator(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Id, List<Id>> expand) {

    Fn1<Id, Id> lookup = new Fn1<Id, Id>() {
      @Override
      public Id apply(Id id) {
        return id;
      }
    };

    return new IteratorClass<Id, Id>(depthFirst, includeStarts, startIds, expand, lookup);
  }

  public static <Id, Node> Iterator<Node> nodeIterator(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Node, List<Id>> expand, Fn1<Id, Node> lookup) {

    return new IteratorClass<Id, Node>(depthFirst, includeStarts, startIds, expand, lookup);
  }

  // ===========================================================================
  // IteratorClass
  // ===========================================================================

  private static class IteratorClass<Id, Node> implements Iterator<Node> {

    // =================================
    // inputs
    // =================================

    private final boolean depthFirst;
    private final boolean includeStarts;
    private final Fn1<Node, List<Id>> expand;
    private final Fn1<Id, Node> lookup;
    private final ImmutableSet<Id> starts;

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

    public IteratorClass(boolean depthFirst, boolean includeStarts, ImmutableList<Id> startIds,
        Fn1<Node, List<Id>> expand, Fn1<Id, Node> lookup) {

      this.depthFirst = depthFirst;
      this.includeStarts = includeStarts;
      this.expand = expand;
      this.lookup = lookup;

      starts = ImmutableSet.copyOf(startIds);

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

        Node node;
        try {
          node = lookup.apply(id);

        } catch (NotContained e) {
          // this happens when a node graph is partial
          continue;
        }

        expand(node);

        if (!includeStarts && starts.contains(id))
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

      // reversed because we want expanded.first to end up as open.first or open.last
      for (int i = ids.size() - 1; i >= 0; i--) {

        Id id = ids.get(i);

        push1(id);
      }
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
