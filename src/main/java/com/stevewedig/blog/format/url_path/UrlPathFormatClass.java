package com.stevewedig.blog.format.url_path;

import static java.util.Objects.requireNonNull;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.stevewedig.blog.format.Format;
import com.stevewedig.blog.format.ParseError;

public class UrlPathFormatClass implements Format<UrlPath> {

  private static char SLASH = '/';
  private static String EMPTY_PATH_STR = "/";

  private Format<String> pathPartFormat;

  public UrlPathFormatClass(Format<String> pathPartFormat) {
    super();
    this.pathPartFormat = pathPartFormat;
  }

  // ===========================================================================
  // write
  // ===========================================================================

  private static Iterable<String> emptyPathParts = ImmutableList.of();

  @Override
  public String write(UrlPath path) {
    requireNonNull(path);
        
    if (path.isEmpty())
      return EMPTY_PATH_STR;

    StringBuilder str = new StringBuilder();

    for (String part : path.parts()) {
      str.append(SLASH);
      str.append(pathPartFormat.write(part));
    }

    if (path.isDir())
      str.append(SLASH);

    return str.toString();
  }

  // ===========================================================================
  // parse
  // ===========================================================================

  @Override
  public UrlPath parse(String str) throws ParseError {
    requireNonNull(str);

    int size = str.length();

    if (size == 0)
      throw new ParseError(str);

    if (str.charAt(0) != SLASH)
      throw new ParseError(str);

    boolean isFile;
    Iterable<String> writtenParts;
    if (size == 1) {
      isFile = false;
      writtenParts = emptyPathParts;
    } else if (str.charAt(size - 1) == SLASH) {
      isFile = false;
      writtenParts = slashSplit(str.substring(1, size - 1));
    } else {
      isFile = true;
      writtenParts = slashSplit(str.substring(1));
    }

    ImmutableList.Builder<String> parts = ImmutableList.builder();
    for (String writtenPart : writtenParts) {
      String part = pathPartFormat.parse(writtenPart);
      parts.add(part);
    }

    return UrlPathLib.path(isFile, parts.build());
  }

  private Iterable<String> slashSplit(String str) {
    return Splitter.on(SLASH).split(str);
  }

}
