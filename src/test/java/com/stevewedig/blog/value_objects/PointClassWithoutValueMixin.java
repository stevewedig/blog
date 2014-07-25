package com.stevewedig.blog.value_objects;

import com.stevewedig.blog.util.ArrayLib;
import com.stevewedig.blog.value_objects.HasObjectHelper;
import com.stevewedig.blog.value_objects.ObjectHelper;
import com.stevewedig.blog.value_objects.ObjectHelperLib;



class PointClassWithoutValueMixin implements Point, HasObjectHelper {
  private final int x;
  private final int y;

  public PointClassWithoutValueMixin(int x, int y) {
    super();
    this.x = x;
    this.y = y;
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  // ===========================================================================
  // helper (mostly copied from ObjectMixin & EntityMixin)
  // ===========================================================================

  protected Object[] fields() {
    return ArrayLib.array("x", x, "y", y);
  }

  // field values don't change, so this can be cached
  @Override
  public ObjectHelper objectHelper() {
    if (cachedHelper == null)
      cachedHelper = ObjectHelperLib.helper(getClass(), fields());
    return cachedHelper;
  }

  private ObjectHelper cachedHelper;

  @Override
  public String toString() {
    return objectHelper().objectString();
  }

  // ===========================================================================
  // delegate to helper
  // ===========================================================================

  @Override
  public boolean equals(Object other) {
    return objectHelper().classAndStateEquals(other);
  }

  @Override
  public int hashCode() {
    return objectHelper().classAndStateHash();
  }

}
