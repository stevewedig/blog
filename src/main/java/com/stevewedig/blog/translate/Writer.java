package com.stevewedig.blog.translate;

public interface Writer<Syntax, Model> {

  Syntax write(Model model);
}
