package com.stevewedig.blog.errors;

import static com.stevewedig.blog.util.StrLib.formatN;


/**
 * Used for situation that mean we have a bug in the code.
 */
public class Bug extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public Bug() {
    super();
  }

  public Bug(String string) {
    super(string);
  }

  public Bug(String template, Object... parts) {
    super(formatN(template, parts));
  }

}
