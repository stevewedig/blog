package com.stevewedig.blog.digraph;

import java.util.Set;

import org.junit.Test;

import com.stevewedig.blog.digraph.id_graph.*;
import com.stevewedig.blog.util.*;

public class TestExampleCategoryTree {

  // ===========================================================================
  // Category enum
  // ===========================================================================

  static enum Category {

    animal,

    mammal, primate,

    reptile
  }

  // ===========================================================================

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
  public static boolean contains(Category parent, Category child) {

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
  public void testExampleCategoryTree() {

  }
}
