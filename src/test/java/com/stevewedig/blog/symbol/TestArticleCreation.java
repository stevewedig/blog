package com.stevewedig.blog.symbol;

import static com.stevewedig.blog.symbol.ArticleWithSymbols.$author;
import static com.stevewedig.blog.symbol.ArticleWithSymbols.$published;
import static com.stevewedig.blog.symbol.ArticleWithSymbols.$tags;
import static com.stevewedig.blog.symbol.ArticleWithSymbols.$title;
import static com.stevewedig.blog.symbol.ArticleWithSymbols.$url;
import static com.stevewedig.blog.symbol.SymbolLib.map;
import static com.stevewedig.blog.value_objects.ObjectHelperLib.assertStateEquals;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

public class TestArticleCreation {

  @Test
  public void testArticleFull() {

    // =================================
    // positional
    // =================================

    ArticleWithPositional positional =
        new ArticleWithPositional("http://url.com", "title", Optional.of(123), "bob",
            ImmutableSet.of("software"));

    // =================================
    // fluent mutable
    // =================================

    ArticleWithMutation mutable =
        new ArticleWithMutation().url("http://url.com").title("title").published(123).author("bob")
            .tags(ImmutableSet.of("software"));

    assertStateEquals(positional, mutable);

    // =================================
    // fluent builder
    // =================================

    ArticleWithBuilder built =
        ArticleWithBuilder.builder().url("http://url.com").title("title").published(123)
            .author("bob").tags(ImmutableSet.of("software")).build();

    assertStateEquals(positional, built);

    // =================================
    // symbol
    // =================================

    ArticleWithSymbols symbol =
        new ArticleWithSymbols(map().put($url, "http://url.com").put($title, "title")
            .put($published, 123).put($author, "bob").put($tags, ImmutableSet.of("software")));

    assertStateEquals(positional, symbol);
  }

  // ===========================================================================

  @Test
  public void testArticleSparse() {

    // =================================
    // positional
    // =================================

    ArticleWithPositional positional = new ArticleWithPositional("http://url.com", "title");

    // =================================
    // fluent mutable
    // =================================

    ArticleWithMutation mutable = new ArticleWithMutation().url("http://url.com").title("title");

    assertStateEquals(positional, mutable);

    // =================================
    // fluent builder
    // =================================

    ArticleWithBuilder built =
        ArticleWithBuilder.builder().url("http://url.com").title("title").build();

    assertStateEquals(positional, built);

    // =================================
    // symbols with defaults
    // =================================

    ArticleWithSymbols symbolWithDefaults =
        new ArticleWithSymbols(map().put($url, "http://url.com").put($title, "title"));

    assertStateEquals(positional, symbolWithDefaults);

    // =================================
    // symbols without defaults // TODO do we need this?
    // =================================

    ArticleWithSymbols symbolWithoutDefaults =
        new ArticleWithSymbols(map().put($url, "http://url.com").put($title, "title")
            .put($published, null).put($author, null).put($tags, null));

    assertStateEquals(positional, symbolWithoutDefaults);


  }

}
