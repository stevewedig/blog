package com.stevewedig.blog.symbol;

import com.stevewedig.blog.util.LambdaLib.Act0;
import com.stevewedig.blog.util.LambdaLib.Act1;
import com.stevewedig.blog.util.LambdaLib.Act2;
import com.stevewedig.blog.util.LambdaLib.Act3;
import com.stevewedig.blog.util.LambdaLib.Act4;
import com.stevewedig.blog.util.LambdaLib.Act5;
import com.stevewedig.blog.util.LambdaLib.Act6;
import com.stevewedig.blog.util.LambdaLib.Fn0;
import com.stevewedig.blog.util.LambdaLib.Fn1;
import com.stevewedig.blog.util.LambdaLib.Fn2;
import com.stevewedig.blog.util.LambdaLib.Fn3;
import com.stevewedig.blog.util.LambdaLib.Fn4;
import com.stevewedig.blog.util.LambdaLib.Fn5;
import com.stevewedig.blog.util.LambdaLib.Fn6;

public abstract class SymbolLambdaLib {

  // ===========================================================================
  // lambda interfaces
  // ===========================================================================

  public interface Fn<Output> extends Fn1<SymbolMap, Output> {
    SymbolSchema inputSchema();
  }

  public interface Act extends Act1<SymbolMap> {
    SymbolSchema inputSchema();
  }

  /*
   * ===========================================================================
   * 
   * Below here there be dragons.
   * 
   * These are generic methods for converting lambdas (from LambdaLib) into Fn nodes. The generic
   * type paramters make something conceptually simple into something syntactically unreadable. We
   * are using the type system to statically check that inputs and outputs of the provided lambda
   * match the input ids and output id.
   * 
   * To temporarily improve readability, you can replace this regex \<[^>]*> with an empty string,
   * to remove the generics chartjunk.
   * 
   * The test for this behavior are in TestFnTypeChecking.java
   * 
   * ===========================================================================
   */

  // ===========================================================================
  // lambda mixins
  // ===========================================================================

  private static abstract class FnMixin<Out> implements Fn<Out> {

    private final SymbolSchema inputSchema;

    public FnMixin(Symbol<?>... symbols) {
      super();
      this.inputSchema = SymbolLib.schema(symbols);
    }

    @Override
    public SymbolSchema inputSchema() {
      return inputSchema;
    }
  }

  private static abstract class ActMixin implements Act {

    private final SymbolSchema inputSchema;

    public ActMixin(Symbol<?>... symbols) {
      super();
      this.inputSchema = SymbolLib.schema(symbols);
    }

    @Override
    public SymbolSchema inputSchema() {
      return inputSchema;
    }
  }

  // ===========================================================================
  // lambda adapters
  // ===========================================================================

  // ===================================
  // fn
  // ===================================

  public static <Out, $Out extends Out> Fn<Out> fn(final Fn0<$Out> fn0) {
    return new FnMixin<Out>() {
      @Override
      public Out apply(SymbolMap params) {
        inputSchema().validate(params);
        $Out $out = fn0.apply();
        return $out;
      }
    };
  };

  public static <Out, $Out extends Out, $A, A extends $A> Fn<Out> fn(final Fn1<$A, $Out> fn1,
      final Symbol<A> a) {
    return new FnMixin<Out>(a) {
      @Override
      public Out apply(SymbolMap params) {
        inputSchema().validate(params);
        $A $a = params.get(a);
        $Out $out = fn1.apply($a);
        return $out;
      }
    };
  };

  public static <Out, $Out extends Out, $A, A extends $A, $B, B extends $B> Fn<Out> fn(
      final Fn2<$A, $B, $Out> fn2, final Symbol<A> a, final Symbol<B> b) {
    return new FnMixin<Out>(a, b) {
      @Override
      public Out apply(SymbolMap params) {
        inputSchema().validate(params);
        $A $a = params.get(a);
        $B $b = params.get(b);
        $Out $out = fn2.apply($a, $b);
        return $out;
      }
    };
  };

  public static <Out, $Out extends Out, $A, A extends $A, $B, B extends $B, $C, C extends $C> Fn<Out> fn(
      final Fn3<$A, $B, $C, $Out> fn3, final Symbol<A> a, final Symbol<B> b, final Symbol<C> c) {
    return new FnMixin<Out>(a, b, c) {
      @Override
      public Out apply(SymbolMap params) {
        inputSchema().validate(params);
        $A $a = params.get(a);
        $B $b = params.get(b);
        $C $c = params.get(c);
        $Out $out = fn3.apply($a, $b, $c);
        return $out;
      }
    };
  };

  public static <Out, $Out extends Out, $A, A extends $A, $B, B extends $B, $C, C extends $C, $D, D extends $D> Fn<Out> fn(
      final Fn4<$A, $B, $C, $D, $Out> fn4, final Symbol<A> a, final Symbol<B> b, final Symbol<C> c,
      final Symbol<D> d) {
    return new FnMixin<Out>(a, b, c, d) {
      @Override
      public Out apply(SymbolMap params) {
        inputSchema().validate(params);
        $A $a = params.get(a);
        $B $b = params.get(b);
        $C $c = params.get(c);
        $D $d = params.get(d);
        $Out $out = fn4.apply($a, $b, $c, $d);
        return $out;
      }
    };
  };

  public static <Out, $Out extends Out, $A, A extends $A, $B, B extends $B, $C, C extends $C, $D, D extends $D, $E, E extends $E> Fn<Out> fn(
      final Fn5<$A, $B, $C, $D, $E, $Out> fn5, final Symbol<A> a, final Symbol<B> b,
      final Symbol<C> c, final Symbol<D> d, final Symbol<E> e) {
    return new FnMixin<Out>(a, b, c, d, e) {
      @Override
      public Out apply(SymbolMap params) {
        inputSchema().validate(params);
        $A $a = params.get(a);
        $B $b = params.get(b);
        $C $c = params.get(c);
        $D $d = params.get(d);
        $E $e = params.get(e);
        $Out $out = fn5.apply($a, $b, $c, $d, $e);
        return $out;
      }
    };
  };

  public static <Out, $Out extends Out, $A, A extends $A, $B, B extends $B, $C, C extends $C, $D, D extends $D, $E, E extends $E, $F, F extends $F> Fn<Out> fn(
      final Fn6<$A, $B, $C, $D, $E, $F, $Out> fn6, final Symbol<A> a, final Symbol<B> b,
      final Symbol<C> c, final Symbol<D> d, final Symbol<E> e, final Symbol<F> f) {
    return new FnMixin<Out>(a, b, c, d, e, f) {
      @Override
      public Out apply(SymbolMap params) {
        inputSchema().validate(params);
        $A $a = params.get(a);
        $B $b = params.get(b);
        $C $c = params.get(c);
        $D $d = params.get(d);
        $E $e = params.get(e);
        $F $f = params.get(f);
        $Out $out = fn6.apply($a, $b, $c, $d, $e, $f);
        return $out;
      }
    };
  };

  // ===================================
  // act
  // ===================================

  public static Act act(final Act0 act0) {
    return new ActMixin() {
      @Override
      public void apply(SymbolMap params) {
        inputSchema().validate(params);
        act0.apply();
      }
    };
  };

  public static <$A, A extends $A> Act act(final Act1<$A> act1, final Symbol<A> a) {
    return new ActMixin(a) {
      @Override
      public void apply(SymbolMap params) {
        inputSchema().validate(params);
        $A $a = params.get(a);
        act1.apply($a);
      }
    };
  };

  public static <$A, A extends $A, $B, B extends $B> Act act(final Act2<$A, $B> act2,
      final Symbol<A> a, final Symbol<B> b) {
    return new ActMixin(a, b) {
      @Override
      public void apply(SymbolMap params) {
        inputSchema().validate(params);
        $A $a = params.get(a);
        $B $b = params.get(b);
        act2.apply($a, $b);
      }
    };
  };

  public static <$A, A extends $A, $B, B extends $B, $C, C extends $C> Act act(
      final Act3<$A, $B, $C> act3, final Symbol<A> a, final Symbol<B> b, final Symbol<C> c) {
    return new ActMixin(a, b, c) {
      @Override
      public void apply(SymbolMap params) {
        inputSchema().validate(params);
        $A $a = params.get(a);
        $B $b = params.get(b);
        $C $c = params.get(c);
        act3.apply($a, $b, $c);
      }
    };
  };

  public static <$A, A extends $A, $B, B extends $B, $C, C extends $C, $D, D extends $D> Act act(
      final Act4<$A, $B, $C, $D> act4, final Symbol<A> a, final Symbol<B> b, final Symbol<C> c,
      final Symbol<D> d) {
    return new ActMixin(a, b, c, d) {
      @Override
      public void apply(SymbolMap params) {
        inputSchema().validate(params);
        $A $a = params.get(a);
        $B $b = params.get(b);
        $C $c = params.get(c);
        $D $d = params.get(d);
        act4.apply($a, $b, $c, $d);
      }
    };
  };

  public static <$A, A extends $A, $B, B extends $B, $C, C extends $C, $D, D extends $D, $E, E extends $E> Act act(
      final Act5<$A, $B, $C, $D, $E> act5, final Symbol<A> a, final Symbol<B> b, final Symbol<C> c,
      final Symbol<D> d, final Symbol<E> e) {
    return new ActMixin(a, b, c, d, e) {
      @Override
      public void apply(SymbolMap params) {
        inputSchema().validate(params);
        $A $a = params.get(a);
        $B $b = params.get(b);
        $C $c = params.get(c);
        $D $d = params.get(d);
        $E $e = params.get(e);
        act5.apply($a, $b, $c, $d, $e);
      }
    };
  };

  public static <$A, A extends $A, $B, B extends $B, $C, C extends $C, $D, D extends $D, $E, E extends $E, $F, F extends $F> Act act(
      final Act6<$A, $B, $C, $D, $E, $F> act6, final Symbol<A> a, final Symbol<B> b,
      final Symbol<C> c, final Symbol<D> d, final Symbol<E> e, final Symbol<F> f) {
    return new ActMixin(a, b, c, d, e, f) {
      @Override
      public void apply(SymbolMap params) {
        inputSchema().validate(params);
        $A $a = params.get(a);
        $B $b = params.get(b);
        $C $c = params.get(c);
        $D $d = params.get(d);
        $E $e = params.get(e);
        $F $f = params.get(f);
        act6.apply($a, $b, $c, $d, $e, $f);
      }
    };
  };
}
