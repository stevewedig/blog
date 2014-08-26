package com.stevewedig.blog.symbol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.stevewedig.blog.util.LambdaLib.Act1;

public class TestSymbolBus {

  private Symbol<String> $signup = SymbolLib.symbol("signup");
  private Symbol<String> $login = SymbolLib.symbol("login");

  // ===========================================================================

//  @Before
//  protected void setUp() {
//
//  }

  // ===================================

  private Act1<String> signupCallback = new Act1<String>() {
    @Override
    public void apply(String accountId) {
      signupId = accountId;
      signupCount++;
    }
  };

  private String signupId = null;
  private int signupCount = 0;

  // ===========================================================================
  
  SymbolBus bus = SymbolLib.bus();

  // ===========================================================================
  // subscribe symbol
  // ===========================================================================

  @Test
  public void testSymbolBus__subscribeSymbol() {

    // initial state
    assertNull(signupId);
    assertEquals(0, signupCount);
    
    // before subscribing, so callback is not called
    bus.publish($signup, "a");
    assertNull(signupId);
    assertEquals(0, signupCount);
    
    // subscribe, so callback is called
    bus.subscribe($signup, signupCallback);
    bus.publish($signup, "b");
    assertEquals("b", signupId);
    assertEquals(1, signupCount);
    
    // unsubscribe, so callback is not called 
    bus.unsubscribe($signup, signupCallback);
    bus.publish($signup, "c");
    assertEquals("b", signupId);
    assertEquals(1, signupCount);
    
    // resubscribe, so callback is called
    bus.subscribe($signup, signupCallback);
    bus.publish($signup, "d");
    assertEquals("d", signupId);
    assertEquals(2, signupCount);
    
    // subscribe again, make sure callback is only called once though
    bus.subscribe($signup, signupCallback);
    bus.publish($signup, "e");
    assertEquals("e", signupId);
    assertEquals(3, signupCount);
  }

  // ===========================================================================
  // subscribe all
  // ===========================================================================
  
  // ===========================================================================
  // subscribe misses
  // ===========================================================================

  // ===========================================================================
  // integration
  // ===========================================================================

}
