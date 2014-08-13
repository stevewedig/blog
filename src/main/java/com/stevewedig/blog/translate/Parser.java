package com.stevewedig.blog.translate;

public interface Parser<Syntax, Model> {

  Model parse(Syntax syntax) throws ParseError;
}
