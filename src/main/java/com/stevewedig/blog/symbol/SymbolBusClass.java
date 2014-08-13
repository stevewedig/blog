package com.stevewedig.blog.symbol;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.stevewedig.blog.errors.NotImplemented;
import com.stevewedig.blog.util.CastLib;
import com.stevewedig.blog.util.LambdaLib.Act1;
import com.stevewedig.blog.util.LambdaLib.Act2;

class SymbolBusClass implements SymbolBus {

  // ===========================================================================
  // threadsafe state
  // ===========================================================================

  private final Multimap<Symbol<?>, Act1<?>> symbol__callbacks = Multimaps
      .synchronizedSetMultimap(HashMultimap.<Symbol<?>, Act1<?>>create());


  // ===========================================================================
  // publish
  // ===========================================================================

  @Override
  public <Event> void publish(Symbol<Event> symbol, Event event) {
    for (Act1<?> _callback : symbol__callbacks.get(symbol)) {

      // safe because of subscribe's signature
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
    throw new NotImplemented(); // TODO
  }

  @Override
  public void unsubscribeAll(Act2<Symbol<?>, Object> callback) {
    throw new NotImplemented(); // TODO
  }

  // ===========================================================================
  // subscribeMisses
  // ===========================================================================

  @Override
  public void subscribeMisses(Act2<Symbol<?>, Object> callback) {
    throw new NotImplemented(); // TODO
  }

  @Override
  public void unsubscribeMisses(Act2<Symbol<?>, Object> callback) {
    throw new NotImplemented(); // TODO
  }

}
