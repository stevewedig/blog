package com.stevewedig.blog.digraph;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.digraph.id_graph.*;
import com.stevewedig.blog.util.CollectLib;

public class TestExampleCategoryIdTree {

  // ===========================================================================
  // enum
  // ===========================================================================

  static enum Category {

    animal, mammal, primate, reptile
  }

  // ===========================================================================
  // tree
  // ===========================================================================

  private static IdTree<Category> idTree = IdTreeLib.fromParentMap(Category.mammal,
      Category.animal, Category.primate, Category.mammal, Category.reptile, Category.animal);

  // TODO assertion
  // todo compare ids?
  static {
    if (idTree.idSize() != Category.values().length)
      throw new RuntimeException("category tree is missing items");
  }

  // ===========================================================================

  // TODO deepest
  public static Category mostSpecific(Set<Category> categories) {

    CollectLib.assertNotEmpty(categories);

    Category specificCat = null;
    Integer specificDepth = null;

    for (Category category : categories) {
      int depth = idTree.depth(category);

      if (specificCat == null || depth > specificDepth) {
        specificCat = category;
        specificDepth = depth;
      }
    }

    return specificCat;
  }

  // ===========================================================================

  // TODO inChildren
  public static boolean isSubcategory(Category child, Category parent) {

    if (parent.equals(child))
      return true;

    if (idTree.ancestorIdSet(child).contains(parent))
      return true;

    return false;
  }

  // ===========================================================================

  // TODO fill in ancestors?
  public static Set<Category> expandParents(Set<Category> otherCategories) {

    // otherCategories = Sets.newHashSet(otherCategories);

    // TODO not right but not bothering, need hierarchy and cateogry objects
    return otherCategories;
  }

  // ===========================================================================
  // test
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
  public void testDeepest() {

    assertEquals(Category.animal, mostSpecific(ImmutableSet.of(Category.animal)));

    assertEquals(Category.mammal, mostSpecific(ImmutableSet.of(Category.animal, Category.mammal)));

    assertEquals(Category.primate, mostSpecific(ImmutableSet.of(Category.animal, Category.mammal,
        Category.primate, Category.reptile)));
  }
}
