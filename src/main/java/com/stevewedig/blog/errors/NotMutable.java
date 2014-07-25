package com.stevewedig.blog.errors;


public class NotMutable extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public NotMutable() {
    super();
  }

  public NotMutable(String string) {
    super(string);
  }

}
