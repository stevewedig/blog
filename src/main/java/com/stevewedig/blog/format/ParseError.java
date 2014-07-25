package com.stevewedig.blog.format;

import com.stevewedig.blog.util.StrLib;

public class ParseError extends Exception {

  private static final long serialVersionUID = 1L;
  
  private String message;
  
  public ParseError(String message) {
    this.message = message;
  }

  public ParseError(NumberFormatException e) {
    this(e.toString());
  }
  
  @Override
  public String toString() {
    return StrLib.objectString(getClass(), "message", message);
  }
}
