package com.stevewedig.blog.symbol;

import com.stevewedig.blog.util.LambdaLib.Act1;
import com.stevewedig.blog.util.LambdaLib.Act2;

/**
 * A type safe event bus for heterogeneous events.
 */
public interface SymbolBus {

  <Event> void publish(Symbol<Event> symbol, Event event);

  // ===================================

  <Event> void subscribe(Symbol<Event> symbol, Act1<Event> callback);

  <Event> void unsubscribe(Symbol<Event> symbol, Act1<Event> callback);

  // ===================================

  void subscribeAll(Act2<Symbol<?>, Object> callback);

  void unsubscribeAll(Act2<Symbol<?>, Object> callback);

  // ===================================

  void subscribeMisses(Act2<Symbol<?>, Object> callback);

  void unsubscribeMisses(Act2<Symbol<?>, Object> callback);

}
