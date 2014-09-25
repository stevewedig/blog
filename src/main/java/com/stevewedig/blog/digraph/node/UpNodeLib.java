package com.stevewedig.blog.digraph.node;

import com.google.common.collect.*;
import com.stevewedig.blog.digraph.errors.NodeIdConflict;
import com.stevewedig.blog.digraph.id_graph.*;

/**
 * A library with tools for manipulating UpNodes.
 */
public abstract class UpNodeLib {

  // ===========================================================================
  // creating nodes
  // ===========================================================================

  /**
   * Create an UpNode from its id and a parent id iterable.
   */
  public static <Id> UpNode<Id> upNode(Id id, Iterable<Id> parentIds) {
    return new UpNodeClass<Id>(id, ImmutableSet.copyOf(parentIds));
  }

  /**
   * Create an UpNode from its id and a parent id varargs array.
   */
  @SafeVarargs
  public static <Id> UpNode<Id> upNode(Id id, Id... parentIds) {
    return upNode(id, ImmutableSet.copyOf(parentIds));
  }

  // ===========================================================================
  // nodes -> ids
  // ===========================================================================

  /**
   * Collect all of the ids and parent ids in an UpNode iterable.
   */
  public static <Id, Node extends UpNode<Id>> ImmutableSet<Id> nodes__ids(Iterable<Node> nodes) {

    ImmutableSet.Builder<Id> ids = ImmutableSet.builder();

    for (Node node : nodes) {
      ids.add(node.id());
      ids.addAll(node.parentIds());
    }

    return ids.build();
  }

  // ===========================================================================
  // nodes -> parentMap
  // ===========================================================================

  /**
   * Convert an UpNode iterable into a mapping from id to parent ids.
   */
  public static <Id, Node extends UpNode<Id>> ImmutableSetMultimap<Id, Id> nodes__parentMap(
      Iterable<Node> nodes) {

    ImmutableSetMultimap.Builder<Id, Id> id__parentIds = ImmutableSetMultimap.builder();

    for (Node node : nodes) {
      id__parentIds.putAll(node.id(), node.parentIds());
    }

    return id__parentIds.build();
  }

  // ===========================================================================
  // nodes -> nodeMap
  // ===========================================================================

  /**
   * Convert an UpNode set into a mapping between id and node.
   */
  public static <Id, Node extends UpNode<Id>> ImmutableBiMap<Id, Node> nodes__nodeMap(
      ImmutableSet<Node> nodes) {

    BiMap<Id, Node> id__node = HashBiMap.create();

    for (Node node : nodes) {

      if (id__node.containsKey(node.id()))
        throw new NodeIdConflict("id = %s", node.id());

      id__node.put(node.id(), node);
    }

    return ImmutableBiMap.copyOf(id__node);
  }

  // ===========================================================================
  // nodes -> idGraph, idDag, idTree
  // ===========================================================================

  /**
   * Create an IdGraph from an UpNode iterable.
   */
  public static <Id, Node extends UpNode<Id>> IdGraph<Id> nodes__idGraph(Iterable<Node> nodes) {

    ImmutableSet<Id> ids = nodes__ids(nodes);

    ImmutableSetMultimap<Id, Id> id__parentIds = nodes__parentMap(nodes);

    return IdGraphLib.fromParentMap(ids, id__parentIds);
  }

  /**
   * Create an IdDag from an UpNode iterable.
   */
  public static <Id, Node extends UpNode<Id>> IdDag<Id> nodes__idDag(ImmutableSet<Node> nodes) {

    ImmutableSet<Id> ids = nodes__ids(nodes);

    ImmutableSetMultimap<Id, Id> id__parentIds = nodes__parentMap(nodes);

    return IdDagLib.fromParentMap(ids, id__parentIds);
  }

  /**
   * Create an IdTree from an UpNode iterable.
   */
  public static <Id, Node extends UpNode<Id>> IdTree<Id> nodes__idTree(ImmutableSet<Node> nodes) {

    ImmutableSet<Id> ids = nodes__ids(nodes);

    ImmutableSetMultimap<Id, Id> id__parentIds = nodes__parentMap(nodes);

    return IdTreeLib.fromParentMap(ids, id__parentIds);
  }

}
