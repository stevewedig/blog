package com.stevewedig.foundation.url.path;

import com.google.common.collect.ImmutableList;

public interface Path {
  ImmutableList<String> parts();

  // ===========================================================================

  boolean isEmpty();

  boolean notEmpty();

  // ===========================================================================

  String head();

  Path tail();
}
