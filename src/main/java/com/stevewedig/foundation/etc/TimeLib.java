package com.stevewedig.foundation.etc;

import com.stevewedig.foundation.fn.LambdaLib.Fn0;

public abstract class TimeLib {

  // http://stackoverflow.com/questions/732034/getting-unixtime-in-java
  public static int unixSecOffset() {
    return (int) (System.currentTimeMillis() / 1000L);
  }

  public static void blockUntil(int timeoutMs, int sleepMs, Fn0<Boolean> doContinue) {
    int retryCount = timeoutMs / sleepMs;
    for (int i = 0; i < retryCount; i++) {
      if (doContinue.apply()) {
        return;
      }
      sleep(sleepMs);
    }
    throw new RuntimeException("blockUntil timed out");
  }

  public static void sleep(int msCount) {
    try {
      Thread.sleep(msCount);
    } catch (InterruptedException e) {
    }
  }

  // http://enos.itcollege.ee/~jpoial/docs/tutorial/essential/threads/timer.html
  // java.util.Timer not available in GWT, so even though you can import it from
  // java standard library, it actually counts as a dependency like any other
  // final Timer timer = new Timer();
  // TimerTask task = new TimerTask() {
  // @Override
  // public void run() {
  // timer.cancel();
  // act.apply();
  // }
  // };
  // timer.schedule(task, 2 * 1000);
}
