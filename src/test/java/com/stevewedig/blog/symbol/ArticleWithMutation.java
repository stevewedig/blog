package com.stevewedig.blog.symbol;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.value_objects.ValueMixin;

// ValueMixin makes this behave as a value object:
// http://stevewedig.com/2014/07/31/value-objects-in-java-and-python/#java
class ArticleWithMutation extends ValueMixin {

  // ===========================================================================
  // state
  // ===========================================================================

  // cannot be final due to fluent mutation
  private String url;
  private String title;
  private Optional<Integer> published = Optional.absent(); // optional
  private String author = null; // nullable
  private ImmutableSet<String> tags = ImmutableSet.of(); // default


  // http://stevewedig.com/2014/07/31/value-objects-in-java-and-python/#java
  @Override
  protected Object[] fields() {
    return array("url", url, "title", title, "published", published, "author", author, "tags", tags);
  }

  // ===========================================================================
  // constructor
  // ===========================================================================

  public ArticleWithMutation() {}

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
  // fluent setters
  // ===========================================================================

  public ArticleWithMutation url(String url) {
    this.url = url;
    return this;
  }

  public ArticleWithMutation title(String title) {
    this.title = title;
    return this;
  }

  public ArticleWithMutation published(Integer published) {
    this.published = Optional.of(published);
    return this;
  }

  public ArticleWithMutation author(String author) {
    this.author = author;
    return this;
  }

  public ArticleWithMutation tags(ImmutableSet<String> tags) {
    this.tags = tags;
    return this;
  }

}
