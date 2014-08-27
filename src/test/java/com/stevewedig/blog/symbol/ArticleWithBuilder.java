package com.stevewedig.blog.symbol;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.value_objects.ValueMixin;

// ValueMixin makes this behave as a value object:
// http://stevewedig.com/2014/07/31/value-objects-in-java-and-python/#java
class ArticleWithBuilder extends ValueMixin {

  // ===========================================================================
  // state
  // ===========================================================================

  private final String url;
  private final String title;
  private final Optional<Integer> published; // optional
  private final String author; // nullable
  private final ImmutableSet<String> tags; // default

  // http://stevewedig.com/2014/07/31/value-objects-in-java-and-python/#java
  @Override
  protected Object[] fields() {
    return array("url", url, "title", title, "published", published, "author", author, "tags", tags);
  }

  // ===========================================================================
  // constructor
  // ===========================================================================

  public ArticleWithBuilder(String url, String title, Optional<Integer> published, String author,
      ImmutableSet<String> tags) {
    this.url = url;
    this.title = title;
    this.published = published;
    this.author = author;
    this.tags = tags;
  }

  // ===========================================================================
  // getters
  // ===========================================================================

  public String getUrl() {
    return url;
  }

  public String getTitle() {
    return title;
  }

  public Optional<Integer> getPublished() {
    return published;
  }

  public String getAuthor() {
    return author;
  }

  public ImmutableSet<String> getTags() {
    return tags;
  }

  // ===========================================================================
  // Builder
  // ===========================================================================

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String url;
    private String title;
    private Optional<Integer> published = Optional.absent(); // optional
    private String author = null; // nullable
    private ImmutableSet<String> tags = ImmutableSet.of(); // default

    public ArticleWithBuilder build() {
      return new ArticleWithBuilder(url, title, published, author, tags);
    }

    // =================================
    // fluent setters
    // =================================

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder published(Integer published) {
      this.published = Optional.of(published);
      return this;
    }

    public Builder author(String author) {
      this.author = author;
      return this;
    }

    public Builder tags(ImmutableSet<String> tags) {
      this.tags = tags;
      return this;
    }

  }

}
