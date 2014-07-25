package com.stevewedig.blog.format;

public interface Parser<Syntax, Model> {

  Model parse(Syntax syntax) throws ParseError;
}
