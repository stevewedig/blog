package com.stevewedig.blog.symbol;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.value_objects.ValueMixin;

// ValueMixin makes this behave as a value object:
// http://stevewedig.com/2014/07/31/value-objects-in-java-and-python/#java
class ArticleWithPositional extends ValueMixin {

  // ===========================================================================
  // state
  // ===========================================================================

  // cannot be final due to fluent mutation
  private final String url;
  private final String title;
  private final Optional<Integer> published;
  private final String author; // nullable
  private final ImmutableSet<String> tags;

  private static ImmutableSet<String> defaultTags = ImmutableSet.of();

  // http://stevewedig.com/2014/07/31/value-objects-in-java-and-python/#java
  @Override
  protected Object[] fields() {
    return array("url", url, "title", title, "published", published, "author", author, "tags", tags);
  }

  // ===========================================================================
  // constructor
  // ===========================================================================

  // constructor with positional parameters
  public ArticleWithPositional(String url, String title, Optional<Integer> published,
      String author, ImmutableSet<String> tags) {
    this.url = url;
    this.title = title;
    this.published = published;
    this.author = author;
    this.tags = tags;
  }

  // constructor with fewer parameters, providing default values for the rest
  public ArticleWithPositional(String url, String title) {
    this(url, title, Optional.<Integer>absent(), null, defaultTags);
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

}
