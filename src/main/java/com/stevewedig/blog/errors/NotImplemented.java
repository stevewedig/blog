package com.stevewedig.blog.errors;


public class NotImplemented extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public NotImplemented() {
    super();
  }

  public NotImplemented(String string) {
    super(string);
  }

}
