package com.stevewedig.blog.value_objects;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;


public class TestValueMixinExample {

  // ===========================================================================
  // Image
  // ===========================================================================

  static class Image extends ValueMixin {

    private final String url;
    private final Optional<Integer> height;
    private final Optional<Integer> width;

    @Override
    protected Object[] fields() {
      return array("url", url, "height", height, "width", width);
    }

    public Image(String url, Optional<Integer> height, Optional<Integer> width) {
      super();
      this.url = url;
      this.height = height;
      this.width = width;
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

  }

  // ===========================================================================
  // Article
  // ===========================================================================

  static class Article extends ValueMixin {

    private final String url;
    private final String title;
    private final Optional<Image> image;

    @Override
    protected Object[] fields() {
      return array("url", url, "title", title, "image", image);
    }

    public Article(String url, String title, Optional<Image> image) {
      super();
      this.url = url;
      this.title = title;
      this.image = image;
    }

    public String url() {
      return url;
    }

    public String title() {
      return title;
    }

    public Optional<Image> image() {
      return image;
    }
  }

  // ===========================================================================
  // Feed
  // ===========================================================================

  static class Feed extends ValueMixin {

    private final String url;
    private final String title;
    private final ImmutableList<Article> articles;

    @Override
    protected Object[] fields() {
      return array("url", url, "title", title, "articles", articles);
    }

    public Feed(String url, String title, ImmutableList<Article> articles) {
      super();
      this.url = url;
      this.title = title;
      this.articles = articles;
    }

    public String url() {
      return url;
    }

    public String title() {
      return title;
    }

    public ImmutableList<Article> articles() {
      return articles;
    }
    
  }


  // ===========================================================================
  // test
  // ===========================================================================

  @Test
  public void testValueMixinExample() {

    
    
  }

}
