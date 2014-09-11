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

/**
 * A digraph containing nodes.
 */
public interface Graph<Id, Node> extends IdGraph<Id>, Set<Node> {

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

  /**
   * True if there are no unbound ids.
   */
  boolean isComplete();

  /**
   * True if there are unbound ids.
   */
  boolean isPartial();

  // ===========================================================================
  // parents
  // ===========================================================================

  /**
   * Getting an id's parent node set.
   */
  ImmutableSet<Node> parentNodeSet(Id id);

  // ===========================================================================
  // children
  // ===========================================================================

  /**
   * Getting an id's child node set.
   */
  ImmutableSet<Node> childNodeSet(Id id);

  // ===========================================================================
  // ancestors
  // ===========================================================================

  /**
   * Getting an id's ancestor node iterable (its parents, it's parents' parents, and so on).
   */
  Iterable<Node> ancestorNodeIterable(Id id);

  /**
   * Getting an id's ancestor node set (its parents, it's parents' parents, and so on).
   */
  ImmutableSet<Node> ancestorNodeSet(Id id);

  // ===========================================================================
  // descendants
  // ===========================================================================

  /**
   * Getting an id's descendant node iterable (its children, it's childrens' children, and so on).
   */
  Iterable<Node> descendantNodeIterable(Id id);

  /**
   * Getting an id's descendant node set (its children, it's childrens' children, and so on).
   */
  ImmutableSet<Node> descendantNodeSet(Id id);

  // ===========================================================================
  // roots (sources)
  // ===========================================================================

  /**
   * The digraph's root (source) nodes, so the nodes without parents.
   */
  ImmutableSet<Node> rootNodeSet();

  // ===========================================================================
  // leaves (sinks)
  // ===========================================================================

  /**
   * The digraph's leaf (sink) nodes, so the nodes without children.
   */
  ImmutableSet<Node> leafNodeSet();

  // ===========================================================================
  // topological sort
  // ===========================================================================

  /**
   * A topologically sorted list of nodes, with roots (sources) first (will be absent if the digraph is cyclic).
   */
  Optional<ImmutableList<Node>> optionalTopsortNodeList();

  // ===========================================================================
  // generic traversal
  // ===========================================================================

  /**
   * Generic node traversal as an iterable.
   * 
   * @param depthFirst Whether to traverse depth first or breadth first.
   * @param includeStarts Whether to include the start nodes in the traversal.
   * @param startIds The initial id set.
   * @param expand A function mapping a node to the next ids.
   * @return A node iterable corresponding to the traversal.
   */
  Iterable<Node> nodeIterable(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Node, List<Id>> expand);

  /**
   * Generic node traversal copied into a list.
   * 
   * @param depthFirst Whether to traverse depth first or breadth first.
   * @param includeStarts Whether to include the start nodes in the traversal.
   * @param startIds The initial id set.
   * @param expand A function mapping a node to the next ids.
   * @return A node list corresponding to the traversal.
   */
  ImmutableList<Node> nodeList(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Node, List<Id>> expand);

  // ===========================================================================
  // converting id collections to node collections
  // ===========================================================================

  /**
   * Converting ids to a node set.
   */
  ImmutableSet<Node> nodeWrapSet(Iterable<Id> ids);

  /**
   * Converting ids to a node list.
   */
  ImmutableList<Node> nodeWrapList(Iterable<Id> ids);

  /**
   * Converting an optional id to an optional node.
   */
  Optional<Node> nodeWrapOptional(Optional<Id> optionalId);

  /**
   * Converting an id iterable to a node iterable.
   */
  Iterable<Node> nodeWrapIterable(Iterable<Id> idIterable);

  /**
   * Converting an id iterator to a node iterator.
   */
  Iterator<Node> nodeWrapIterator(Iterator<Id> idIterator);

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

