package com.stevewedig.blog.digraph.node_graph;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Sets;
import com.stevewedig.blog.digraph.id_graph.GraphValidationErrors.GraphContainedUnexpectedIds;
import com.stevewedig.blog.digraph.id_graph.IdGraph;
import com.stevewedig.blog.errors.NotContained;
import com.stevewedig.blog.errors.NotImplemented;
import com.stevewedig.blog.errors.NotMutable;
import com.stevewedig.blog.util.LambdaLib.Fn1;
import com.stevewedig.blog.value_objects.ValueMixin;

// TODO improve the toString, also on IdGraph
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

  public GraphClass(IdGraph<Id> idGraph, ImmutableBiMap<Id, Node> id__node) {
    this.idGraph = idGraph;
    this.id__node = id__node;

    validate();
  }

  private void validate() {

    Set<Id> graphIds = idGraph.idSet();

    Set<Id> nodeMapIds = id__node.keySet();

    Set<Id> unexpectedIds = Sets.difference(nodeMapIds, graphIds);

    if (!unexpectedIds.isEmpty())
      throw new GraphContainedUnexpectedIds("unexpectedIds = %s, graphIds = %s, nodeMapIds = %s",
          unexpectedIds, graphIds, nodeMapIds);

    // note that it is ok for the id graph to have more ids than the node map, because node graphs
    // are allowed to be partial
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
  // unboundIds (ids without nodes)
  // ===========================================================================

  @Override
  public ImmutableSet<Id> unboundIdSet() {
    if (unboundIds == null)
      unboundIds = ImmutableSet.copyOf(Sets.difference(idSet(), id__node.keySet()));
    return unboundIds;
  }

  private ImmutableSet<Id> unboundIds;

  // ===================================

  @Override
  public boolean isComplete() {
    return unboundIdSet().isEmpty();
  }

  @Override
  public boolean isPartial() {
    return !isComplete();
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
    return nodeWrapSet(parentIdSet(id));
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
    return nodeWrapSet(childIdSet(id));
  }

  // ===========================================================================
  // ancestors
  // ===========================================================================

  @Override
  public Iterable<Id> ancestorIdIterable(Id id) {
    return idGraph.ancestorIdIterable(id);
  }

  @Override
  public ImmutableSet<Id> ancestorIdSet(Id id) {
    return idGraph.ancestorIdSet(id);
  }

  @Override
  public Iterable<Node> ancestorNodeIterable(Id id) {
    return nodeWrapIterable(ancestorIdIterable(id));
  }

  @Override
  public ImmutableSet<Node> ancestorNodeSet(Id id) {
    return nodeWrapSet(ancestorIdIterable(id));
  }

  // ===========================================================================
  // descendants
  // ===========================================================================

  @Override
  public Iterable<Id> descendantIdIterable(Id id) {
    return idGraph.descendantIdIterable(id);
  }

  @Override
  public ImmutableSet<Id> descendantIdSet(Id id) {
    return idGraph.descendantIdSet(id);
  }

  @Override
  public Iterable<Node> descendantNodeIterable(Id id) {
    return nodeWrapIterable(descendantIdIterable(id));
  }

  @Override
  public ImmutableSet<Node> descendantNodeSet(Id id) {
    return nodeWrapSet(descendantIdIterable(id));
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
      rootNodes = nodeWrapSet(rootIdSet());
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
      leafNodes = nodeWrapSet(leafIdSet());
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
        optionalTopsortNodeList = Optional.of(nodeWrapList(optionalTopsortIdList().get()));
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
  public Iterable<Node> nodeIterable(boolean depthFirst, boolean includeStarts,
      ImmutableList<Id> startIds, Fn1<Id, List<Id>> expand) {

    return nodeWrapIterable(idIterable(depthFirst, includeStarts, startIds, expand));
  }

  // ===========================================================================
  // converting id collections to node collections
  // ===========================================================================

  @Override
  public ImmutableSet<Node> nodeWrapSet(Iterable<Id> ids) {

    ImmutableSet.Builder<Node> nodeSet = ImmutableSet.builder();

    for (Id id : ids)
      if (containsNodeForId(id))
        nodeSet.add(id__node.get(id));

    return nodeSet.build();
  }

  // ===================================

  @Override
  public ImmutableList<Node> nodeWrapList(Iterable<Id> ids) {

    ImmutableList.Builder<Node> nodeList = ImmutableList.builder();

    for (Id id : ids)
      if (containsNodeForId(id))
        nodeList.add(id__node.get(id));

    return nodeList.build();
  }

  // ===================================

  @Override
  public Optional<Node> nodeWrapOptional(Optional<Id> idOpt) {

    if (!idOpt.isPresent())
      return Optional.absent();

    Id id = idOpt.get();

    if (!containsNodeForId(id))
      return Optional.absent();

    return Optional.of(id__node.get(id));
  }

  // ===================================

  @Override
  public Iterable<Node> nodeWrapIterable(final Iterable<Id> idIterable) {

    return new Iterable<Node>() {
      @Override
      public Iterator<Node> iterator() {
        return nodeWrapIterator(idIterable.iterator());
      }
    };
  }

  // ===================================

  @Override
  public Iterator<Node> nodeWrapIterator(final Iterator<Id> idIterator) {

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

          nextId = idIterator.next();

          if (!containsNodeForId(nextId))
            nextId = null;
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
