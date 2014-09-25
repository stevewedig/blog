package com.stevewedig.blog.translate;

import com.stevewedig.blog.util.LambdaLib.Fn1;

/**
 * Translator related utilities.
 */
public abstract class TranslatorLib {

  /**
   * Convert a parser to a lambda that parses.
   */
  public static <Syntax, Model> Fn1<Syntax, Model> parserFn(final Parser<Syntax, Model> parser) {
    return new Fn1<Syntax, Model>() {
      @Override
      public Model apply(Syntax syntax) {
        return parser.parse(syntax);
      }
    };
  }

  /**
   * Convert a write to a lambda that writes.
   */
  public static <Syntax, Model> Fn1<Model, Syntax> writerFn(final Writer<Syntax, Model> writer) {
    return new Fn1<Model, Syntax>() {
      @Override
      public Syntax apply(Model model) {
        return writer.write(model);
      }
    };
  }
  
}
