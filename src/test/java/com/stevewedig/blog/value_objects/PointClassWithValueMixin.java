package com.stevewedig.blog.value_objects;

import com.stevewedig.blog.value_objects.ValueMixin;



class PointClassWithValueMixin extends ValueMixin implements Point {
  private final int x;
  private final int y;

  public PointClassWithValueMixin(int x, int y) {
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
  // helper
  // ===========================================================================

  @Override
  protected Object[] fields() {
    return array("x", x, "y", y);
  }

}
