package com.stevewedig.blog.digraph;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.digraph.id_graph.*;

public class TestExampleCategoryTree {

  // TODO this example is in progress

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

  // this will raise an error if we add a Category without adding it to the tree
  static {
    idTree.assertIdsMatch(Category.values());
  }

  // ===========================================================================

  public static Category mostSpecific(Set<Category> categories) {
    return idTree.mostDeep(categories);
  }

  // ===========================================================================

  // TODO inChildren
  public static boolean isSubcategory(Category child, Category parent) {
    return idTree.descendantOf(child, parent, true);
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

  // TODO show the idTree vs nodeTree approaches

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
