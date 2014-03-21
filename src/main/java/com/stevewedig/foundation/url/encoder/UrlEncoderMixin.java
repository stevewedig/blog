package com.stevewedig.foundation.url.encoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.stevewedig.foundation.etc.StrLib;
import com.stevewedig.foundation.url.path.Path;
import com.stevewedig.foundation.url.path.PathLib;
import com.stevewedig.foundation.url.path_query.PathQuery;
import com.stevewedig.foundation.url.path_query.PathQueryLib;
import com.stevewedig.foundation.url.query.Query;
import com.stevewedig.foundation.url.query.QueryLib;

public abstract class UrlEncoderMixin implements UrlEncoder {

  // ===========================================================================
  // path
  // ===========================================================================

  @Override
  public String writePath(Path path) {

    StringBuffer body = new StringBuffer();

    for (String part : path.parts()) {
      body.append(writePathPart(part) + "/");
    }

    return body.length() > 0 ? StrLib.prefix(body.toString()) : body.toString();
  }

  @Override
  public Path parsePath(String str) {

    List<String> writtenParts = Lists.newArrayList(StrLib.stripSplit(str, '/'));

    List<String> parts = new ArrayList<String>();
    for (String writtenPart : writtenParts) {
      parts.add(parsePathPart(writtenPart));
    }

    return PathLib.path(parts);
  }

  // ===========================================================================
  // query
  // ===========================================================================

  @Override
  public String writeQuery(String... query) {
    return writeQuery(QueryLib.query(query));
  }

  @Override
  public String writeQuery(Query query) {

    // normalize key order
    List<String> key_list = new ArrayList<String>(query.params().keySet());
    Collections.sort(key_list);

    StringBuffer body = new StringBuffer();

    int count = 0;
    for (String key : key_list) {
      String val = query.params().get(key);

      body.append(writeQueryPart(key));
      body.append("=");
      body.append(writeQueryPart(val));

      if (count++ < query.params().size() - 1)
        body.append("&");
    }

    return body.toString();
  }

  @Override
  public Query parseQuery(String str) {

    if (str.length() == 0)
      return QueryLib.query();

    List<String> arc_strs = Lists.newArrayList(StrLib.split(str, '&'));

    ImmutableMap.Builder<String, String> key__val = ImmutableMap.builder();

    for (String arc_str : arc_strs) {

      int offset = arc_str.indexOf('='); // TODO @untested that it allows x="abc=def"

      String key;
      String val;
      if (offset < 0) {
        key = arc_str;
        val = "";
      } else {
        key = arc_str.substring(0, offset);
        val = parseQueryPart(arc_str.substring(offset + 1));
      }

      key__val.put(key, val);
    }

    return QueryLib.query(key__val.build());

  }

  // ===========================================================================
  // path query
  // ===========================================================================

  @Override
  public String writePathQuery(PathQuery pathQuery) {
    String str = writePath(pathQuery.path());

    if (str.length() == 0 || !str.substring(str.length() - 1).equals("/"))
      str += "/";

    if (pathQuery.query().params().size() > 0) {
      str += "?" + writeQuery(pathQuery.query());
    }

    return str;
  }

  @Override
  public PathQuery parsePathQuery(String str) {
    int i = str.indexOf('?');

    String pathStr, queryStr;
    if (i < 0) {
      pathStr = str;
      queryStr = "";
    } else {
      pathStr = str.substring(0, i);
      queryStr = str.substring(i + 1);
    }

    return PathQueryLib.pathQuery(parsePath(pathStr), parseQuery(queryStr));
  }

}
