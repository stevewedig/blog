package com.stevewedig.foundation.url.path;

import com.google.common.collect.ImmutableList;
import com.stevewedig.foundation.etc.StrLib;

public abstract class PathLib {

  public static Path path(ImmutableList<String> parts) {
    return new PathClass(parts);
  }

  public static Path path(Iterable<String> parts) {
    return new PathClass(ImmutableList.copyOf(parts));
  }

  public static Path path(String part) {
    Iterable<String> parts;
    if(StrLib.isBlank(part))
      parts = ImmutableList.of();
    else
      parts = ImmutableList.of(part); 
    return path(parts);
  }

  public static Path path() {
    Iterable<String> parts = ImmutableList.of();
    return path(parts);
  }
  
  public static Path split(String pathStr, char delim) {
    return path(StrLib.split(pathStr, delim));
  }
}
