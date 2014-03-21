package com.stevewedig.foundation.url.router;

import com.stevewedig.foundation.value_object.ObjectHelperLib;

public class PathRouterLocationConflict extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private String key;

  public PathRouterLocationConflict(String key) {
    super();
    this.key = key;
  }

  @Override
  public String toString() {
    return ObjectHelperLib.strHelper(getClass(), "key", key);
  }
}
