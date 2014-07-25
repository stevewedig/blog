package com.stevewedig.blog.util;

import com.stevewedig.blog.util.LambdaLib.Fn0;



public abstract class CyclicRefLib {

  // ===========================================================================
  // factory
  // ===========================================================================

  public static <Value> Ref<Value> ref() {
    return new CyclicRefClass<>();
  }

  // ===========================================================================
  // interface
  // ===========================================================================

  public static interface Ref<Value> {

    /**
     * @return lambda that calls getValue()
     */
    Fn0<Value> lambda();

    Value getValue() throws CyclicRefNotSet;

    void setValue(Value value) throws CyclicRefAlreadySet;
    
    boolean hasSetValue();
  }

  // ===========================================================================
  // errors
  // ===========================================================================

  public static class CyclicRefNotSet extends RuntimeException {
    private static final long serialVersionUID = 1L;
  }

  public static class CyclicRefAlreadySet extends RuntimeException {
    private static final long serialVersionUID = 1L;
  }

  // ===========================================================================
  // class
  // ===========================================================================

  private static class CyclicRefClass<Value> implements Ref<Value> {
    private boolean hasSetValue = false;
    private Value value;

    @Override
    public Fn0<Value> lambda() {
      return getValueLambda;
    }

    private Fn0<Value> getValueLambda = new Fn0<Value>() {
      @Override
      public Value apply() {
        return getValue();
      }
    };

    @Override
    public boolean hasSetValue() {
      return hasSetValue;
    }

    @Override
    public Value getValue() throws CyclicRefNotSet {
      if (!hasSetValue)
        throw new CyclicRefNotSet();
      return value;
    }

    @Override
    public void setValue(Value value) throws CyclicRefAlreadySet {
      if (hasSetValue)
        throw new CyclicRefAlreadySet();

      hasSetValue = true;
      this.value = value;
    }
  }
}
