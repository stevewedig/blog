package com.stevewedig.blog.util;

public abstract class NumLib {

  // bit check could be faster:
  // http://stackoverflow.com/questions/7342237/check-whether-number-is-even-or-odd
  public static boolean isEven(int num) {
    return num % 2 == 0;
  }

  public static boolean isOdd(int num) {
    return num % 2 != 0;
  }

}
