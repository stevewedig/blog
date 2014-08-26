package com.stevewedig.blog.symbol;

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

  private final Set<Act2<Symbol<?>, Object>> globalCallbacks = Collections
      .synchronizedSet(new HashSet<Act2<Symbol<?>, Object>>());

  private final Set<Act2<Symbol<?>, Object>> missCallbacks = Collections
      .synchronizedSet(new HashSet<Act2<Symbol<?>, Object>>());

  // ===================================
  
  private <Event> boolean symbolHasSubscriber(Symbol<Event> symbol) {
    return symbol__callbacks.containsKey(symbol);
  }

  // ===========================================================================
  // publish
  // ===========================================================================

  @Override
  public <Event> void publish(Symbol<Event> symbol, Event event) {

    publishAll(symbol, event);

    if (symbolHasSubscriber(symbol))
      publishHit(symbol, event);
    else
      publishMiss(symbol, event);

  }

  // ===================================
  
  private <Event> void publishAll(Symbol<Event> symbol, Event event) {

    for (Act2<Symbol<?>, Object> callback : globalCallbacks)
      callback.apply(symbol, event);
  }

  private <Event> void publishMiss(Symbol<Event> symbol, Event event) {

    for (Act2<Symbol<?>, Object> callback : missCallbacks)
      callback.apply(symbol, event);
  }

  private <Event> void publishHit(Symbol<Event> symbol, Event event) {

    for (Act1<?> _callback : symbol__callbacks.get(symbol)) {

      // type safe because of subscribe's signature
      Act1<Event> callback = CastLib.cast(_callback);

      callback.apply(event);
    }
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
    globalCallbacks.add(callback);
  }

  @Override
  public void unsubscribeAll(Act2<Symbol<?>, Object> callback) {
    globalCallbacks.remove(callback);
  }

  // ===========================================================================
  // subscribeMisses
  // ===========================================================================

  @Override
  public void subscribeMisses(Act2<Symbol<?>, Object> callback) {
    missCallbacks.add(callback);
  }

  @Override
  public void unsubscribeMisses(Act2<Symbol<?>, Object> callback) {
    missCallbacks.remove(callback);
  }

}
