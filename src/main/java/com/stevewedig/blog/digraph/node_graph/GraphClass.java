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
  // ids
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
  public void assertIdsEqual(ImmutableSet<Id> ids) {
    idGraph.assertIdsEqual(ids);
  }

  @Override
  public void assertIdsEqual(Id[] ids) {
    idGraph.assertIdsEqual(ids);
  }

  @Override
  public IdGraph<Id> filterIdGraph(Set<Id> ids) {
    return idGraph.filterIdGraph(ids);
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
  public Id getId(Node node) {
    return id__node.inverse().get(node);
  }

  // ===================================

  @Override
  public Node getNode(Id id) {

    if (!containsNodeForId(id))
      throw new NotContained("id = %s", id);

    return id__node.get(id);
  }

  // ===================================

  private Fn1<Id, Node> nodeLambda() {
    if (nodeLambda == null)
      nodeLambda = new Fn1<Id, Node>() {
        @Override
        public Node apply(Id id) {
          return getNode(id);
        }
      };
    return nodeLambda;
  }

  private Fn1<Id, Node> nodeLambda;

  // ===================================

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
  public boolean isParentOf(Id id, Id potentialChild) {
    return idGraph.isParentOf(id, potentialChild);
  }

  @Override
  public ImmutableSetMultimap<Id, Id> id__parentIds() {
    return idGraph.id__parentIds();
  }

  @Override
  public ImmutableSet<Id> parentIdSet(Id id) {
    return idGraph.parentIdSet(id);
  }

  // ===================================

  @Override
  public ImmutableSet<Node> parentNodeSet(Id id) {
    return transformSet(parentIdSet(id), false);
  }

  // ===========================================================================
  // children
  // ===========================================================================

  @Override
  public boolean isChildOf(Id id, Id potentialParent) {
    return idGraph.isChildOf(id, potentialParent);
  }

  @Override
  public ImmutableSetMultimap<Id, Id> id__childIds() {
    return idGraph.id__childIds();
  }

  @Override
  public ImmutableSet<Id> childIdSet(Id id) {
    return idGraph.childIdSet(id);
  }

  // ===================================

  @Override
  public ImmutableSet<Node> childNodeSet(Id id) {
    return transformSet(childIdSet(id), false);
  }

  // ===========================================================================
  // ancestors
  // ===========================================================================

  @Override
  public boolean isAncestorOf(Id id, Id potentialDescendant, boolean inclusive) {
    return idGraph.isAncestorOf(id, potentialDescendant, inclusive);
  }

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
  public IdGraph<Id> ancestorIdGraph(Id id, boolean inclusive) {
    return idGraph.ancestorIdGraph(id, inclusive);
  }

  @Override
  public IdGraph<Id> ancestorIdGraph(Set<Id> ids, boolean inclusive) {
    return idGraph.ancestorIdGraph(ids, inclusive);
  }

  // ===================================

  @Override
  public Iterable<Node> ancestorNodeIterable(Id id, boolean inclusive) {
    return transformIterable(ancestorIdIterable(id, inclusive), false);
  }

  @Override
  public Iterable<Node> ancestorNodeIterable(Set<Id> ids, boolean inclusive) {
    return transformIterable(ancestorIdIterable(ids, inclusive), false);
  }

  @Override
  public ImmutableSet<Node> ancestorNodeSet(Id id, boolean inclusive) {
    return transformSet(ancestorIdIterable(id, inclusive), false);
  }

  @Override
  public ImmutableSet<Node> ancestorNodeSet(Set<Id> ids, boolean inclusive) {
    return transformSet(ancestorIdIterable(ids, inclusive), false);
  }

  // ===========================================================================
  // descendants
  // ===========================================================================

  @Override
  public boolean isDescendantOf(Id id, Id potentialAncestor, boolean inclusive) {
    return idGraph.isDescendantOf(id, potentialAncestor, inclusive);
  }

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
  public IdGraph<Id> descendantIdGraph(Id id, boolean inclusive) {
    return idGraph.descendantIdGraph(id, inclusive);
  }

  @Override
  public IdGraph<Id> descendantIdGraph(Set<Id> ids, boolean inclusive) {
    return idGraph.descendantIdGraph(ids, inclusive);
  }

  // ===================================

  @Override
  public Iterable<Node> descendantNodeIterable(Id id, boolean inclusive) {
    return transformIterable(descendantIdIterable(id, inclusive), false);
  }

  @Override
  public Iterable<Node> descendantNodeIterable(Set<Id> ids, boolean inclusive) {
    return transformIterable(descendantIdIterable(ids, inclusive), false);
  }

  @Override
  public ImmutableSet<Node> descendantNodeSet(Id id, boolean inclusive) {
    return transformSet(descendantIdIterable(id, inclusive), false);
  }

  @Override
  public ImmutableSet<Node> descendantNodeSet(Set<Id> ids, boolean inclusive) {
    return transformSet(descendantIdIterable(ids, inclusive), false);
  }

  // ===========================================================================
  // roots (sources)
  // ===========================================================================

  @Override
  public boolean isRoot(Id id) {
    return idGraph.isRoot(id);
  }

  // ===================================

  @Override
  public ImmutableSet<Id> rootIdSet() {
    return idGraph.rootIdSet();
  }

  // ===================================

  @Override
  public ImmutableSet<Node> rootNodeSet() {
    if (rootNodes == null)
      rootNodes = transformSet(rootIdSet(), false);
    return rootNodes;
  }

  private ImmutableSet<Node> rootNodes;

  // ===========================================================================
  // leaves (sinks)
  // ===========================================================================

  @Override
  public boolean isLeaf(Id id) {
    return idGraph.isLeaf(id);
  }

  // ===================================

  @Override
  public ImmutableSet<Id> leafIdSet() {
    return idGraph.leafIdSet();
  }

  // ===================================

  @Override
  public ImmutableSet<Node> leafNodeSet() {
    if (leafNodes == null)
      leafNodes = transformSet(leafIdSet(), false);
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
        optionalTopsortNodeList = Optional.of(transformList(optionalTopsortIdList().get(), false));
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
  public Iterable<Id> traverseIdIterable(boolean depthFirst, boolean inclusive,
      ImmutableList<Id> startIds, Fn1<Id, List<Id>> expand) {

    return idGraph.traverseIdIterable(depthFirst, inclusive, startIds, expand);
  }

  @Override
  public Iterable<Id> traverseIdIterable(boolean depthFirst, boolean inclusive, Id startId,
      Fn1<Id, List<Id>> expand) {

    return idGraph.traverseIdIterable(depthFirst, inclusive, startId, expand);
  }

  @Override
  public ImmutableList<Id> traverseIdList(boolean depthFirst, boolean inclusive,
      ImmutableList<Id> startIds, Fn1<Id, List<Id>> expand) {

    return idGraph.traverseIdList(depthFirst, inclusive, startIds, expand);
  }

  @Override
  public ImmutableList<Id> traverseIdList(boolean depthFirst, boolean inclusive, Id startId,
      Fn1<Id, List<Id>> expand) {

    return idGraph.traverseIdList(depthFirst, inclusive, startId, expand);
  }

  // ===================================

  @Override
  public Iterable<Node> traverseNodeIterable(boolean depthFirst, boolean inclusive, Id startId,
      Fn1<Node, List<Id>> expand) {

    return traverseNodeIterable(depthFirst, inclusive, ImmutableList.of(startId), expand);
  }

  @Override
  public Iterable<Node> traverseNodeIterable(boolean depthFirst, boolean inclusive,
      ImmutableList<Id> startIds, Fn1<Node, List<Id>> expand) {

    return TraverseLib.nodeIterable(depthFirst, inclusive, startIds, expand, nodeLambda());
  }

  @Override
  public ImmutableList<Node> traverseNodeList(boolean depthFirst, boolean inclusive, Id startId,
      Fn1<Node, List<Id>> expand) {

    return traverseNodeList(depthFirst, inclusive, ImmutableList.of(startId), expand);
  }

  @Override
  public ImmutableList<Node> traverseNodeList(boolean depthFirst, boolean inclusive,
      ImmutableList<Id> startIds, Fn1<Node, List<Id>> expand) {

    return ImmutableList.copyOf(traverseNodeIterable(depthFirst, inclusive, startIds, expand));
  }


  // ===========================================================================
  // transformations from ids to nodes
  // ===========================================================================

  @Override
  public ImmutableSet<Node> transformSet(Iterable<Id> ids) {
    return transformSet(ids, false);
  }

  @Override
  public ImmutableSet<Node> transformSet(Iterable<Id> ids, boolean skipMissingNodes) {

    ImmutableSet.Builder<Node> nodeSet = ImmutableSet.builder();

    for (Id id : ids)
      if (!containsNodeForId(id))
        if (skipMissingNodes)
          continue;
        else
          throw new NotContained("graph does not have node with id = %s", id);
      else
        nodeSet.add(id__node.get(id));

    return nodeSet.build();
  }

  // ===================================

  @Override
  public ImmutableList<Node> transformList(Iterable<Id> ids) {
    return transformList(ids, false);
  }

  @Override
  public ImmutableList<Node> transformList(Iterable<Id> ids, boolean skipMissingNodes) {

    ImmutableList.Builder<Node> nodeList = ImmutableList.builder();

    for (Id id : ids)
      if (!containsNodeForId(id))
        if (skipMissingNodes)
          continue;
        else
          throw new NotContained("graph does not have node with id = %s", id);
      else
        nodeList.add(id__node.get(id));

    return nodeList.build();
  }

  // ===================================

  @Override
  public Optional<Node> transformOptional(Optional<Id> id) {
    return transformOptional(id, false);
  }

  @Override
  public Optional<Node> transformOptional(Optional<Id> optionalId, boolean skipMissingNode) {

    if (!optionalId.isPresent())
      return Optional.absent();

    Id id = optionalId.get();

    if (!containsNodeForId(id))
      if (skipMissingNode)
        return Optional.absent();
      else
        throw new NotContained("graph does not have node with id = %s", id);

    return Optional.of(getNode(id));
  }

  // ===================================

  @Override
  public Iterable<Node> transformIterable(Iterable<Id> idIterable) {
    return transformIterable(idIterable, false);
  }

  @Override
  public Iterable<Node> transformIterable(final Iterable<Id> idIterable,
      final boolean skipMissingNodes) {

    return new Iterable<Node>() {
      @Override
      public Iterator<Node> iterator() {
        return transformIterator(idIterable.iterator(), skipMissingNodes);
      }
    };
  }

  // ===================================

  @Override
  public Iterator<Node> transformIterator(Iterator<Id> idIterator) {
    return transformIterator(idIterator, false);
  }

  @Override
  public Iterator<Node> transformIterator(final Iterator<Id> idIterator,
      final boolean skipMissingNodes) {

    // Iterators.transform doesn't look like it will work because of the skipMissingNodes flag

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

        Node node = getNode(nextId);

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

          if (!containsNodeForId(id))
            if(skipMissingNodes)
              continue;
            else
              throw new NotContained("graph does not have node with id = %s", id);

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
