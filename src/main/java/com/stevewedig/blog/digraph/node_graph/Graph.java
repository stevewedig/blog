package com.stevewedig.blog.digraph.node_graph;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.digraph.id_graph.IdGraph;
import com.stevewedig.blog.errors.NotContained;
import com.stevewedig.blog.util.LambdaLib.Fn1;

public interface Graph<Id, Node> extends IdGraph<Id>, Set<Node> {

  // ===========================================================================
  // exposing inner idGraph
  // ===========================================================================

  IdGraph<Id> idGraph();

  // ===========================================================================
  // nodeSet
  // ===========================================================================

  /**
   * @return ImmutableSet of nodes, although Graph implements Set&lt;Node&gt;.
   */
  ImmutableSet<Node> nodeSet();

  int nodeSize();

  // ===========================================================================
  // mapping id -> node
  // ===========================================================================

  /**
   * @return The mapping between id and node.
   */
  ImmutableBiMap<Id, Node> id__node();

  boolean containsNodeForId(Id id);

  /**
   * @return The node associated with the id (will not return null).
   */
  Node node(Id id) throws NotContained;

  Fn1<Id, Node> nodeLambda();

  // ===========================================================================
  // unboundIds (ids without nodes)
  // ===========================================================================

  /**
   * @return The ids which are referenced but don't have associated nodes in the graph.
   */
  ImmutableSet<Id> unboundIdSet();

  /**
   * @return True if there are no unbound ids.
   */
  boolean isComplete();

  /**
   * @return True if there are unbound ids.
   */
  boolean isPartial();

  // ===========================================================================
  // parents
  // ===========================================================================

  ImmutableSet<Node> parentNodeSet(Id id);

  // ===========================================================================
  // children
  // ===========================================================================

  ImmutableSet<Node> childNodeSet(Id id);

  // ===========================================================================
  // ancestors
  // ===========================================================================

  Iterable<Node> ancestorNodeIterable(Id id);

  /**
   * 
   * @param id
   * @return ancestorNodes, not inclusive
   */
  ImmutableSet<Node> ancestorNodeSet(Id id);

  // ===========================================================================
  // descendants
  // ===========================================================================

  Iterable<Node> descendantNodeIterable(Id id);

  /**
   * 
   * @param id
   * @return descendantNodes, not inclusive
   */
  ImmutableSet<Node> descendantNodeSet(Id id);

  // ===========================================================================
  // roots (sources)
  // ===========================================================================

  ImmutableSet<Node> rootNodeSet();

  // ===========================================================================
  // leaves (sinks)
  // ===========================================================================

  ImmutableSet<Node> leafNodeSet();

  // ===========================================================================
  // topological sort
  // ===========================================================================

  /**
   * @return Topologically sorted list of nodes, will be absent if graph contains a cycle.
   */
  Optional<ImmutableList<Node>> optionalTopsortNodeList();

  // ===========================================================================
  // generic traversal
  // ===========================================================================

  /**
   * Generic node traversal.
   * 
   * @param depthFirst Whether to traverse depth first or breadth first.
   * @param includeStarts Whether to include the start nodes in the traversal.
   * @param startIds The initial id set.
   * @param expand A function mapping a node to the next ids.
   * @return A node iterable corresponding to the traversal.
   */
  Iterable<Node> nodeIterable(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Node, List<Id>> expand);

  // ===========================================================================
  // converting id collections to node collections
  // ===========================================================================

  /**
   * @return Node set corresponding to the id set.
   */
  ImmutableSet<Node> nodeWrapSet(Iterable<Id> ids);

  /**
   * @return Node list corresponding to the id set.
   */
  ImmutableList<Node> nodeWrapList(Iterable<Id> ids);

  Optional<Node> nodeWrapOptional(Optional<Id> optionalId);

  Iterable<Node> nodeWrapIterable(Iterable<Id> idIterable);

  Iterator<Node> nodeWrapIterator(Iterator<Id> idIterator);

  // ===========================================================================
  // deprecating Set's mutation methods (Graphs are immutable)
  // ===========================================================================

  @Deprecated
  @Override
  boolean add(Node e);

  @Deprecated
  @Override
  boolean remove(Object o);

  @Deprecated
  @Override
  boolean addAll(Collection<? extends Node> c);

  @Deprecated
  @Override
  boolean retainAll(Collection<?> c);

  @Deprecated
  @Override
  boolean removeAll(Collection<?> c);

  @Deprecated
  @Override
  void clear();

}
