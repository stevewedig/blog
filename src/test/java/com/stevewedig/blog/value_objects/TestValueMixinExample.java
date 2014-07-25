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
      this.url = url;
      this.title = title;
      this.image = image;
    }

    public Article(String url, String title, Image image) {
      this(url, title, Optional.of(image));
    }

    public Article(String url, String title) {
      this(url, title, Optional.<Image>absent());
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
      this.url = url;
      this.title = title;
      this.articles = articles;
    }

    public Feed(String url, String title, Article... articles) {
      this(url, title, ImmutableList.copyOf(articles));
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

    // same images
    Image image1 = new Image("http://image.com", 20, 30);
    Image image2 = new Image("http://image.com", 20, 30);
    CompareLib.assertEqualObjectsAndStrings(image1, image2);

    // image with different url
    CompareLib.assertUnequalObjectsAndStrings(image1, new Image("http://xxx.com", 20, 30));

    // image without size
    CompareLib.assertUnequalObjectsAndStrings(image1, new Image("http://image.com"));

    // same articles
    Article article1 = new Article("http://article.com", "My Article", image1);
    Article article2 = new Article("http://article.com", "My Article", image2);
    CompareLib.assertEqualObjectsAndStrings(article1, article2);

    // article with different url
    CompareLib.assertUnequalObjectsAndStrings(article1, new Article("http://xxx.com", "My Article",
        image1));

    // article with different title
    CompareLib.assertUnequalObjectsAndStrings(article1, new Article("http://article.com", "xxx",
        image1));

    // article with different image
    CompareLib.assertUnequalObjectsAndStrings(article1, new Article("http://article.com",
        "My Article", new Image("http://xxx.com")));

    // article without an image
    CompareLib.assertUnequalObjectsAndStrings(article1, new Article("http://article.com",
        "My Article"));

    // same feeds
    Article article3 = new Article("http://article3.com", "My Article 3");
    Feed feed1 = new Feed("http://feed.com", "My Feed", article1, article3);
    Feed feed2 = new Feed("http://feed.com", "My Feed", article1, article3);
    CompareLib.assertEqualObjectsAndStrings(feed1, feed2);

    // feed with different url
    CompareLib.assertUnequalObjectsAndStrings(feed1, new Feed("http://xxx.com", "My Feed",
        article1, article3));

    // feed with different title
    CompareLib.assertUnequalObjectsAndStrings(feed1, new Feed("http://feed.com", "xxx", article1,
        article3));

    // feed with different articles
    CompareLib.assertUnequalObjectsAndStrings(feed1, new Feed("http://feed.com", "My Feed",
        article1));

    // feed with different article order
    CompareLib.assertUnequalObjectsAndStrings(feed1, new Feed("http://feed.com", "My Feed",
        article3, article1));

    // feed without articles
    CompareLib.assertUnequalObjectsAndStrings(feed1, new Feed("http://feed.com", "My Feed"));

  }

}
