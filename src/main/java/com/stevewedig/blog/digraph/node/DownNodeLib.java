package com.stevewedig.blog.digraph.node;

import com.google.common.collect.*;
import com.stevewedig.blog.digraph.errors.NodeIdConflict;
import com.stevewedig.blog.digraph.id_graph.*;

/**
 * A library with tools for manipulating DownNodes.
 */
public abstract class DownNodeLib {

  // ===========================================================================
  // creating nodes
  // ===========================================================================

  public static <Id> DownNode<Id> downNode(Id id, Iterable<Id> parentIds) {
    return new DownNodeClass<Id>(id, ImmutableSet.copyOf(parentIds));
  }

  @SafeVarargs
  public static <Id> DownNode<Id> downNode(Id id, Id... parentIds) {
    return downNode(id, ImmutableSet.copyOf(parentIds));
  }

  // ===========================================================================
  // nodeIter -> idSet
  // ===========================================================================

  public static <Id, Node extends DownNode<Id>> ImmutableSet<Id> node__ids(Iterable<Node> nodes) {

    ImmutableSet.Builder<Id> ids = ImmutableSet.builder();

    for (Node node : nodes) {
      ids.add(node.id());
      ids.addAll(node.childIds());
    }

    return ids.build();
  }

  // ===========================================================================
  // nodeIter -> parentMap
  // ===========================================================================

  public static <Id, Node extends DownNode<Id>> ImmutableSetMultimap<Id, Id> nodes__parentMap(
      Iterable<Node> nodes) {

    ImmutableSetMultimap.Builder<Id, Id> id__parentIds = ImmutableSetMultimap.builder();

    for (Node node : nodes) {

      Id parentId = node.id();

      for (Id id : node.childIds())
        id__parentIds.put(id, parentId);
    }

    return id__parentIds.build();
  }

  // ===========================================================================
  // nodeSet -> nodeMap
  // ===========================================================================

  public static <Id, Node extends DownNode<Id>> ImmutableBiMap<Id, Node> nodes__nodeMap(
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
  // nodeSet -> idGraph, idDag, idTree
  // ===========================================================================

  public static <Id, Node extends DownNode<Id>> IdGraph<Id> nodes__idGraph(Iterable<Node> nodes) {

    ImmutableSet<Id> ids = node__ids(nodes);

    ImmutableSetMultimap<Id, Id> id__parentIds = nodes__parentMap(nodes);

    return IdGraphLib.fromParentMap(ids, id__parentIds);
  }

  public static <Id, Node extends DownNode<Id>> IdDag<Id> nodes__idDag(ImmutableSet<Node> nodes) {

    ImmutableSet<Id> ids = node__ids(nodes);

    ImmutableSetMultimap<Id, Id> id__parentIds = nodes__parentMap(nodes);

    return IdDagLib.fromParentMap(ids, id__parentIds);
  }

  public static <Id, Node extends DownNode<Id>> IdTree<Id> nodes__idTree(ImmutableSet<Node> nodes) {

    ImmutableSet<Id> ids = node__ids(nodes);

    ImmutableSetMultimap<Id, Id> id__parentIds = nodes__parentMap(nodes);

    return IdTreeLib.fromParentMap(ids, id__parentIds);
  }

}
