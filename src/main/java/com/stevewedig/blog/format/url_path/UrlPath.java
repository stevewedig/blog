package com.stevewedig.blog.format.url_path;

import com.google.common.collect.ImmutableList;

public interface UrlPath extends Iterable<String> {
  ImmutableList<String> parts();

  // ===========================================================================

  boolean isFile();

  boolean isDir();

  // ===========================================================================

  boolean isEmpty();

  boolean notEmpty();

  boolean isRootDir();
  
  // ===========================================================================

  String head();

  UrlPath tail();

  // ===========================================================================

  UrlPath concat(UrlPath subPath);

  // ===========================================================================

  UrlPath toggleIsFile();


}
