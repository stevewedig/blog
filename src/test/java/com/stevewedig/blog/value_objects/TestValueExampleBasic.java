package com.stevewedig.blog.value_objects;

import org.junit.Test;


public class TestValueExampleBasic {

  static boolean LOOK_AT_PRINTING = false;

  @Test
  public void testWithValueMixin() {

    Point point1 = new PointClassWithValueMixin(2, 3);
    Point point2 = new PointClassWithValueMixin(2, 3);
    CompareLib.assertSameValueAndSameString(point1, point2);

    Point point3 = new PointClassWithoutValueMixin(2, 3);
    Point point4 = new PointClassWithoutValueMixin(2, 3);
    CompareLib.assertSameValueAndSameString(point3, point4);
    
    CompareLib.assertDifferentValueAndDifferentString(point1, point3);
    
  }

}
