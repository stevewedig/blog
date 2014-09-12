package com.stevewedig.blog.digraph.node_graph_partial;

import java.util.*;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.id_graph.IdGraph;
import com.stevewedig.blog.errors.NotContained;
import com.stevewedig.blog.util.LambdaLib.Fn1;

/**
 * A digraph containing nodes.
 */
public interface PartialGraph<Id, Node> extends IdGraph<Id>, Set<Node> {

  // ===========================================================================
  // exposing inner idGraph
  // ===========================================================================

  /**
   * The internal id graph.
   */
  IdGraph<Id> idGraph();

  // ===========================================================================
  // nodeSet
  // ===========================================================================

  /**
   * An ImmutableSet of nodes (note that Graph also implements Set&lt;Node&gt;).
   */
  ImmutableSet<Node> nodeSet();

  /**
   * The size of the node set, will be small than idSize() in partial graphs.
   */
  int nodeSize();

  // ===========================================================================
  // mapping id -> node
  // ===========================================================================

  /**
   * The mapping between id and node.
   */
  ImmutableBiMap<Id, Node> id__node();

  /**
   * Whether a node is associated with this id.
   */
  boolean containsNodeForId(Id id);

  /**
   * Getting the node associated with an id.
   */
  Node node(Id id) throws NotContained;

  /**
   * A lambda that get's the node associated with an id.
   */
  Fn1<Id, Node> nodeLambda();

  // ===========================================================================
  // unboundIds (ids without nodes)
  // ===========================================================================

  /**
   * The ids in the idGraph which don't have associated nodes in id__node.
   */
  ImmutableSet<Id> unboundIdSet();

  // ===========================================================================
  // converting id collections to node collections
  // ===========================================================================

  /**
   * Converting ids to a node set.
   */
  ImmutableSet<Node> nodeWrapSet(Iterable<Id> ids, boolean skipMissingNodes);

  /**
   * Converting ids to a node list.
   */
  ImmutableList<Node> nodeWrapList(Iterable<Id> ids, boolean skipMissingNodes);

  /**
   * Converting optional id to an optional node.
   */
  Optional<Node> nodeWrapOptional(Optional<Id> id, boolean skipMissingNode);

  /**
   * Converting an id iterable to a node iterable.
   */
  Iterable<Node> nodeWrapIterable(Iterable<Id> idIterable, boolean skipMissingNodes);

  /**
   * Converting an id iterator to a node iterator.
   */
  Iterator<Node> nodeWrapIterator(Iterator<Id> idIterator, boolean skipMissingNodes);

  // ===========================================================================
  // deprecating Set's mutation methods (Graphs are immutable)
  // ===========================================================================

  /**
   * Not implemented because graphs are immutable.
   */
  @Deprecated
  @Override
  boolean add(Node e);
  
  /**
   * Not implemented because graphs are immutable.
   */
  @Deprecated
  @Override
  boolean remove(Object o);
  
  /**
   * Not implemented because graphs are immutable.
   */
  @Deprecated
  @Override
  boolean addAll(Collection<? extends Node> c);

  /**
   * Not implemented because graphs are immutable.
   */
  @Deprecated
  @Override
  boolean retainAll(Collection<?> c);
  
  /**
   * Not implemented because graphs are immutable.
   */
  @Deprecated
  @Override
  boolean removeAll(Collection<?> c);
  
  /**
   * Not implemented because graphs are immutable.
   */
  @Deprecated
  @Override
  void clear();

}

