package com.stevewedig.blog.util;

import java.util.UUID;

public abstract class UuidLib {

  public static String uuid() {
    return UUID.randomUUID().toString();
  }

  public static String uuid(String prefix) {
    return prefix + "-" + uuid();
  }
    
}
