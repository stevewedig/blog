package com.stevewedig.blog.symbol;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Ordering;

/**
 * The library for working with TypeMap, Symbol, SymbolMap, SymbolSchema, and SymbolBus.
 */
public abstract class SymbolLib {

  // ===========================================================================
  // TypeMap
  // ===========================================================================

  /**
   * Create an empty TypeMap.
   */
  public static TypeMap typeMap() {
    return new TypeMapClass();
  }

  /**
   * Create a TypeMap from its internal state, which is not statically checked.
   */
  public static TypeMap typeMap(Map<Class<?>, Object> state) {
    return new TypeMapClass(state);
  }

  // ===========================================================================
  // Symbol
  // ===========================================================================

  /**
   * Create a symbol from a name and a Value parameter.
   */
  public static <Value> Symbol<Value> symbol(String name) {
    return new Symbol<Value>(name);
  }

  /**
   * Create a symbol from a type object.
   */
  public static <Value> Symbol<Value> symbol(Class<Value> type) {
    return symbol(type.getName());
  }

  // ===========================================================================
  // SymbolMap
  // ===========================================================================

  /**
   * Create an empty SymbolMap.Fluid, which can be used as a builder for an immutable map
   * (map().put()...put().solid()).
   */
  public static SymbolMap.Fluid map() {
    return new SymbolMapFluidClass();
  }

  /**
   * Create a SymbolMap.Fluid from its internal state, which is not statically checked.
   */
  public static SymbolMap.Fluid map(Map<Symbol<?>, Object> state) {
    return new SymbolMapFluidClass(state);
  }

  /**
   * Create an empty SymbolMap.Solid (which is not very useful).
   */
  public static SymbolMap.Solid solidMap() {
    return new SymbolMapSolidClass();
  }

  /**
   * Create a SymbolMap.Solid from its internal state, which is not statically checked.
   */
  public static SymbolMap.Solid solidMap(Map<Symbol<?>, Object> state) {
    return new SymbolMapSolidClass(state);
  }

  // ===========================================================================
  // SymbolSchema
  // ===========================================================================

  /**
   * Create a SymbolSchema from required and optional symbols.
   */
  public static SymbolSchema schema(Iterable<Symbol<?>> requiredSymbols,
      Iterable<Symbol<?>> optionalSymbols) {
    return new SymbolSchemaClass(requiredSymbols, optionalSymbols);
  }

  /**
   * Create a SymbolSchema from required symbols.
   */
  public static SymbolSchema schema(Iterable<Symbol<?>> requiredSymbols) {
    ImmutableSet<Symbol<?>> optionalSymbols = ImmutableSet.of();
    return schema(requiredSymbols, optionalSymbols);
  }

  /**
   * Create a SymbolSchema from required symbols.
   */
  public static SymbolSchema schema(Symbol<?>... requiredSymbols) {
    return schema(ImmutableSet.copyOf(requiredSymbols));
  }

  /**
   * An empty SymbolSchema fly weight.
   */
  public static SymbolSchema emptySchema = schema();

  // ===========================================================================
  // SymbolBus
  // ===========================================================================

  /**
   * Create a SymbolBus.
   */
  public static SymbolBus bus() {
    return new SymbolBusClass();
  }

  // ===========================================================================
  // sort
  // ===========================================================================

  /**
   * Sort symbols by name.
   */
  public static List<Symbol<?>> sortSymbols(Iterable<Symbol<?>> symbols) {
    return symbolOrdering.sortedCopy(symbols);
  }

  private static Ordering<Symbol<?>> symbolOrdering = new Ordering<Symbol<?>>() {
    @Override
    public int compare(Symbol<?> left, Symbol<?> right) {
      return left.name().compareTo(right.name());
    }
  };

}
