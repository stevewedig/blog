package com.stevewedig.blog.symbol;

import static com.stevewedig.blog.symbol.ArticleWithSymbols.*;
import static com.stevewedig.blog.symbol.SymbolLib.map;
import static com.stevewedig.blog.value_objects.ObjectHelperLib.assertStateEquals;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

public class TestArticleCreation {

  // ===========================================================================
  // create without defaults
  // ===========================================================================

  @Test
  public void testArticleWithoutDefaults() {

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

    ArticleWithSymbols param =
        new ArticleWithSymbols(map().put($url, "http://url.com").put($title, "title")
            .put($published, 123).put($author, "bob").put($tags, ImmutableSet.of("software")));

    assertStateEquals(positional, param);
  }

  // ===========================================================================
  // create with defaults
  // ===========================================================================

  @Test
  public void testArticleWithDefaults() {

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

    ArticleWithSymbols paramWithDefaults =
        new ArticleWithSymbols(map().put($url, "http://url.com").put($title, "title"));

    assertStateEquals(positional, paramWithDefaults);

    // =================================
    // symbols adapting nulls to defaults
    // =================================

    // adapting null to defaults
    ArticleWithSymbols paramWithoutDefaults =
        new ArticleWithSymbols(map().put($url, "http://url.com").put($title, "title")
            .put($published, null).put($author, null).put($tags, null));

    assertStateEquals(positional, paramWithoutDefaults);


  }

  // ===========================================================================
  // copy with mutations
  // ===========================================================================

  @Test
  public void testCopyWithMutation() {

    ArticleWithSymbols sparse =
        new ArticleWithSymbols(map().put($url, "http://url.com").put($title, "title"));


    ArticleWithSymbols full =
        new ArticleWithSymbols(map().put($url, "http://url.com").put($title, "title")
            .put($published, 123).put($author, "bob").put($tags, ImmutableSet.of("software")));

    // =================================
    // copy
    // =================================

    assertEquals(sparse, sparse.copy());

    assertThat(sparse, not(sameInstance(sparse.copy())));

    assertEquals(full, full.copy());

    assertThat(full, not(sameInstance(full.copy())));

    // =================================
    // copyWithMutations
    // =================================

    assertEquals(
        full,
        sparse.copyWithMutations(map().put($published, 123).put($author, "bob")
            .put($tags, ImmutableSet.of("software"))));

    // adapting null to defaults
    assertEquals(sparse,
        full.copyWithMutations(map().put($published, null).put($author, null).put($tags, null)));

  }

}
