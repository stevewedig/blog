package com.stevewedig.blog.symbol;

import static com.stevewedig.blog.symbol.SymbolLib.map;
import static com.stevewedig.blog.symbol.SymbolLib.symbol;
import static com.stevewedig.blog.translate.FormatLib.boolFlagFormat;
import static com.stevewedig.blog.translate.FormatLib.boolJsonFormat;
import static com.stevewedig.blog.translate.FormatLib.doubleFormat;
import static com.stevewedig.blog.translate.FormatLib.floatFormat;
import static com.stevewedig.blog.translate.FormatLib.intCommaListFormat;
import static com.stevewedig.blog.translate.FormatLib.intFormat;
import static com.stevewedig.blog.translate.FormatLib.strCommaSetFormat;
import static com.stevewedig.blog.translate.FormatLib.strFormat;
import static org.junit.Assert.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.symbol.format.ConfigFormat;
import com.stevewedig.blog.symbol.format.SymbolFormat;
import com.stevewedig.blog.symbol.format.SymbolFormatLib;
import com.stevewedig.blog.symbol.format.SymbolTranslator;
import com.stevewedig.blog.translate.Format;
import com.stevewedig.blog.translate.ParseError;
import com.stevewedig.blog.util.PropLib;
import com.stevewedig.blog.value_objects.ValueMixin;

public class TestSymbolFormat {

  // ===========================================================================
  // Step 1: Create symbols with heterogeneous value types
  // ===========================================================================

  public static Symbol<String> $notUsed = symbol("notUsed");
  public static Symbol<String> $userName = symbol("userName");
  public static Symbol<Integer> $threadCount = symbol("threadCount");
  public static Symbol<Float> $version = symbol("version");
  public static Symbol<Double> $precision = symbol("precision");
  public static Symbol<Boolean> $createTables = symbol("createTables");
  public static Symbol<Boolean> $launchNukes = symbol("launchNukes");
  public static Symbol<LogLevel> $logLevel = symbol("logLevel");
  public static Symbol<Point> $point = symbol("point");
  public static Symbol<ImmutableSet<String>> $adminEmails = symbol("adminEmails");
  public static Symbol<ImmutableList<Integer>> $thresholds = symbol("thresholds");

  // ===========================================================================
  // test
  // ===========================================================================

  @Test
  public void testSymbolFormat() {

    // =================================
    // Step 2: Create translator, associating symbols with formats
    // =================================

    // $userName has default format, which is strFormat
    // $createTables and $logLevels are both Boolean, but use different formats
    SymbolTranslator translator =
        SymbolFormatLib.translator().add($notUsed, strFormat).add($userName)
            .add($threadCount, intFormat).add($version, floatFormat).add($precision, doubleFormat)
            .add($createTables, boolFlagFormat).add($launchNukes, boolJsonFormat)
            .add($logLevel, logLevelFormat).add($point, Point.format)
            .add($adminEmails, strCommaSetFormat).add($thresholds, intCommaListFormat).build();

    // =================================
    // Step 3: Get a ConfigFormat
    // =================================

    // This one is backed by java.util.Properties
    ConfigFormat configFormat = PropLib.format;

    // =================================
    // Step 4: Chain a ConfigFormat and a SymbolTranslator to get a SymbolFormat
    // =================================

    SymbolFormat format = SymbolFormatLib.format(configFormat, translator);

    // =================================
    // Step 5: Use SymbolFormat to convert between fileContent and symbolMap
    // =================================

    SymbolMap symbolMap =
        map().put($userName, "bob").put($threadCount, 4).put($version, 2.3f).put($precision, 0.01d)
            .put($createTables, true).put($launchNukes, false).put($logLevel, LogLevel.warning)
            .put($point, new Point(7, 8)).put($adminEmails, ImmutableSet.of("alice@example.com"))
            .put($thresholds, ImmutableList.of(10, 20, 30)).immutable();

    String fileContent =
        "userName = bob\n" + "threadCount = 4\n" + "version = 2.3\n" + "precision = 0.01\n"
            + "createTables = 1\n" + "launchNukes = false\n" + "logLevel = warning\n"
            + "point = {\"x\": 7, \"y\": 8}\n" + "adminEmails = alice@example.com\n"
            + "thresholds = 10, 20, 30\n";

    assertEquals(symbolMap, format.parse(fileContent));

    // writing is non-deterministic, so easiest to test it by parsing it back out
    assertEquals(symbolMap, format.parse(format.write(symbolMap)));

    // System.out.println(format.write(symbolMap));
  }

  // ===========================================================================
  // LogLevel enum with toString/valueOf format
  // ===========================================================================

  static enum LogLevel {
    fatal, error, warning, info, debug
  }

  Format<LogLevel> logLevelFormat = new Format<LogLevel>() {
    @Override
    public LogLevel parse(String syntax) throws ParseError {
      return LogLevel.valueOf(syntax);
    }

    @Override
    public String write(LogLevel model) {
      return model.toString();
    }
  };

  // ===========================================================================
  // Point class with JSON format
  // ===========================================================================

  // http://stevewedig.com/2014/07/31/value-objects-in-java-and-python/
  static class Point extends ValueMixin {

    // =================================
    // state
    // =================================

    private final int x;
    private final int y;

    @Override
    protected Object[] fields() {
      return array("x", x, "y", y);
    }

    // =================================
    // constructor
    // =================================

    public Point(int x, int y) {
      super();
      this.x = x;
      this.y = y;
    }

    // =================================
    // getters
    // =================================

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    // =================================
    // format
    // =================================

    // obviously you would want to use a real JSON library
    public static Format<Point> format = new Format<Point>() {

      @Override
      public Point parse(String json) throws ParseError {

        Pattern pattern = Pattern.compile("x\":\\s*(\\d+).*y\":\\s*(\\d+)");
        Matcher matcher = pattern.matcher(json);
        matcher.find();

        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));

        return new Point(x, y);
      }

      @Override
      public String write(Point point) {
        return "{\"x\": " + point.getX() + ", \"y\": " + point.getY() + "}";
      }
    };

  }

}
