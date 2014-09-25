package com.stevewedig.blog.symbol;

import com.stevewedig.blog.util.LambdaLib.Act1;
import com.stevewedig.blog.util.LambdaLib.Act2;

/**
 * A type safe event bus for heterogeneous events.
 */
public interface SymbolBus {

  /**
   * Publish an event to a symbol of the corresponding type.
   */
  <Event> void publish(Symbol<Event> symbol, Event event);

  // ===================================

  /**
   * Subscribe to a symbol's events.
   */
  <Event> void subscribe(Symbol<Event> symbol, Act1<Event> callback);

  /**
   * Stop subscribing to a symbol's events.
   */
  <Event> void unsubscribe(Symbol<Event> symbol, Act1<Event> callback);

  // ===================================

  /**
   * Subscribe to all events.
   */
  void subscribeAll(Act2<Symbol<?>, Object> callback);

  /**
   * Stop subscribing to all events.
   */
  void unsubscribeAll(Act2<Symbol<?>, Object> callback);

  // ===================================

  /**
   * Subscribe to all events without subscribers.
   */
  void subscribeMisses(Act2<Symbol<?>, Object> callback);

  /**
   * Stop subscribing to all events without subscribers.
   */
  void unsubscribeMisses(Act2<Symbol<?>, Object> callback);

}
