package com.stevewedig.blog.util;

public interface CallbackLib {

  public interface Fn<Out> {
    void ok(Out out);

    void fail(Throwable failure);
  }

  public static interface Act {
    void ok();

    void fail(Throwable failure);
  }
}
