package com.stevewedig.blog.symbol;

import static com.stevewedig.blog.symbol.SymbolLib.symbol;
import static com.stevewedig.blog.symbol.SymbolLib.map;

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

  @Override
  protected Object[] fields() {
    return array("url", url, "title", title, "published", published, "author", author, "tags", tags);
  }

  // ===========================================================================
  // constructor
  // ===========================================================================

  // constructor accepting symbol params
  public ArticleWithSymbols(SymbolMap map) {
    url = map.get($url);
    title = map.get($title);
    published = map.getOptional($published);
    author = map.getNullable($author);
    tags = map.getDefault($tags, defaultTags); // TODO
  }

  private static ImmutableSet<String> defaultTags = ImmutableSet.of();

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
  // copy / clone
  // ===========================================================================

  private SymbolMap.Fluid params() {
    // TODO really ugly that published is not Optional as a symbol
    return map().put($url, url).put($title, title)
        .put($published, published.isPresent() ? published.get() : null).put($author, author)
        .put($tags, tags);
  }

  public ArticleWithSymbols copyWithMutations(SymbolMap mutations) {

    SymbolMap.Fluid params = params();

    params.putAll(mutations);

    return new ArticleWithSymbols(params);
  }

  public ArticleWithSymbols copy() {
    return copyWithMutations(map());
  }

}
