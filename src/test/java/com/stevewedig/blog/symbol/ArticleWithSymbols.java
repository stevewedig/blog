package com.stevewedig.blog.symbol;

import static com.stevewedig.blog.symbol.SymbolLib.map;
import static com.stevewedig.blog.symbol.SymbolLib.symbol;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.value_objects.ValueMixin;

// ValueMixin makes this behave as a value object:
// http://stevewedig.com/2014/07/31/value-objects-in-java-and-python/#java
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

  // http://stevewedig.com/2014/07/31/value-objects-in-java-and-python/#java
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

  // params could be cached because ArticleWithSymbols is immutable
  // (we could actually just save the params.solid() in the constructor)
  private SymbolMap.Solid params() {
    return map().put($url, url).put($title, title).put($published, published.orNull())
        .put($author, author).put($tags, tags).solid();
  }

  // shallow
  public ArticleWithSymbols copy() {

    return new ArticleWithSymbols(params());
  }

  // shallow
  public ArticleWithSymbols copyWithMutations(SymbolMap mutations) {

    return new ArticleWithSymbols(params().fluid().putAll(mutations));
  }

}
