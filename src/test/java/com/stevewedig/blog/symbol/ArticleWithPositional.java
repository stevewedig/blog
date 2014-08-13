package com.stevewedig.blog.symbol;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.value_objects.ValueMixin;

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

  @Override
  protected Object[] fields() {
    return array("url", url, "title", title, "published", published, "author", author, "tags", tags);
  }

  // ===========================================================================
  // constructor
  // ===========================================================================

  public ArticleWithPositional(String url, String title, Optional<Integer> published,
      String author, ImmutableSet<String> tags) {
    super();
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

}
