package com.stevewedig.blog.symbol;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.stevewedig.blog.util.CastLib;
import com.stevewedig.blog.util.LambdaLib.Act1;
import com.stevewedig.blog.util.LambdaLib.Act2;

class SymbolBusClass implements SymbolBus {

  // ===========================================================================
  // threadsafe state
  // ===========================================================================

  private final Multimap<Symbol<?>, Act1<?>> symbol__callbacks = Multimaps
      .synchronizedSetMultimap(HashMultimap.<Symbol<?>, Act1<?>>create());

  private final Set<Act2<Symbol<?>, Object>> catchAlls = Collections
      .synchronizedSet(new HashSet<Act2<Symbol<?>, Object>>());

  private final Set<Act2<Symbol<?>, Object>> catchMisses = Collections
      .synchronizedSet(new HashSet<Act2<Symbol<?>, Object>>());

  // ===========================================================================
  // publish
  // ===========================================================================

  @Override
  public <Event> void publish(Symbol<Event> symbol, Event event) {

    Collection<Act1<?>> callbacks = symbol__callbacks.get(symbol);

    // miss subscribers
    if (callbacks.isEmpty()) {
      for (Act2<Symbol<?>, Object> callback : catchMisses)
        callback.apply(symbol, event);

      return;
    }

    // symbol subscribers
    for (Act1<?> _callback : callbacks) {

      // safe because of subscribe's signature
      Act1<Event> callback = CastLib.cast(_callback);

      callback.apply(event);
    }

    // all subscribers
    for (Act2<Symbol<?>, Object> callback : catchAlls)
      callback.apply(symbol, event);
  }

  // ===========================================================================
  // subscribe
  // ===========================================================================

  @Override
  public <Event> void subscribe(Symbol<Event> symbol, Act1<Event> callback) {
    symbol__callbacks.put(symbol, callback);
  }

  @Override
  public <Event> void unsubscribe(Symbol<Event> symbol, Act1<Event> callback) {
    symbol__callbacks.remove(symbol, callback);
  }

  // ===========================================================================
  // subscribeAll
  // ===========================================================================

  @Override
  public void subscribeAll(Act2<Symbol<?>, Object> callback) {
    catchAlls.add(callback);
  }

  @Override
  public void unsubscribeAll(Act2<Symbol<?>, Object> callback) {
    catchAlls.remove(callback);
  }

  // ===========================================================================
  // subscribeMisses
  // ===========================================================================

  @Override
  public void subscribeMisses(Act2<Symbol<?>, Object> callback) {
    catchMisses.add(callback);
  }

  @Override
  public void unsubscribeMisses(Act2<Symbol<?>, Object> callback) {
    catchMisses.remove(callback);
  }

}
