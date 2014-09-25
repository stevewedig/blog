package com.stevewedig.blog.digraph.alg;

import java.util.*;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.util.MultimapLib;

/**
 * Generic topological sort.
 */
public abstract class TopsortLib {

  // http://en.wikipedia.org/wiki/Topological_sorting
  //
  // L <- Empty list that will contain the sorted elements
  // S <- Set of all nodes with no incoming edges
  // while S is non-empty do
  // -- remove a node n from S
  // -- add n to tail of L
  // -- for each node m with an edge e from n to m do
  // ----- remove edge e from the graph
  // ----- if m has no other incoming edges then
  // -------- insert m into S
  // if graph has edges then
  // -- return error (graph has at least one cycle)
  // else
  // -- return L (a topologically sorted order)

  /**
   * Generic topological sort, starting with node ids and the dependency structure between ids. If
   * your dag nodes don't have ids, you can just use the nodes themselves as ids.
   * 
   * @param idSet The node ids in your dag.
   * @param id__parents The dependency structure of your dag.
   * @return A topological sort of ids with roots (sources) at the start, will be absent if the
   *         graph is cyclic.
   */
  public static <Id> Optional<ImmutableList<Id>> sort(ImmutableSet<Id> idSet,
      ImmutableSetMultimap<Id, Id> id__parents) {

    // need to traverse in this direction as well
    ImmutableMultimap<Id, Id> id__children = id__parents.inverse();

    // building a topologically sorted id list
    ImmutableList.Builder<Id> sorted = ImmutableList.builder();

    // ids are closed when the are added to sorted
    Set<Id> closed = new HashSet<>();

    // ids are opened when their parents are all closed
    Stack<Id> open = new Stack<>();

    // parent counts
    Map<Id, Integer> id__parentCount = new HashMap<>();
    for (Id id : idSet) {

      int parentCount = id__parents.get(id).size();
      id__parentCount.put(id, parentCount);

      // the graph's roots (sources) form the initial open set
      if (parentCount == 0)
        open.push(id);
    }

    // loop: close an open id and see if that opens any of its children
    while (!open.isEmpty()) {

      Id id = open.pop();

      // close
      sorted.add(id);
      closed.add(id);

      // update parent counts of children, and open if now 0
      for (Id child : id__children.get(id)) {

        int parentCount = id__parentCount.get(child) - 1;
        id__parentCount.put(child, parentCount);

        if (parentCount == 0)
          open.push(child);
      };
    }

    // if we're missing any ids there is a cycle
    if (idSet.size() != closed.size())
      return Optional.absent();

    // return the topologically sorted id list
    return Optional.of(sorted.build());
  }

  /**
   * Generic topological sort, starting with the dependency structure between ids (this function assume
   * the graph is connected, so all ids have an arc). If your dag nodes don't have ids, you can just
   * use the nodes themselves as ids.
   * 
   * @param id__parents The dependency structure of your dag.
   * @return A topological sort of ids with roots (sources) at the start, will be absent if the
   *         graph is cyclic.
   */
  public static <Id> Optional<ImmutableList<Id>> sort(ImmutableSetMultimap<Id, Id> id__parents) {

    ImmutableSet<Id> idSet = MultimapLib.keysAndValues(id__parents);

    return sort(idSet, id__parents);
  }

}
