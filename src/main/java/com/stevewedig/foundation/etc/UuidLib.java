package com.stevewedig.foundation.etc;

import java.util.UUID;

public abstract class UuidLib {

  public static String uuid() {
    return UUID.randomUUID().toString();
  }

  public static String uuid(String prefix) {
    return prefix + "-" + uuid();
  }
    
}
