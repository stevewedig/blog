package com.stevewedig.blog.util;

import java.io.Serializable;

import com.google.common.base.Optional;

/**
 * useful for async and for getting around checked exception (for io chains, etc)
 * 
 * @param <T>
 */
public class Fallible<T> implements Serializable {
  private static final long serialVersionUID = 1L;

  // ===========================================================================
  // create
  // ===========================================================================

  public static <T> Fallible<T> success(T value) {
    return new Fallible<T>(Optional.of(value), Optional.<Throwable>absent());
  }

  public static <T> Fallible<T> failure(Throwable error) {
    return new Fallible<T>(Optional.<T>absent(), Optional.of(error));
  }

  // ===========================================================================
  // state
  // ===========================================================================

  private final Optional<T> value;
  private final Optional<Throwable> error;

  // ===========================================================================
  // constructor
  // ===========================================================================

  private Fallible(Optional<T> value, Optional<Throwable> error) {
    super();
    this.value = value;
    this.error = error;
  }

  // ===========================================================================
  // attr
  // ===========================================================================

  public Optional<T> value() {
    return value;
  }

  public Optional<Throwable> error() {
    return error;
  }

  // ===========================================================================
  // convenience
  // ===========================================================================

  public boolean isSuccess() {
    return value().isPresent();
  }

  public boolean isFailure() {
    return !isSuccess();
  }

  public T get() {
    // TODO improve this error message
    return value().get();
  }

  public Object getError() {
    // TODO improve this error message
    return error().get();
  }


}
