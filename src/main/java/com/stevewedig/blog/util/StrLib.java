package com.stevewedig.blog.util;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;


public abstract class StrLib {

  // ===========================================================================
  // blank
  // ===========================================================================

  public static boolean isBlank(String str) {
    return str.isEmpty() || str.matches("^\\s*$");
  }

  // ===========================================================================
  // objectStr
  // ===========================================================================

  public static String objectStr(Class<?> type, Object... fieldNamesAndValues) {
    String name = StrLib.simpleClassName(type);
    return objectStr(name, fieldNamesAndValues);
  }

  public static String objectStr(String typeName, Object... fieldNamesAndValues) {

    ToStringHelper builder = Objects.toStringHelper(typeName);

    int fieldCount = fieldNamesAndValues.length / 2;

    for (int i = 0; i < fieldCount; i++) {
      String name = (String) fieldNamesAndValues[i * 2];
      Object value = fieldNamesAndValues[i * 2 + 1];
      builder.add(name, value);
    }

    return builder.toString();
  }

  // objectClass.getSimpleName() is not implemented in GWT
  public static String simpleClassName(Class<?> type) {
    String dottedPath = type.getName();
    ImmutableList<String> parts = ImmutableList.copyOf(Splitter.on('.').split(dottedPath));
    return parts.get(parts.size() - 1);
  }

  // ===========================================================================
  // format() function that is compatible with gwt (however it only supports %s)
  //
  // http://stackoverflow.com/questions/3126232/string-formatter-in-gwt
  // http://www.gwtproject.org/doc/latest/DevGuideCodingBasicsFormatting.html
  // ===========================================================================

  public static String format(String template) {
    return formatVar(template);
  }

  public static String format(String template, Object a) {
    return formatVar(template, a);
  }

  public static String format(String template, Object a, Object b) {
    return formatVar(template, a, b);
  }

  public static String format(String template, Object a, Object b, Object c) {
    return formatVar(template, a, b, c);
  }

  public static String format(String template, Object a, Object b, Object c, Object d) {
    return formatVar(template, a, b, c, d);
  }

  public static String format(String template, Object a, Object b, Object c, Object d, Object e) {
    return formatVar(template, a, b, c, d, e);
  }

  public static String format(String template, Object a, Object b, Object c, Object d, Object e,
      Object f) {
    return formatVar(template, a, b, c, d, e, f);
  }

  public static String format(String template, Object a, Object b, Object c, Object d, Object e,
      Object f, Object g) {
    return formatVar(template, a, b, c, d, e, f, g);
  }

  public static String formatVar(String template, Object... items) {
    String str = template;
    for (Object item : items)
      str = str.replaceFirst("%s", quoteReplacement(item.toString()));
    return str;
  }

  // http://stackoverflow.com/questions/11913709/why-does-replaceall-fail-with-illegal-group-reference
  // Matcher.quoteReplacement
  private static String quoteReplacement(String str) {
    str = str.replace("\\", "\\\\");
    str = str.replace("$", "\\$");
    return str;
  }

  // ===========================================================================
  // print combines format() and println()
  // ===========================================================================

  public static void print(String template) {
    System.out.println(format(template));
  }

  public static void print(String template, Object a) {
    System.out.println(format(template, a));
  }

  public static void print(String template, Object a, Object b) {
    System.out.println(format(template, a, b));
  }

  public static void print(String template, Object a, Object b, Object c) {
    System.out.println(format(template, a, b, c));
  }

  public static void print(String template, Object a, Object b, Object c, Object d) {
    System.out.println(format(template, a, b, c, d));
  }

  public static void print(String template, Object a, Object b, Object c, Object d, Object e) {
    System.out.println(format(template, a, b, c, d, e));
  }

  public static void print(String template, Object a, Object b, Object c, Object d, Object e,
      Object f) {
    System.out.println(format(template, a, b, c, d, e, f));
  }

  public static void print(String template, Object a, Object b, Object c, Object d, Object e,
      Object f, Object g) {
    System.out.println(format(template, a, b, c, d, e, f, g));
  }

}
