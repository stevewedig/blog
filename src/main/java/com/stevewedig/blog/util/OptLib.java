package com.stevewedig.blog.util;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.stevewedig.blog.util.LambdaLib.Fn0;

public abstract class OptLib {

  public static <A, B> boolean isPresentXor(Optional<A> a, Optional<B> b) {    
    return LogicLib.xor(a.isPresent(), b.isPresent());
  }

  @SafeVarargs
  public static <Value> boolean isPresentXor(Optional<Value>... items) {
    
    Predicate<Optional<Value>> pred = isPresentFn();
    
    return LogicLib.xor(pred, items);
  }

  public static <Value> Predicate<Optional<Value>> isPresentFn() {
    return new Predicate<Optional<Value>>() {
      @Override
      public boolean apply(Optional<Value> value) {
        return value.isPresent();
      }
    };
 }

  public static <Value> Fn0<Optional<Value>> absentFn() {
    return new Fn0<Optional<Value>>() {
      @Override
      public Optional<Value> apply() {
        return Optional.absent();
      }
    };
  }
}
