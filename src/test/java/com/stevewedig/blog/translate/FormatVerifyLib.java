package com.stevewedig.blog.translate;

import static org.junit.Assert.assertEquals;

import com.google.common.base.Optional;
import com.stevewedig.blog.errors.NotThrown;

public class FormatVerifyLib {

  public static <Model> void verify(Format<Model> format, Model model, String syntax) {
    verify(format, model, syntax, Optional.<String>absent());
  }

  public static <Model> void verify(Format<Model> format, Model model, String syntax,
      String invalidSyntax) {
    verify(format, model, syntax, Optional.of(invalidSyntax));
  }

  public static <Model> void verify(Format<Model> format, Model model, String syntax,
      Optional<String> invalidSyntax) {

    assertEquals(syntax, format.write(model));

    assertEquals(model, format.parse(syntax));

    if (invalidSyntax.isPresent()) {
      try {
        format.parse(invalidSyntax.get());
        throw new NotThrown(ParseError.class);
      } catch (ParseError e) {
      }
    }
  }

}
