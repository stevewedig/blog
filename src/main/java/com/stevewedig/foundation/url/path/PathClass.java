package com.stevewedig.foundation.url.path;

import java.util.Iterator;

import com.google.common.collect.ImmutableList;
import com.stevewedig.foundation.value_object.ValueMixin;
import com.stevewedig.foundation.value_object.ValueObjectHelper;

class PathClass extends ValueMixin implements Path {
  private final ImmutableList<String> parts;

  public PathClass(ImmutableList<String> parts) {
    this.parts = parts;
  }

  @Override
  public ImmutableList<String> parts() {
    return parts;
  }

  // ===========================================================================
  // ValueMixin
  // ===========================================================================

  @Override
  public ValueObjectHelper objectHelper() {
    return objectHelper("parts", parts);
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

  // ===========================================================================
  
  @Override
  public String head() {

    if (isEmpty())
      throw new RuntimeException("emtpy path doesn't have a head");
    
    return parts.get(0);
  }

  @Override
  public Path tail() {

    if (isEmpty())
      throw new RuntimeException("emtpy path doesn't have a tail");

    Iterator<String> tailParts = parts.iterator();
    tailParts.next();

    return PathLib.path(ImmutableList.copyOf(tailParts));
  }

}
