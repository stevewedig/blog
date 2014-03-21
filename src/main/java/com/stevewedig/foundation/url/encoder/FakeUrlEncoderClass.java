package com.stevewedig.foundation.url.encoder;


class FakeUrlEncoderClass extends UrlEncoderMixin {

  @Override
  public String writePathPart(String part) {
    return part;
  }

  @Override
  public String parsePathPart(String writtenPart) {
    return writtenPart;
  }

  @Override
  public String writeQueryPart(String part) {
    return part;
  }

  @Override
  public String parseQueryPart(String writtenPart) {
    return writtenPart;
  }

}
