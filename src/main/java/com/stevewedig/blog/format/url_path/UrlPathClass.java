package com.stevewedig.blog.format.url_path;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.stevewedig.blog.value_objects.ValueMixin;

class UrlPathClass extends ValueMixin implements UrlPath {
  private final boolean isFile;
  private final ImmutableList<String> parts;

  public UrlPathClass(boolean isFile, ImmutableList<String> parts) {
    if (isFile && parts.isEmpty())
      throw new RuntimeException("root path must be a dir");

    this.isFile = isFile;
    this.parts = parts;
  }

  @Override
  public boolean isFile() {
    return isFile;
  }

  @Override
  public boolean isDir() {
    return !isFile();
  }


  @Override
  public ImmutableList<String> parts() {
    return parts;
  }

  // ===========================================================================
  // ValueMixin
  // ===========================================================================

  @Override
  public Object[] fields() {
    return array("isFile", isFile, "parts", parts);
  }

  // ===========================================================================
  // Iterable
  // ===========================================================================

  @Override
  public Iterator<String> iterator() {
    return parts.iterator();
  }

  // ===========================================================================

  @Override
  public boolean isEmpty() {
    return parts.isEmpty();
  }

  @Override
  public boolean notEmpty() {
    return !isEmpty();
  }
  
  @Override
  public boolean isRootDir() {
    // isDir() check is technically unnecessary, but just being cautious
    return isDir() && isEmpty();
  }

  // ===========================================================================

  @Override
  public String head() {

    if (isEmpty())
      throw new RuntimeException("emtpy path doesn't have a head");

    return parts.get(0);
  }

  @Override
  public UrlPath tail() {

    if (isEmpty())
      throw new RuntimeException("emtpy path doesn't have a tail");

    Iterator<String> tailParts = parts.iterator();
    tailParts.next();

    return UrlPathLib.dir(ImmutableList.copyOf(tailParts));
  }

  // ===========================================================================

  // TODO untested
  @Override
  public UrlPath concat(UrlPath subPath) {

    if (isFile)
      throw new CannotConcatToFilePath();

    List<String> newParts = Lists.newArrayList(parts());
    newParts.addAll(subPath.parts());

    return UrlPathLib.path(subPath.isFile(), newParts);
  }

  // ===========================================================================

  @Override
  public UrlPath toggleIsFile() {
    return UrlPathLib.path(!isFile(), parts());
  }

}
