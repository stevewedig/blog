package com.stevewedig.blog.format.url_query;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.stevewedig.blog.format.Format;
import com.stevewedig.blog.format.ParseError;

public class UrlQueryFormatClass implements Format<UrlQuery> {

  private static char EQUAL = '=';
  private static char AND = '&';
  
  private Format<String> queryPartFormat;

  public UrlQueryFormatClass(Format<String> queryPartFormat) {
    super();
    this.queryPartFormat = queryPartFormat;
  }

  // ===========================================================================
  // parse
  // ===========================================================================

  @Override
  public UrlQuery parse(String str) throws ParseError {
    requireNonNull(str);

    if (str.length() == 0)
      return UrlQueryLib.query();

    Iterable<String> writtenEntries = Splitter.on(AND).split(str);

    ImmutableMap.Builder<String, String> params = ImmutableMap.builder();
    for (String writtenEntry : writtenEntries) {

      try {
        parseEntry(writtenEntry, params);
      } catch (IllegalArgumentException e) {
        throw new ParseError(str);
      }
    }

    try {
      return UrlQueryLib.query(params.build());
    } catch (IllegalArgumentException e) {
      throw new ParseError(str); // duplicate keys
    }
  }

  private void parseEntry(String writtenEntry, Builder<String, String> params) throws ParseError {


    int offset = writtenEntry.indexOf(EQUAL);

    if (offset < 0)
      throw new IllegalArgumentException(writtenEntry);

    String writtenKey = writtenEntry.substring(0, offset);
    String writtenValue = writtenEntry.substring(offset + 1);

    String key = queryPartFormat.parse(writtenKey);
    String value = queryPartFormat.parse(writtenValue);

    params.put(key, value);
  }
  
  // ===========================================================================
  // write
  // ===========================================================================

  @Override
  public String write(UrlQuery query) {
    requireNonNull(query);
    
    ImmutableMap<String, String> params = query.params();

    // normalize key order
    List<String> keyList = new ArrayList<>(params.keySet());
    Collections.sort(keyList);

    StringBuilder str = new StringBuilder();

    for (String key : keyList) {

      if (str.length() > 0)
        str.append(AND);

      String value = params.get(key);
      writeEntry(key, value, str);
    }

    return str.toString();
  }

  private void writeEntry(String key, String value, StringBuilder str) {
    String writtenKey = queryPartFormat.write(key);
    String writtenValue = queryPartFormat.write(value);

    str.append(writtenKey);
    str.append(EQUAL);
    str.append(writtenValue);
  }


}
