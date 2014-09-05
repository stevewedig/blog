package com.stevewedig.blog.digraph.alg;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.errors.NotImplemented;
import com.stevewedig.blog.util.LambdaLib.Fn1;

public abstract class TraverseLib {

  // ===========================================================================
  // iterable
  // ===========================================================================

  public static <Id> Iterable<Id> iterable(final boolean depthFirst, final boolean includeStarts,
      final ImmutableList<Id> startIds, final Fn1<Id, List<Id>> expand) {

    return new IterableClass<>(depthFirst, includeStarts, expand, startIds);
  }

  // ===========================================================================
  // IterableClass
  // ===========================================================================

  private static class IterableClass<Id> implements Iterable<Id> {

    private final boolean depthFirst;
    private final boolean includeStarts;
    private final Fn1<Id, List<Id>> expand;
    private final ImmutableList<Id> startIds;

    public IterableClass(boolean depthFirst, boolean includeStarts, Fn1<Id, List<Id>> expand,
        ImmutableList<Id> startIds) {

      this.depthFirst = depthFirst;
      this.includeStarts = includeStarts;
      this.expand = expand;
      this.startIds = startIds;
    }

    @Override
    public Iterator<Id> iterator() {
      return TraverseLib.iterator(depthFirst, includeStarts, startIds, expand);
    }

  }

  // ===========================================================================
  // iterator
  // ===========================================================================

  public static <Id> Iterator<Id> iterator(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Id, List<Id>> expand) {

    return new IteratorClass<Id>(depthFirst, includeStarts, startIds, expand);
  }

  // ===========================================================================
  // IteratorClass
  // ===========================================================================

  private static class IteratorClass<Id> implements Iterator<Id> {

    // =================================
    // inputs
    // =================================

    private final boolean depthFirst;
    private final boolean includeStarts;
    private final Fn1<Id, List<Id>> expand;
    private final ImmutableSet<Id> starts;

    // =================================
    // state
    // =================================

    // LinkedList has tail pointer, so should be fast as FIFO (breadth first) and LIFO (depth first)
    // http://stackoverflow.com/a/25207657
    private final LinkedList<Id> open = new LinkedList<Id>();

    // for deduplication
    private final Set<Id> closed = new HashSet<>();

    private Id nextId = null;

    // =================================
    // constructor
    // =================================

    public IteratorClass(boolean depthFirst, boolean includeStarts, ImmutableList<Id> startIds,
        Fn1<Id, List<Id>> expand) {

      this.depthFirst = depthFirst;
      this.includeStarts = includeStarts;
      this.expand = expand;

      starts = ImmutableSet.copyOf(startIds);

      pushN(startIds);
    }

    // =================================
    // hasNext
    // =================================

    @Override
    public boolean hasNext() {

      findNext();

      return nextId != null;
    }

    // =================================
    // next
    // =================================

    @Override
    public Id next() {

      findNext();

      if (nextId == null)
        throw new NoSuchElementException();

      Id id = nextId;

      nextId = null;

      return id;
    }

    // =================================
    // findNext
    // =================================

    private void findNext() {

      while (nextId == null && !open.isEmpty()) {

        Id id = open.removeFirst();

        expand(id);

        if (!includeStarts && starts.contains(id))
          continue;

        nextId = id;
      }
    }

    // =================================
    // remove
    // =================================

    @Override
    public void remove() {
      throw new NotImplemented();
    }

    // =================================
    // expand
    // =================================

    private void expand(Id node) {

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

  }
}
