package com.stevewedig.blog.errors;

import static com.stevewedig.blog.util.StrLib.formatVar;


/**
 * Used for assertions that only fail if a library has a bug.
 * 
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
    super(formatVar(template, parts));
  }

}
