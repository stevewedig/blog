package com.stevewedig.blog.value_objects;

import static com.stevewedig.blog.value_objects.CompareLib.assertEqualObjectsAndStrings;
import static com.stevewedig.blog.value_objects.CompareLib.assertUnequalObjectsAndStrings;

import org.junit.Test;

import com.google.common.base.Optional;

public class TestValueWithoutMixin {

  // ===========================================================================
  // Image
  // ===========================================================================

  static class Image implements HasObjectHelper {

    private final String url;
    private final Optional<Integer> height;
    private final Optional<Integer> width;

    protected Object[] fields() {
      return new Object[] {"url", url, "height", height, "width", width};
    }

    public Image(String url, Optional<Integer> height, Optional<Integer> width) {
      this.url = url;
      this.height = height;
      this.width = width;
    }

    public Image(String url, Integer height, Integer width) {
      this(url, Optional.of(height), Optional.of(width));
    }

    public Image(String url) {
      this(url, Optional.<Integer>absent(), Optional.<Integer>absent());
    }

    public String url() {
      return url;
    }

    public Optional<Integer> height() {
      return height;
    }

    public Optional<Integer> width() {
      return width;
    }

    // =================================
    // Due to Java only supporting single implementation inheritance, you sometimes have to
    // copy/paste the following instead of inheriting from ValueMixin. I've tried to minimize how
    // much code gets copy/pasted by pulling the logic into an ObjectHelper.
    // =================================

    @Override
    public ObjectHelper objectHelper() {
      if (cachedHelper == null)
        cachedHelper = ObjectHelperLib.helper(getClass(), fields());
      return cachedHelper;
    }

    private ObjectHelper cachedHelper;

    @Override
    public String toString() {
      return objectHelper().classAndStateString();
    }

    @Override
    public boolean equals(Object other) {
      return objectHelper().classAndStateEquals(other);
    }

    @Override
    public int hashCode() {
      return objectHelper().classAndStateHash();
    }

  }

  // ===========================================================================
  // test
  // ===========================================================================

  @Test
  public void testValueWithoutMixin() {

    // same images
    Image image1 = new Image("http://image.com", 20, 30);
    Image image2 = new Image("http://image.com", 20, 30);
    assertEqualObjectsAndStrings(image1, image2);

    // image with different url
    assertUnequalObjectsAndStrings(image1, new Image("http://xxx.com", 20, 30));

    // image without size
    assertUnequalObjectsAndStrings(image1, new Image("http://image.com"));

  }

}
