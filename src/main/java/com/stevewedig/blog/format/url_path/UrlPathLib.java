package com.stevewedig.blog.format.url_path;

import com.google.common.collect.ImmutableList;
import com.stevewedig.blog.format.Format;
import com.stevewedig.blog.util.LambdaLib.Fn1;

public abstract class UrlPathLib {

  // ===========================================================================
  // path
  // ===========================================================================

  public static UrlPath path(boolean isFile, ImmutableList<String> parts) {
    return new UrlPathClass(isFile, parts);
  }

  public static UrlPath path(boolean isFile, Iterable<String> parts) {
    return path(isFile, ImmutableList.copyOf(parts));
  }

  public static UrlPath path(boolean isFile, String... parts) {
    return path(isFile, ImmutableList.copyOf(parts));
  }

  // ===========================================================================
  // dir
  // ===========================================================================

  public static UrlPath dir(ImmutableList<String> parts) {
    return path(false, parts);
  }

  public static UrlPath dir(Iterable<String> parts) {
    return path(false, parts);
  }

  public static UrlPath dir(String... parts) {
    return path(false, parts);
  }

  public static UrlPath rootDir() {
    return path(false, ImmutableList.<String>of());
  }

  // ===========================================================================
  // file
  // ===========================================================================

  public static UrlPath file(ImmutableList<String> parts) {
    return path(true, parts);
  }

  public static UrlPath file(Iterable<String> parts) {
    return path(true, parts);
  }

  public static UrlPath file(String... parts) {
    return path(true, parts);
  }

  // ===========================================================================
  // format
  // ===========================================================================

  public static Format<UrlPath> format(Format<String> pathPartFormat) {
    return new UrlPathFormatClass(pathPartFormat);
  }

  public static Fn1<Format<String>, Format<UrlPath>> createFormat =
      new Fn1<Format<String>, Format<UrlPath>>() {
        @Override
        public Format<UrlPath> apply(Format<String> a) {
          return format(a);
        }
      };
}
