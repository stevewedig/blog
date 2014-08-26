package com.stevewedig.blog.symbol;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.stevewedig.blog.util.LambdaLib.Act1;

public class TestSymbolBus2 {


  @Test
  public void testSymbolBus() {

    SymbolBus bus = SymbolLib.bus();

    // note that we can use multiple symbols with the same type parameter
    final Symbol<Integer> $accountCreated = SymbolLib.symbol("accountCreated");
    final Symbol<Integer> $accountDeleted = SymbolLib.symbol("accountDeleted");

    final Map<Symbol<?>, Integer> symbol__lastAccountId = new HashMap<>();

    Act1<Integer> accountCreatedListener = new Act1<Integer>() {
      @Override
      public void apply(Integer accountId) {
        symbol__lastAccountId.put($accountCreated, accountId);
      }
    };

    Act1<Integer> accountDeletedListener = new Act1<Integer>() {
      @Override
      public void apply(Integer accountId) {
        symbol__lastAccountId.put($accountDeleted, accountId);
      }
    };

    // start with no listeners, so events have no effect
    bus.publish($accountCreated, 1);
    bus.publish($accountDeleted, 2);
    assertTrue(symbol__lastAccountId.isEmpty());

    // listen then unlisten, so still no effect
    bus.subscribe($accountCreated, accountCreatedListener);
    bus.subscribe($accountDeleted, accountDeletedListener);
    bus.unsubscribe($accountCreated, accountCreatedListener);
    bus.unsubscribe($accountDeleted, accountDeletedListener);
    bus.publish($accountCreated, 1);
    bus.publish($accountDeleted, 2);
    assertTrue(symbol__lastAccountId.isEmpty());

    // add listeners, so now has effect
    bus.subscribe($accountCreated, accountCreatedListener);
    bus.subscribe($accountDeleted, accountDeletedListener);
    bus.publish($accountCreated, 3);
    bus.publish($accountDeleted, 4);
    assertThat(symbol__lastAccountId.get($accountCreated), equalTo(3));
    assertThat(symbol__lastAccountId.get($accountDeleted), equalTo(4));
    
  }
  
  // TODO test the multiple listeners for one event case

  // TODO prevent duplicate firing if registered twice?

}
