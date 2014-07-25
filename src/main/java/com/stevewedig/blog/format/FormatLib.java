package com.stevewedig.blog.format;


public abstract class FormatLib {

  // ===========================================================================
  // chain
  // ===========================================================================

  public static <A, B> Format<B> chain(final Format<A> format, final Translator<A, B> translator) {
    return new Format<B>() {

      @Override
      public B parse(String str) throws ParseError {
        A a = format.parse(str);
        B b = translator.parse(a);
        return b;
      }

      @Override
      public String write(B b) {
        A a = translator.write(b);
        String str = format.write(a);
        return str;
      }
    };
  }

  // ===========================================================================
  // int format
  // ===========================================================================

  public static Format<Integer> integer = new Format<Integer>() {
    @Override
    public Integer parse(String syntax) throws ParseError {
      try {
        return Integer.parseInt(syntax);
      } catch (NumberFormatException e) {
        throw new ParseError(e);
      }
    }

    @Override
    public String write(Integer model) {
      return model.toString();
    }
  };
}
