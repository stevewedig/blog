package com.stevewedig.blog.format;

public interface Writer<Syntax, Model> {

  Syntax write(Model model);
}
