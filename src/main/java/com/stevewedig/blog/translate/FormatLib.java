package com.stevewedig.blog.translate;

import java.util.*;
import java.util.Map.Entry;

import com.google.common.base.*;
import com.google.common.collect.*;


/**
 * Format related utilities and common formats such as str, int, bool, etc.
 */
public abstract class FormatLib {

  // ===========================================================================
  // chain
  // ===========================================================================

  /**
   * Chain together a format and a translator to create the combined format.
   */
  public static <A, B> Format<B> chain(final Format<A> format, final Translator<A, B> translator) {
    return new Format<B>() {

      @Override
      public B parse(String str) {
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
  // str format (passthrough)
  // ===========================================================================

  /**
   * A String format which is a pass through (no-op).
   */
  public static Format<String> strFormat = new Format<String>() {
    @Override
    public String parse(String syntax) {
      return syntax;
    }

    @Override
    public String write(String model) {
      return model;
    }
  };

  // ===========================================================================
  // int format
  // ===========================================================================

  /**
   * An Integer format using Integer.parseInt.
   */
  public static Format<Integer> intFormat = new Format<Integer>() {
    @Override
    public Integer parse(String syntax) {
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

  // ===========================================================================
  // float format
  // ===========================================================================

  /**
   * A Float format using Float.parseFloat.
   */
  public static Format<Float> floatFormat = new Format<Float>() {
    @Override
    public Float parse(String syntax) {
      try {
        return Float.parseFloat(syntax);
      } catch (NumberFormatException e) {
        throw new ParseError(e);
      }
    }

    @Override
    public String write(Float model) {
      return model.toString();
    }
  };

  // ===========================================================================
  // double format
  // ===========================================================================

  /**
   * A Double format using Double.parseDouble.
   */
  public static Format<Double> doubleFormat = new Format<Double>() {
    @Override
    public Double parse(String syntax) {
      try {
        return Double.parseDouble(syntax);
      } catch (NumberFormatException e) {
        throw new ParseError(e);
      }
    }

    @Override
    public String write(Double model) {
      return model.toString();
    }
  };

  // ===========================================================================
  // bool format
  // ===========================================================================

  /**
   * A Boolean format using true or false.
   */
  public static Format<Boolean> boolJsonFormat = new Format<Boolean>() {
    @Override
    public Boolean parse(String syntax) {
      if (syntax.equals("true"))
        return true;
      else if (syntax.equals("false"))
        return false;
      else
        throw new ParseError("invalid json boolean: %s", syntax);
    }

    @Override
    public String write(Boolean model) {
      return model.toString();
    }
  };

  // ===================================

  /**
   * A Boolean format using 0 or 1.
   */
  public static Format<Boolean> boolFlagFormat = new Format<Boolean>() {
    @Override
    public Boolean parse(String syntax) {
      switch (syntax) {
        case "0":
          return false;
        case "1":
          return true;
        default:
          throw new ParseError("invalid boolean flag: %s", syntax);
      }
    }

    @Override
    public String write(Boolean model) {
      return model ? "1" : "0";
    }
  };

  // ===========================================================================
  // set
  // ===========================================================================

  /**
   * A function for creating Set formats.
   */
  public static <Item> Format<ImmutableSet<Item>> genSetFormat(final Format<Item> itemFormat,
      String delimiter) {

    final Splitter splitter = Splitter.on(delimiter);
    final Joiner joiner = Joiner.on(delimiter);

    return new Format<ImmutableSet<Item>>() {

      @Override
      public ImmutableSet<Item> parse(String collectionStr) throws ParseError {

        if (collectionStr.length() == 0)
          return ImmutableSet.of();

        ImmutableSet.Builder<Item> items = ImmutableSet.builder();

        for (String itemStr : splitter.split(collectionStr)) {
          Item item = itemFormat.parse(itemStr);
          items.add(item);
        }

        return items.build();
      }

      @Override
      public String write(ImmutableSet<Item> items) {

        List<String> itemStrs = new ArrayList<>();
        for (Item item : items) {
          String itemStr = itemFormat.write(item);
          itemStrs.add(itemStr);
        }

        return joiner.join(itemStrs);
      }
    };
  }

  /**
   * A String ImmutableSet format with comma delimiters.
   */
  public static Format<ImmutableSet<String>> strCommaSetFormat = genSetFormat(strFormat, ", ");

  /**
   * An Integer ImmutableSet format with comma delimiters.
   */
  public static Format<ImmutableSet<Integer>> intCommaSetFormat = genSetFormat(intFormat, ", ");

  /**
   * A function for parsing String ImmutableSets which is more convenient than writing
   * ImmutableSet.of("a", "b", ...).
   */
  public static ImmutableSet<String> parseSet(String string) {
    return strCommaSetFormat.parse(string);
  }

  // ===========================================================================
  // list
  // ===========================================================================

  /**
   * A function for creating List formats.
   */
  public static <Item> Format<ImmutableList<Item>> genListFormat(final Format<Item> itemFormat,
      String delimiter) {

    final Splitter splitter = Splitter.on(delimiter);
    final Joiner joiner = Joiner.on(delimiter);

    return new Format<ImmutableList<Item>>() {

      @Override
      public ImmutableList<Item> parse(String collectionStr) throws ParseError {

        if (collectionStr.length() == 0)
          return ImmutableList.of();

        ImmutableList.Builder<Item> items = ImmutableList.builder();

        for (String itemStr : splitter.split(collectionStr)) {
          Item item = itemFormat.parse(itemStr);
          items.add(item);
        }

        return items.build();
      }

      @Override
      public String write(ImmutableList<Item> items) {

        List<String> itemStrs = new ArrayList<>();
        for (Item item : items) {
          String itemStr = itemFormat.write(item);
          itemStrs.add(itemStr);
        }

        return joiner.join(itemStrs);
      }
    };
  }

  /**
   * A String ImmutableList format with comma delimiters.
   */
  public static Format<ImmutableList<String>> strCommaListFormat = genListFormat(strFormat, ", ");

  /**
   * An Integer ImmutableList format with comma delimiters.
   */
  public static Format<ImmutableList<Integer>> intCommaListFormat = genListFormat(intFormat, ", ");

  /**
   * A function for parsing String ImmutableLists which is more convenient than writing
   * ImmutableList.of("a", "b", ...).
   */
  public static ImmutableList<String> parseList(String string) {
    return strCommaListFormat.parse(string);
  }

  // ===========================================================================
  // multimap
  // ===========================================================================

  /**
   * An ImmutableSetMultimap format with String keys and values, with comma delimited entries, and
   * with equal delimiting the key and value of an entry.
   */
  public static Format<ImmutableSetMultimap<String, String>> strMultimapFormat =
      new Format<ImmutableSetMultimap<String, String>>() {
        @Override
        public ImmutableSetMultimap<String, String> parse(String syntax) throws ParseError {

          if (syntax.length() == 0)
            return ImmutableSetMultimap.of();

          ImmutableSetMultimap.Builder<String, String> map = ImmutableSetMultimap.builder();

          for (String entryStr : commaSplitter.trimResults().split(syntax)) {

            List<String> entryParts = equalsSplitter.trimResults().splitToList(entryStr);

            String key = entryParts.get(0);
            String value = entryParts.get(1);

            map.put(key, value);
          }

          return map.build();

        }

        @Override
        public String write(ImmutableSetMultimap<String, String> model) {

          List<String> entryStrs = new ArrayList<>();

          for (Entry<String, String> entry : model.entries()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String entryStr = equalsJoiner.join(key, value);
            entryStrs.add(entryStr);
          }

          return commaJoiner.join(entryStrs);
        }
      };

  /**
   * A function for parsing ImmutableSetMultimaps with String keys and values, which is more
   * convenient than writing ImmutableSetMultimap.of("a", "b", ...).
   */
  public static ImmutableSetMultimap<String, String> parseMultimap(String string) {
    return FormatLib.strMultimapFormat.parse(string);
  }

  private static final Splitter commaSplitter = Splitter.on(",");
  private static final Joiner commaJoiner = Joiner.on(", ");

  private static final Splitter equalsSplitter = Splitter.on("=");
  private static final Joiner equalsJoiner = Joiner.on(" = ");

}
