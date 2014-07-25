package com.stevewedig.blog.util;

import com.google.common.base.Function;


public abstract class LambdaLib {

  // ===========================================================================
  // Fn (has output)
  // ===========================================================================

  public static interface Fn0<Out> {
    Out apply();
  }

  // can use Fn1 wherever Guava expects Function
  public static interface Fn1<A, Out> extends Function<A, Out> {
    @Override
    Out apply(A a);
  }

  public static interface Fn2<A, B, Out> {
    Out apply(A a, B b);
  }

  public static interface Fn3<A, B, C, Out> {
    Out apply(A a, B b, C c);
  }

  public static interface Fn4<A, B, C, D, Out> {
    Out apply(A a, B b, C c, D d);
  }

  public static interface Fn5<A, B, C, D, E, Out> {
    Out apply(A a, B b, C c, D d, E e);
  }

  public static interface Fn6<A, B, C, D, E, F, Out> {
    Out apply(A a, B b, C c, D d, E e, F f);
  }

  public static interface Fn7<A, B, C, D, E, F, G, Out> {
    Out apply(A a, B b, C c, D d, E e, F f, G g);
  }

  public static interface Fn8<A, B, C, D, E, F, G, H, Out> {
    Out apply(A a, B b, C c, D d, E e, F f, G g, H h);
  }

  public static interface Fn9<A, B, C, D, E, F, G, H, I, Out> {
    Out apply(A a, B b, C c, D d, E e, F f, G g, H h, I i);
  }

  // ===========================================================================
  // Act (no output)
  // ===========================================================================

  public static interface Act0 {
    void apply();
  }
  public static interface Act1<A> {
    void apply(A a);
  }

  public static interface Act2<A, B> {
    void apply(A a, B b);
  }

  public static interface Act3<A, B, C> {
    void apply(A a, B b, C c);
  }

  public static interface Act4<A, B, C, D> {
    void apply(A a, B b, C c, D d);
  }

  public static interface Act5<A, B, C, D, E> {
    void apply(A a, B b, C c, D d, E e);
  }

  public static interface Act6<A, B, C, D, E, F> {
    void apply(A a, B b, C c, D d, E e, F f);
  }

  public static interface Act7<A, B, C, D, E, F, G> {
    void apply(A a, B b, C c, D d, E e, F f, G g);
  }

  public static interface Act8<A, B, C, D, E, F, G, H> {
    void apply(A a, B b, C c, D d, E e, F f, G g, H h);
  }

  public static interface Act9<A, B, C, D, E, F, G, H, I> {
    void apply(A a, B b, C c, D d, E e, F f, G g, H h, I i);
  }

}
