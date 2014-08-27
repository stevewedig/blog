package com.stevewedig.blog.symbol;

import static com.stevewedig.blog.symbol.SymbolLib.symbol;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.value_objects.ValueMixin;

class ArticleWithSymbols extends ValueMixin {

  // ===========================================================================
  // symbols
  // ===========================================================================

  public static Symbol<String> $url = symbol("url");
  public static Symbol<String> $title = symbol("title");
  public static Symbol<Integer> $published = symbol("published");
  public static Symbol<String> $author = symbol("author");
  public static Symbol<ImmutableSet<String>> $tags = symbol("$tags");

  // ===========================================================================
  // state
  // ===========================================================================

  private final String url;
  private final String title;
  private final Optional<Integer> published; // optional
  private final String author; // nullable
  private final ImmutableSet<String> tags; // default

  private static ImmutableSet<String> defaultTags = ImmutableSet.of();

  @Override
  protected Object[] fields() {
    return array("url", url, "title", title, "published", published, "author", author, "tags", tags);
  }

  // ===========================================================================
  // constructor
  // ===========================================================================

  // constructor accepting symbol params
  public ArticleWithSymbols(SymbolMap params) {
    url = params.get($url);
    title = params.get($title);
    published = params.getOptional($published);
    author = params.getNullable($author);
    tags = params.getDefault($tags, defaultTags);

    // used for copy() and copyWithMutations()
    this.params = params.solid();
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
  // copy (clone)
  // ===========================================================================

  private final SymbolMap.Solid params;

  public ArticleWithSymbols copy() {
    
    return new ArticleWithSymbols(params);
  }

  public ArticleWithSymbols copyWithMutations(SymbolMap mutations) {

    return new ArticleWithSymbols(params.fluid().putAll(mutations));
  }

}
