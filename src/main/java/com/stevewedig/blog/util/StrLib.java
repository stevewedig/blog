package com.stevewedig.blog.util;

import com.google.common.base.*;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.collect.ImmutableList;

/**
 * String utilities not available in Guava's Strings or Apache commons StringUtils.
 */
public abstract class StrLib {

  // ===========================================================================
  // classAndStateString
  // ===========================================================================

  /**
   * For implementing toString() to print an object's state.
   */
  public static String classAndStateString(Class<?> type, Object... fieldNamesAndValues) {
    String name = StrLib.simpleClassName(type);
    return classAndStateString(name, fieldNamesAndValues);
  }

  /**
   * For implementing toString() to print an object's state.
   */
  public static String classAndStateString(String typeName, Object... fieldNamesAndValues) {

    ToStringHelper builder = MoreObjects.toStringHelper(typeName);

    int fieldCount = fieldNamesAndValues.length / 2;

    for (int i = 0; i < fieldCount; i++) {
      String name = (String) fieldNamesAndValues[i * 2];
      Object value = fieldNamesAndValues[i * 2 + 1];
      builder.add(name, value);
    }

    return builder.toString();
  }

  // objectClass.getSimpleName() is not implemented in GWT, so reimplement
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
    return formatN(template);
  }

  public static String format(String template, Object a) {
    return formatN(template, a);
  }

  public static String format(String template, Object a, Object b) {
    return formatN(template, a, b);
  }

  public static String format(String template, Object a, Object b, Object c) {
    return formatN(template, a, b, c);
  }

  public static String format(String template, Object a, Object b, Object c, Object d) {
    return formatN(template, a, b, c, d);
  }

  public static String format(String template, Object a, Object b, Object c, Object d, Object e) {
    return formatN(template, a, b, c, d, e);
  }

  public static String format(String template, Object a, Object b, Object c, Object d, Object e,
      Object f) {
    return formatN(template, a, b, c, d, e, f);
  }

  public static String format(String template, Object a, Object b, Object c, Object d, Object e,
      Object f, Object g) {
    return formatN(template, a, b, c, d, e, f, g);
  }

  // ===================================

  public static String formatN(String template, Object... items) {
    String str = template;
    for (Object item : items)
      str = str.replaceFirst("%s", quoteReplacement(item == null ? "null" : item.toString()));
    return str;
  }

  // this is strange, look into this more later (related to Matcher.quoteReplacement)
  // http://stackoverflow.com/questions/11913709/why-does-replaceall-fail-with-illegal-group-reference
  private static String quoteReplacement(String str) {
    str = str.replace("\\", "\\\\");
    str = str.replace("$", "\\$");
    return str;
  }

  // ===========================================================================
  // print() combines format() and println()
  // ===========================================================================

  public static void print(String template) {
    printN(template);
  }

  public static void print(String template, Object a) {
    printN(template, a);
  }

  public static void print(String template, Object a, Object b) {
    printN(template, a, b);
  }

  public static void print(String template, Object a, Object b, Object c) {
    printN(template, a, b, c);
  }

  public static void print(String template, Object a, Object b, Object c, Object d) {
    printN(template, a, b, c, d);
  }

  public static void print(String template, Object a, Object b, Object c, Object d, Object e) {
    printN(template, a, b, c, d, e);
  }

  public static void print(String template, Object a, Object b, Object c, Object d, Object e,
      Object f) {
    printN(template, a, b, c, d, e, f);
  }

  public static void print(String template, Object a, Object b, Object c, Object d, Object e,
      Object f, Object g) {
    printN(template, a, b, c, d, e, f, g);
  }

  // ===================================

  public static void printN(String template, Object... items) {
    System.out.println(formatN(template, items));
  }

}
