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

    animal, mammal, primate, reptile
  }

  // ===========================================================================
  // id tree
  // ===========================================================================

  // parent map means mapping from child -> parent(s)
  static IdTree<Category> tree = IdTreeLib.fromParentMap(Category.mammal, Category.animal,
      Category.primate, Category.mammal, Category.reptile, Category.animal);

  // this will raise an error if we add a Category without adding it to the tree
  static {
    tree.assertIdsMatch(Category.values());
  }

  // ===========================================================================
  // id tree methods
  // ===========================================================================

  public static boolean isSubcategory(Category child, Category parent) {
    return tree.descendantOf(child, parent, true);
  }

  public static Category mostSpecific(Set<Category> categories) {
    return tree.mostDeep(categories);
  }

  public static Set<Category> addSupercategories(Set<Category> categories) {

    // return tree.ancestorIdSet(categories, true);
    
    // otherCategories = Sets.newHashSet(otherCategories);

    // TODO not right but not bothering, need hierarchy and cateogry objects
    return categories;
  }

  // ===========================================================================
  // tests
  // ===========================================================================

  @Test
  public void testSubcategory() {

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
}
