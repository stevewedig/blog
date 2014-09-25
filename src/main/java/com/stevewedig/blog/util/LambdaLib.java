package com.stevewedig.blog.util;

import com.google.common.base.Function;

/**
 * Interfaces for creating anonymous lambdas (callbacks).
 */
public abstract class LambdaLib {

  // ===========================================================================
  // Fn (has output)
  // ===========================================================================

  /**
   * A function lambda with 0 inputs and an output.
   */
  public static interface Fn0<Out> {
    Out apply();
  }

  // extends Function, so you can use Fn1 wherever Guava Function
  /**
   * A function lambda with 1 input and an output.
   */
  public static interface Fn1<A, Out> extends Function<A, Out> {
    @Override
    Out apply(A a);
  }

  /**
   * A function lambda with 2 inputs and an output.
   */
  public static interface Fn2<A, B, Out> {
    Out apply(A a, B b);
  }

  /**
   * A function lambda with 3 inputs and an output.
   */
  public static interface Fn3<A, B, C, Out> {
    Out apply(A a, B b, C c);
  }

  /**
   * A function lambda with 4 inputs and an output.
   */
  public static interface Fn4<A, B, C, D, Out> {
    Out apply(A a, B b, C c, D d);
  }

  /**
   * A function lambda with 5 inputs and an output.
   */
  public static interface Fn5<A, B, C, D, E, Out> {
    Out apply(A a, B b, C c, D d, E e);
  }

  /**
   * A function lambda with 6 inputs and an output.
   */
  public static interface Fn6<A, B, C, D, E, F, Out> {
    Out apply(A a, B b, C c, D d, E e, F f);
  }

  /**
   * A function lambda with 7 inputs and an output.
   */
  public static interface Fn7<A, B, C, D, E, F, G, Out> {
    Out apply(A a, B b, C c, D d, E e, F f, G g);
  }

  /**
   * A function lambda with 8 inputs and an output.
   */
  public static interface Fn8<A, B, C, D, E, F, G, H, Out> {
    Out apply(A a, B b, C c, D d, E e, F f, G g, H h);
  }

  /**
   * A function lambda with 9 inputs and an output.
   */
  public static interface Fn9<A, B, C, D, E, F, G, H, I, Out> {
    Out apply(A a, B b, C c, D d, E e, F f, G g, H h, I i);
  }

  // ===========================================================================
  // Act (no output)
  // ===========================================================================

  /**
   * An action lambda with 0 inputs and no output.
   */
  public static interface Act0 {
    void apply();
  }

  /**
   * An action lambda with 1 input and no output.
   */
  public static interface Act1<A> {
    void apply(A a);
  }

  /**
   * An action lambda with 2 inputs and no output.
   */
  public static interface Act2<A, B> {
    void apply(A a, B b);
  }

  /**
   * An action lambda with 3 inputs and no output.
   */
  public static interface Act3<A, B, C> {
    void apply(A a, B b, C c);
  }

  /**
   * An action lambda with 4 inputs and no output.
   */
  public static interface Act4<A, B, C, D> {
    void apply(A a, B b, C c, D d);
  }

  /**
   * An action lambda with 5 inputs and no output.
   */
  public static interface Act5<A, B, C, D, E> {
    void apply(A a, B b, C c, D d, E e);
  }

  /**
   * An action lambda with 6 inputs and no output.
   */
  public static interface Act6<A, B, C, D, E, F> {
    void apply(A a, B b, C c, D d, E e, F f);
  }

  /**
   * An action lambda with 7 inputs and no output.
   */
  public static interface Act7<A, B, C, D, E, F, G> {
    void apply(A a, B b, C c, D d, E e, F f, G g);
  }

  /**
   * An action lambda with 8 inputs and no output.
   */
  public static interface Act8<A, B, C, D, E, F, G, H> {
    void apply(A a, B b, C c, D d, E e, F f, G g, H h);
  }

  /**
   * An action lambda with 9 inputs and no output.
   */
  public static interface Act9<A, B, C, D, E, F, G, H, I> {
    void apply(A a, B b, C c, D d, E e, F f, G g, H h, I i);
  }

}
