package com.stevewedig.blog.digraph.node_graph_partial;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.digraph.id_graph.IdGraph;
import com.stevewedig.blog.errors.NotContained;

/**
 * A partial digraph containing nodes.
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
  // nodes
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
   * Getting the id associated with a node.
   */
  Id getId(Node node);

  /**
   * Getting the node associated with an id.
   */
  Node getNode(Id id) throws NotContained;

  /**
   * The ids in the idGraph which don't have associated nodes in id__node.
   */
  ImmutableSet<Id> unboundIdSet();

  // ===========================================================================
  // conversions from ids to nodes
  // ===========================================================================

  /**
   * Converting ids to a node set.
   */
  ImmutableSet<Node> convertSet(Iterable<Id> ids, boolean skipMissingNodes);

  /**
   * Converting ids to a node list.
   */
  ImmutableList<Node> convertList(Iterable<Id> ids, boolean skipMissingNodes);

  /**
   * Converting optional id to an optional node.
   */
  Optional<Node> convertOptional(Optional<Id> id, boolean skipMissingNode);

  /**
   * Converting an id iterable to a node iterable.
   */
  Iterable<Node> convertIterable(Iterable<Id> idIterable, boolean skipMissingNodes);

  /**
   * Converting an id iterator to a node iterator.
   */
  Iterator<Node> convertIterator(Iterator<Id> idIterator, boolean skipMissingNodes);

  // ===========================================================================
  // deprecating Set's mutation methods (this library's graphs are immutable)
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
