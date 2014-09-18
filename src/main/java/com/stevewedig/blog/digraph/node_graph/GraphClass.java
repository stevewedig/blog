package com.stevewedig.blog.digraph.node_graph;

import java.util.*;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.alg.TraverseLib;
import com.stevewedig.blog.digraph.errors.*;
import com.stevewedig.blog.digraph.id_graph.IdGraph;
import com.stevewedig.blog.errors.*;
import com.stevewedig.blog.util.LambdaLib.Fn1;
import com.stevewedig.blog.value_objects.ValueMixin;

/**
 * An implementation of Graph.
 */
public class GraphClass<Id, Node> extends ValueMixin implements Graph<Id, Node> {

  // ===========================================================================
  // state
  // ===========================================================================

  private final IdGraph<Id> idGraph;
  private ImmutableBiMap<Id, Node> id__node;

  @Override
  public Object[] fields() {
    return array("idGraph", idGraph, "id__node", id__node);
  }

  // ===========================================================================
  // constructor
  // ===========================================================================

  public GraphClass(IdGraph<Id> idGraph, ImmutableBiMap<Id, Node> id__node, boolean allowPartial) {
    this.idGraph = idGraph;
    this.id__node = id__node;

    validate(allowPartial);
  }

  // ===================================
  // validate
  // ===================================

  private void validate(boolean allowPartial) {

    if (!allowPartial && !unboundIdSet().isEmpty())
      throw new GraphIsMissingNodes("unbound ids = %s", unboundIdSet());

    // =============

    Set<Id> graphIds = idGraph.idSet();

    Set<Id> nodeMapIds = id__node.keySet();

    Set<Id> unexpectedIds = Sets.difference(nodeMapIds, graphIds);

    if (!unexpectedIds.isEmpty())
      throw new GraphHadUnexpectedIds("unexpectedIds = %s, graphIds = %s, nodeMapIds = %s",
          unexpectedIds, graphIds, nodeMapIds);
  }

  // ===========================================================================
  // idGraph
  // ===========================================================================

  @Override
  public IdGraph<Id> idGraph() {
    return idGraph;
  }

  // ===========================================================================
  // idSet
  // ===========================================================================

  @Override
  public ImmutableSet<Id> idSet() {
    return idGraph.idSet();
  }

  @Override
  public int idSize() {
    return idGraph.idSize();
  }

  @Override
  public void assertIdsMatch(ImmutableSet<Id> ids) {
    idGraph.assertIdsMatch(ids);
  }

  @Override
  public void assertIdsMatch(Id[] ids) {
    idGraph.assertIdsMatch(ids);
  }

  // ===========================================================================
  // nodeSet
  // ===========================================================================

  @Override
  public ImmutableSet<Node> nodeSet() {
    return id__node.values();
  }

  @Override
  public int nodeSize() {
    return id__node.size();
  }

  // ===========================================================================
  // mapping id -> node
  // ===========================================================================

  @Override
  public ImmutableBiMap<Id, Node> id__node() {
    return id__node;
  }

  // ===================================

  @Override
  public boolean containsNodeForId(Id id) {
    return id__node.containsKey(id);
  }

  // ===================================

  @Override
  public Node node(Id id) {

    if (!containsNodeForId(id))
      throw new NotContained("id = %s", id);

    return id__node.get(id);
  }

  // ===================================

  @Override
  public Fn1<Id, Node> nodeLambda() {
    if (nodeLambda == null)
      nodeLambda = new Fn1<Id, Node>() {
        @Override
        public Node apply(Id id) {
          return node(id);
        }
      };
    return nodeLambda;
  }

  private Fn1<Id, Node> nodeLambda;

  // ===========================================================================
  // unboundIds (ids without nodes)
  // ===========================================================================

  @Override
  public ImmutableSet<Id> unboundIdSet() {
    if (unboundIds == null)
      unboundIds = ImmutableSet.copyOf(Sets.difference(idSet(), id__node.keySet()));
    return unboundIds;
  }

  private ImmutableSet<Id> unboundIds;

  // ===========================================================================
  // parents
  // ===========================================================================

  @Override
  public ImmutableSetMultimap<Id, Id> id__parentIds() {
    return idGraph.id__parentIds();
  }

  @Override
  public ImmutableSet<Id> parentIdSet(Id id) {
    return idGraph.parentIdSet(id);
  }

  @Override
  public Fn1<Id, List<Id>> parentIdListLambda() {
    return idGraph.parentIdListLambda();
  }

  @Override
  public ImmutableSet<Node> parentNodeSet(Id id) {
    return nodeWrapSet(parentIdSet(id), false);
  }

  @Override
  public SetMultimap<Id, Id> filterParentMap(Set<Id> ids) {
    return idGraph.filterParentMap(ids);
  }

  // ===========================================================================
  // children
  // ===========================================================================

  @Override
  public ImmutableSetMultimap<Id, Id> id__childIds() {
    return idGraph.id__childIds();
  }

  @Override
  public ImmutableSet<Id> childIdSet(Id id) {
    return idGraph.childIdSet(id);
  }

  @Override
  public Fn1<Id, List<Id>> childIdListLambda() {
    return idGraph.childIdListLambda();
  }

  @Override
  public ImmutableSet<Node> childNodeSet(Id id) {
    return nodeWrapSet(childIdSet(id), false);
  }

  @Override
  public SetMultimap<Id, Id> filterChildMap(Set<Id> ids) {
    return idGraph.filterChildMap(ids);
  }

  // ===========================================================================
  // ancestors
  // ===========================================================================

  @Override
  public Iterable<Id> ancestorIdIterable(Id id, boolean inclusive) {
    return idGraph.ancestorIdIterable(id, inclusive);
  }

  @Override
  public Iterable<Id> ancestorIdIterable(Set<Id> ids, boolean inclusive) {
    return idGraph.ancestorIdIterable(ids, inclusive);
  }

  @Override
  public ImmutableSet<Id> ancestorIdSet(Id id, boolean inclusive) {
    return idGraph.ancestorIdSet(id, inclusive);
  }

  @Override
  public ImmutableSet<Id> ancestorIdSet(Set<Id> ids, boolean inclusive) {
    return idGraph.ancestorIdSet(ids, inclusive);
  }

  @Override
  public boolean ancestorOf(Id id, Id potentialDescendant, boolean inclusive) {
    return idGraph.ancestorOf(id, potentialDescendant, inclusive);
  }

  // ===================================

  @Override
  public Iterable<Node> ancestorNodeIterable(Id id, boolean inclusive) {
    return nodeWrapIterable(ancestorIdIterable(id, inclusive), false);
  }

  @Override
  public Iterable<Node> ancestorNodeIterable(Set<Id> ids, boolean inclusive) {
    return nodeWrapIterable(ancestorIdIterable(ids, inclusive), false);
  }

  @Override
  public ImmutableSet<Node> ancestorNodeSet(Id id, boolean inclusive) {
    return nodeWrapSet(ancestorIdIterable(id, inclusive), false);
  }

  @Override
  public ImmutableSet<Node> ancestorNodeSet(Set<Id> ids, boolean inclusive) {
    return nodeWrapSet(ancestorIdIterable(ids, inclusive), false);
  }

  // ===========================================================================
  // descendants
  // ===========================================================================

  @Override
  public Iterable<Id> descendantIdIterable(Id id, boolean inclusive) {
    return idGraph.descendantIdIterable(id, inclusive);
  }

  @Override
  public ImmutableSet<Id> descendantIdSet(Id id, boolean inclusive) {
    return idGraph.descendantIdSet(id, inclusive);
  }

  @Override
  public Iterable<Id> descendantIdIterable(Set<Id> ids, boolean inclusive) {
    return idGraph.descendantIdIterable(ids, inclusive);
  }

  @Override
  public ImmutableSet<Id> descendantIdSet(Set<Id> ids, boolean inclusive) {
    return idGraph.descendantIdSet(ids, inclusive);
  }

  @Override
  public boolean descendantOf(Id id, Id potentialAncestor, boolean inclusive) {
    return idGraph.descendantOf(id, potentialAncestor, inclusive);
  }

  // ===================================

  @Override
  public Iterable<Node> descendantNodeIterable(Id id, boolean inclusive) {
    return nodeWrapIterable(descendantIdIterable(id, inclusive), false);
  }

  @Override
  public Iterable<Node> descendantNodeIterable(Set<Id> ids, boolean inclusive) {
    return nodeWrapIterable(descendantIdIterable(ids, inclusive), false);
  }

  @Override
  public ImmutableSet<Node> descendantNodeSet(Id id, boolean inclusive) {
    return nodeWrapSet(descendantIdIterable(id, inclusive), false);
  }

  @Override
  public ImmutableSet<Node> descendantNodeSet(Set<Id> ids, boolean inclusive) {
    return nodeWrapSet(descendantIdIterable(ids, inclusive), false);
  }

  // ===========================================================================
  // roots (sources)
  // ===========================================================================

  @Override
  public ImmutableSet<Id> rootIdSet() {
    return idGraph.rootIdSet();
  }

  // ===================================

  @Override
  public ImmutableSet<Node> rootNodeSet() {
    if (rootNodes == null)
      rootNodes = nodeWrapSet(rootIdSet(), false);
    return rootNodes;
  }

  private ImmutableSet<Node> rootNodes;

  // ===========================================================================
  // leaves (sinks)
  // ===========================================================================

  @Override
  public ImmutableSet<Id> leafIdSet() {
    return idGraph.leafIdSet();
  }

  // ===================================

  @Override
  public ImmutableSet<Node> leafNodeSet() {
    if (leafNodes == null)
      leafNodes = nodeWrapSet(leafIdSet(), false);
    return leafNodes;
  }

  private ImmutableSet<Node> leafNodes;

  // ===========================================================================
  // topological sort
  // ===========================================================================

  @Override
  public boolean containsCycle() {
    return idGraph.containsCycle();
  }

  // ===================================

  @Override
  public Optional<ImmutableList<Id>> optionalTopsortIdList() {
    return idGraph.optionalTopsortIdList();
  }

  // ===================================

  @Override
  public Optional<ImmutableList<Node>> optionalTopsortNodeList() {

    if (optionalTopsortNodeList == null) {
      if (optionalTopsortIdList().isPresent())
        optionalTopsortNodeList = Optional.of(nodeWrapList(optionalTopsortIdList().get(), false));
      else
        optionalTopsortNodeList = Optional.absent();
    }

    return optionalTopsortNodeList;
  }

  private Optional<ImmutableList<Node>> optionalTopsortNodeList;

  // ===========================================================================
  // generic traversal
  // ===========================================================================

  @Override
  public Iterable<Id> idIterable(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Id, List<Id>> expand) {

    return idGraph.idIterable(depthFirst, includeStarts, startIds, expand);
  }

  @Override
  public ImmutableList<Id> idList(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Id, List<Id>> expand) {

    return idGraph.idList(depthFirst, includeStarts, startIds, expand);
  }

  @Override
  public Iterable<Node> nodeIterable(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Node, List<Id>> expand) {

    return TraverseLib.nodeIterable(depthFirst, includeStarts, startIds, expand, nodeLambda());
  }

  @Override
  public ImmutableList<Node> nodeList(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Node, List<Id>> expand) {

    return ImmutableList.copyOf(nodeIterable(depthFirst, includeStarts, startIds, expand));
  }

  // ===========================================================================
  // converting id collections to node collections
  // ===========================================================================

  @Override
  public ImmutableSet<Node> nodeWrapSet(Iterable<Id> ids, boolean skipMissingNodes) {

    ImmutableSet.Builder<Node> nodeSet = ImmutableSet.builder();

    for (Id id : ids)
      if (skipMissingNodes && !containsNodeForId(id))
        continue;
      else
        nodeSet.add(id__node.get(id));

    return nodeSet.build();
  }

  // ===================================

  @Override
  public ImmutableList<Node> nodeWrapList(Iterable<Id> ids, boolean skipMissingNodes) {

    ImmutableList.Builder<Node> nodeList = ImmutableList.builder();

    for (Id id : ids)
      if (skipMissingNodes && !containsNodeForId(id))
        continue;
      else
        nodeList.add(id__node.get(id));

    return nodeList.build();
  }

  // ===================================

  @Override
  public Optional<Node> nodeWrapOptional(Optional<Id> optionalId, boolean skipMissingNode) {

    if (!optionalId.isPresent())
      return Optional.absent();

    Id id = optionalId.get();

    if (skipMissingNode && !containsNodeForId(id))
      return Optional.absent();

    return Optional.of(node(id));
  }

  // ===================================

  @Override
  public Iterable<Node> nodeWrapIterable(final Iterable<Id> idIterable,
      final boolean skipMissingNodes) {

    return new Iterable<Node>() {
      @Override
      public Iterator<Node> iterator() {
        return nodeWrapIterator(idIterable.iterator(), skipMissingNodes);
      }
    };
  }

  // ===================================

  @Override
  public Iterator<Node> nodeWrapIterator(final Iterator<Id> idIterator,
      final boolean skipMissingNodes) {

    return new Iterator<Node>() {

      @Override
      public boolean hasNext() {

        findNextIdWithNode();

        return nextId != null;
      }

      @Override
      public Node next() {

        findNextIdWithNode();

        if (nextId == null)
          throw new NoSuchElementException();

        Node node = node(nextId);

        nextId = null;

        return node;
      }

      @Override
      public void remove() {
        throw new NotImplemented();
      }

      // ===========

      private void findNextIdWithNode() {

        while (nextId == null && idIterator.hasNext()) {

          Id id = idIterator.next();

          if (skipMissingNodes && !containsNodeForId(id))
            continue;

          nextId = id;
        }
      }

      private Id nextId;

    };
  }

  // ===========================================================================
  // implementing Set<Node> via delegation
  // ===========================================================================

  @Override
  public Iterator<Node> iterator() {
    return nodeSet().iterator();
  }

  @Override
  public int size() {
    return nodeSet().size();
  }

  @Override
  public boolean isEmpty() {
    return nodeSet().isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return nodeSet().contains(o);
  }

  @Override
  public Object[] toArray() {
    return nodeSet().toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return nodeSet().toArray(a);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return nodeSet().containsAll(c);
  }

  @Deprecated
  @Override
  public boolean add(Node e) {
    throw new NotMutable();
  }

  @Deprecated
  @Override
  public boolean remove(Object o) {
    throw new NotMutable();
  }

  @Deprecated
  @Override
  public boolean addAll(Collection<? extends Node> c) {
    throw new NotMutable();
  }

  @Deprecated
  @Override
  public boolean retainAll(Collection<?> c) {
    throw new NotMutable();
  }

  @Deprecated
  @Override
  public boolean removeAll(Collection<?> c) {
    throw new NotMutable();
  }

  @Deprecated
  @Override
  public void clear() {
    throw new NotMutable();
  }

}
