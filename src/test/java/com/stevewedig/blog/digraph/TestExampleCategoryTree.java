package com.stevewedig.blog.digraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import com.stevewedig.blog.digraph.id_graph.IdTree;
import com.stevewedig.blog.digraph.id_graph.IdTreeLib;

public class TestExampleCategoryTree {

  // ===========================================================================
  // category enum (the id set)
  // ===========================================================================

  static enum Category {

    animal, mammal, primate, reptile, lizard
  }

  // ===========================================================================
  // static category tree (an id tree)
  // ===========================================================================

  // "child map" means mapping from parent -> children
  static IdTree<Category> tree = IdTreeLib.fromChildMap(Category.animal, Category.mammal,
      Category.mammal, Category.primate, Category.animal, Category.reptile, Category.reptile,
      Category.lizard);

  // an AssertionError will be raised if we create a Category without adding it to the tree
  static {
    tree.assertIdsMatch(Category.values());
  }

  // ===========================================================================
  // static category releated methods
  // ===========================================================================

  /**
   * similar to the "issubclass" operator
   */
  public static boolean isSubcategory(Category child, Category parent) {
    return tree.descendantOf(child, parent, true);
  }

  /**
   * non-deterministic choice when two categories are equally deep
   */
  public static Category mostSpecific(Set<Category> categories) {
    return tree.mostDeep(categories);
  }

  /**
   * create a smaller tree containing the nodes below the provided category
   */
  public static IdTree<Category> subTree(Category category) {

    ImmutableSet<Category> idsInSubTree = tree.descendantIdSet(category, true);

    SetMultimap<Category, Category> subtreeParentMap = tree.filterParentMap(idsInSubTree);

    return IdTreeLib.fromParentMap(idsInSubTree, subtreeParentMap);
  }

  /**
   * create a smaller tree containing the nodes above the provided categories
   */
  public static IdTree<Category> superTree(Category... categories) {

    ImmutableSet<Category> idsInSuperTree =
        tree.ancestorIdSet(ImmutableSet.copyOf(categories), true);

    SetMultimap<Category, Category> subtreeParentMap = tree.filterParentMap(idsInSuperTree);

    return IdTreeLib.fromParentMap(idsInSuperTree, subtreeParentMap);
  }

  // ===========================================================================
  // tests
  // ===========================================================================

  @Test
  public void testTreeStructure() {

    assertEquals(Category.animal, tree.rootId());

    assertEquals(ImmutableSet.of(Category.primate, Category.lizard), tree.leafIdSet());

  }

  @Test
  public void testIsSubcategory() {

    assertTrue(isSubcategory(Category.animal, Category.animal));
    assertTrue(isSubcategory(Category.mammal, Category.animal));
    assertTrue(isSubcategory(Category.primate, Category.mammal));
    assertTrue(isSubcategory(Category.primate, Category.animal));

    assertFalse(isSubcategory(Category.animal, Category.mammal));
    assertFalse(isSubcategory(Category.mammal, Category.primate));
    assertFalse(isSubcategory(Category.reptile, Category.mammal));

  }

  @Test
  public void testMostSpecific() {

    assertEquals(Category.animal, mostSpecific(ImmutableSet.of(Category.animal)));

    assertEquals(Category.mammal, mostSpecific(ImmutableSet.of(Category.animal, Category.mammal)));

    assertEquals(Category.primate, mostSpecific(ImmutableSet.of(Category.animal, Category.mammal,
        Category.primate, Category.reptile)));

  }

  @Test
  public void testSubTree() {

    // primate is below mammal
    assertEquals(IdTreeLib.fromChildMap(Category.mammal, Category.primate),
        subTree(Category.mammal));

    // everything is below the root
    assertEquals(tree, subTree(Category.animal));

    // nothing is below a leaf
    assertEquals(IdTreeLib.fromChildMap(ImmutableSet.of(Category.primate)),
        subTree(Category.primate));
  }

  @Test
  public void testSuperTree() {

    // animal is above mammal
    assertEquals(IdTreeLib.fromChildMap(Category.animal, Category.mammal),
        superTree(Category.mammal));

    // animal and mammal are above primate
    assertEquals(
        IdTreeLib.fromChildMap(Category.animal, Category.mammal, Category.mammal, Category.primate),
        superTree(Category.primate));

    // everything is above the leaves
    assertEquals(tree, superTree(Category.primate, Category.lizard));

    // nothing is above the root
    assertEquals(IdTreeLib.fromChildMap(ImmutableSet.of(Category.animal)),
        superTree(Category.animal));

  }

}
