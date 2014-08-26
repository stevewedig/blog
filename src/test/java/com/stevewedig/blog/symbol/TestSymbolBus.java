package com.stevewedig.blog.symbol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.stevewedig.blog.util.LambdaLib.Act1;
import com.stevewedig.blog.util.LambdaLib.Act2;

public class TestSymbolBus {

  // ===========================================================================
  // create symbols
  // ===========================================================================

  protected Symbol<String> $signup = SymbolLib.symbol("signup");
  protected Symbol<String> $login = SymbolLib.symbol("login");

  // ===========================================================================
  // create signup callback
  // ===========================================================================

  protected Act1<String> signupCallback = new Act1<String>() {
    @Override
    public void apply(String accountId) {
      signupId = accountId;
      signupCount++;
    }
  };

  protected String signupId = null;
  protected int signupCount = 0;

  // ===========================================================================
  // create all callback
  // ===========================================================================

  protected Act2<Symbol<?>, Object> allCallback = new Act2<Symbol<?>, Object>() {
    @Override
    public void apply(Symbol<?> symbol, Object event) {
      allSymbol = symbol;
      allId = (String) event;
      allCount++;
    }
  };

  protected Symbol<?> allSymbol = null;
  protected String allId = null;
  protected int allCount = 0;

  // ===========================================================================
  // create miss callback
  // ===========================================================================

  protected Act2<Symbol<?>, Object> missCallback = new Act2<Symbol<?>, Object>() {
    @Override
    public void apply(Symbol<?> symbol, Object event) {
      missSymbol = symbol;
      missId = (String) event;
      missCount++;
    }
  };

  protected Symbol<?> missSymbol = null;
  protected String missId = null;
  protected int missCount = 0;

  // ===========================================================================
  // create bus
  // ===========================================================================

  protected SymbolBus bus = SymbolLib.bus();

  // ===========================================================================
  // tests
  // ===========================================================================

  @Test
  public void testSymbolBus__noSubscribers() {

    // initial state
    assertNull(signupId);
    assertEquals(0, signupCount);
    assertNull(allSymbol);
    assertNull(allId);
    assertEquals(0, allCount);
    assertNull(missSymbol);
    assertNull(missId);
    assertEquals(0, missCount);

    // no subscribers
    bus.publish($signup, "id1");
    assertEquals(0, signupCount);
    assertNull(allSymbol);
    assertNull(allId);
    assertEquals(0, allCount);
    assertNull(missSymbol);
    assertNull(missId);
    assertEquals(0, missCount);

    // subscribe then unsubscribe
    bus.subscribe($signup, signupCallback);
    bus.unsubscribe($signup, signupCallback);
    bus.subscribeAll(allCallback);
    bus.unsubscribeAll(allCallback);
    bus.subscribeMisses(missCallback);
    bus.unsubscribeMisses(missCallback);

    // still no subscribers
    bus.publish($signup, "id2");
    assertEquals(0, signupCount);
    assertNull(allSymbol);
    assertNull(allId);
    assertEquals(0, allCount);
    assertNull(missSymbol);
    assertNull(missId);
    assertEquals(0, missCount);
  }

  @Test
  public void testSymbolBus__withSubscribers() {

    // initial state
    assertEquals(0, signupCount);
    assertNull(allSymbol);
    assertNull(allId);
    assertEquals(0, allCount);
    assertNull(missSymbol);
    assertNull(missId);
    assertEquals(0, missCount);

    // subscribe
    bus.subscribe($signup, signupCallback);
    bus.subscribeAll(allCallback);
    bus.subscribeMisses(missCallback);

    // subscribe again to ensure that each is only called once
    bus.subscribe($signup, signupCallback);
    bus.subscribeAll(allCallback);
    bus.subscribeMisses(missCallback);

    // add other subscribers, to verify that we support multiple
    bus.subscribe($signup, new Act1<String>() {
      @Override
      public void apply(String accountId) {}
    });
    bus.subscribeAll(new Act2<Symbol<?>, Object>() {
      @Override
      public void apply(Symbol<?> symbol, Object event) {}
    });
    bus.subscribeMisses(new Act2<Symbol<?>, Object>() {
      @Override
      public void apply(Symbol<?> symbol, Object event) {}
    });

    // publish to symbol with subscriber ($signup)
    bus.publish($signup, "id1");
    assertEquals("id1", signupId);
    assertEquals(1, signupCount);
    assertEquals($signup, allSymbol);
    assertEquals("id1", allId);
    assertEquals(1, allCount);
    assertNull(missSymbol); // no change
    assertNull(missId); // no change
    assertEquals(0, missCount); // no change

    // publish to symbol without subscriber ($login)
    bus.publish($login, "id2");
    assertEquals("id1", signupId); // no change
    assertEquals(1, signupCount); // no change
    assertEquals($login, allSymbol);
    assertEquals("id2", allId);
    assertEquals(2, allCount);
    assertEquals($login, missSymbol);
    assertEquals("id2", missId);
    assertEquals(1, missCount);
  }

}
