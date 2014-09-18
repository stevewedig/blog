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
  // static category related methods
  // ===========================================================================

  /**
   * Is a category below another one, inclusively? (Similar to the "issubclass" operator.)
   */
  public static boolean isSubcategory(Category category, Category potentialAncestor) {
    return tree.descendantOf(category, potentialAncestor, true);
  }

  /**
   * Find the most specific category (non-deterministic when they are equally deep).
   */
  public static Category mostSpecific(Set<Category> categories) {
    return tree.mostDeep(categories);
  }

  /**
   * Create a smaller tree containing only the categories below the provided category.
   */
  public static IdTree<Category> subtree(Category category) {

    ImmutableSet<Category> subtreeIds = tree.descendantIdSet(category, true);

    SetMultimap<Category, Category> subtreeChildMap = tree.filterChildMap(subtreeIds);

    return IdTreeLib.fromChildMap(subtreeIds, subtreeChildMap);
  }

  /**
   * Create a smaller tree containing only the categories above the provided categories.
   */
  public static IdTree<Category> supertree(Category... categories) {

    ImmutableSet<Category> supertreeIds = tree.ancestorIdSet(ImmutableSet.copyOf(categories), true);

    SetMultimap<Category, Category> subtreeChildMap = tree.filterChildMap(supertreeIds);

    return IdTreeLib.fromChildMap(supertreeIds, subtreeChildMap);
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
  public void testSubtree() {

    // primate is below mammal
    assertEquals(IdTreeLib.fromChildMap(Category.mammal, Category.primate),
        subtree(Category.mammal));

    // everything is below the root
    assertEquals(tree, subtree(Category.animal));

    // nothing is below a leaf
    assertEquals(IdTreeLib.fromChildMap(ImmutableSet.of(Category.primate)),
        subtree(Category.primate));
  }

  @Test
  public void testSupertree() {

    // animal is above mammal
    assertEquals(IdTreeLib.fromChildMap(Category.animal, Category.mammal),
        supertree(Category.mammal));

    // animal and mammal are above primate
    assertEquals(
        IdTreeLib.fromChildMap(Category.animal, Category.mammal, Category.mammal, Category.primate),
        supertree(Category.primate));

    // everything is above the leaves
    assertEquals(tree, supertree(Category.primate, Category.lizard));

    // nothing is above the root
    assertEquals(IdTreeLib.fromChildMap(ImmutableSet.of(Category.animal)),
        supertree(Category.animal));

  }

}
