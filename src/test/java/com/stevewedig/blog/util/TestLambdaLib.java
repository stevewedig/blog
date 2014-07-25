package com.stevewedig.blog.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;
import com.stevewedig.blog.util.LambdaLib.Act1;
import com.stevewedig.blog.util.LambdaLib.Fn1;
import com.stevewedig.blog.util.LambdaLib.Fn2;

public class TestLambdaLib {

  @Test
  public void testFn() {

    Fn2<Integer, Integer, Integer> add = new Fn2<Integer, Integer, Integer>() {
      @Override
      public Integer apply(Integer a, Integer b) {
        return a + b;
      }
    };

    assertEquals((Integer) 3, add.apply(1, 2));

  }

  @Test
  public void testAct() {

    final List<Integer> items = new ArrayList<>();

    Act1<Integer> add = new Act1<Integer>() {
      @Override
      public void apply(Integer item) {
        items.add(item);
      }
    };

    assertTrue(items.isEmpty());

    add.apply(10);

    assertEquals((Integer) 10, items.get(0));
  }

  @Test
  public void testFn1IsGuavaFunction() {

    Fn1<Integer, String> fn = null;

    @SuppressWarnings("unused")
    Function<Integer, String> function = fn;

  }
}
