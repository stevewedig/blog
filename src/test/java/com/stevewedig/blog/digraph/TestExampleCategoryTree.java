package com.stevewedig.blog.digraph;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.digraph.id_graph.*;

public class TestExampleCategoryTree {

  // ===========================================================================
  // enum
  // ===========================================================================

  static enum Category {

    animal, mammal, primate, reptile, lizard
  }

  // ===========================================================================
  // static category tree (an id tree)
  // ===========================================================================

  // "parent map" means mapping from child -> parent(s)
  static IdTree<Category> tree = IdTreeLib.fromParentMap(Category.mammal, Category.animal,
      Category.primate, Category.mammal, Category.reptile, Category.animal, Category.lizard,
      Category.reptile);

  // an AssertionError will be raised if we create a Category without adding it to the tree
  static {
    tree.assertIdsMatch(Category.values());
  }

  // ===========================================================================
  // static category methods
  // ===========================================================================

  // similar to the "issubclass" operator
  public static boolean isSubcategory(Category child, Category parent) {
    return tree.descendantOf(child, parent, true);
  }

  // non-deterministic choice when two categories are equally deep
  public static Category mostSpecific(Set<Category> categories) {
    return tree.mostDeep(categories);
  }

  // subtree // TODO impl subtree method
  public static Set<Category> addSupercategories(Set<Category> categories) {
    return tree.ancestorIdSet(categories, true);
  }

  // ===========================================================================
  // tests
  // ===========================================================================

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
  public void testAddSupercategories() {

    assertEquals(ImmutableSet.of(Category.animal),
        addSupercategories(ImmutableSet.of(Category.animal)));

    assertEquals(ImmutableSet.of(Category.animal, Category.mammal, Category.primate),
        addSupercategories(ImmutableSet.of(Category.primate)));

    assertEquals(ImmutableSet.of(Category.animal, Category.mammal, Category.primate,
        Category.reptile, Category.lizard), addSupercategories(ImmutableSet.of(Category.primate,
        Category.lizard)));

  }

}
