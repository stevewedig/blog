package com.stevewedig.foundation.value_object;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.stevewedig.foundation.url.path.Path;
import com.stevewedig.foundation.url.path.PathLib;
import com.stevewedig.foundation.url.path_query.PathQuery;
import com.stevewedig.foundation.url.path_query.PathQueryLib;
import com.stevewedig.foundation.url.query.Query;
import com.stevewedig.foundation.url.query.QueryLib;
import com.stevewedig.foundation.value_object.ValueObject;
import com.stevewedig.foundation.value_object.ValueObjectHelper;

/**
 * Verify and demonstrate Value Objects defined via this library   
 */
public class TestValueObjectExample {

  private static boolean DO_PRINT = false;

  // Here is what the object tree looks like:
  //
  // PathQuery
  // ---- path = Path
  // --------- parts = ImmutableList<String>
  // ---- query = Query
  // --------- map = ImmutableMap<String, String>
  @Test
  public void testExample() {

    // 3 paths
    Path path1 = PathLib.path(ImmutableList.of("blog", "posts"));
    Path path2 = PathLib.path(ImmutableList.of("blog", "posts"));
    Path path3 = PathLib.path(ImmutableList.of("blog", "comments"));

    // path 1 and 2 are different instances, but have same value
    TestHelpers.assertSameValueAndSameString(path1,  path2);

    // path 1 and 3 have different value
    TestHelpers.assertDifferentValueAndDifferentString(path1,  path3);

    // 1 query
    Query query = QueryLib.query(ImmutableMap.of("id", "1"));

    // 3 path queries
    PathQuery pathQuery1 = PathQueryLib.pathQuery(path1, query);
    PathQuery pathQuery2 = PathQueryLib.pathQuery(path2, query);
    PathQuery pathQuery3 = PathQueryLib.pathQuery(path3, query);

    // pathQuery 1 and 2 are different instances, but have same value
    TestHelpers.assertSameValueAndSameString(pathQuery1,  pathQuery2);

    // pathQuery 1 and 3 have different value
    TestHelpers.assertDifferentValueAndDifferentString(pathQuery1,  pathQuery3);

    // toString() of value objects
    if (DO_PRINT) {
      System.out.println("\nprinting value objects...");
      System.out.println(path1);
      System.out.println(query);
      System.out.println(pathQuery1);
    }
    assertEquals(path1.toString(), "PathClass{parts=[blog, posts]}");
    assertEquals(query.toString(), "QueryClass{params={id=1}}");
    assertEquals(pathQuery1.toString(),
        "PathQueryClass{path=PathClass{parts=[blog, posts]}, query=QueryClass{params={id=1}}}");

    // =========================================================================
    // the stuff below has to do with the implementation, so feel free to ignore it
    // =========================================================================
    
    // toString() of object helpers
    if (DO_PRINT) {
      System.out.println("\nprinting value object helpers...");
      System.out.println(((ValueObject) path1).objectHelper());
      System.out.println(((ValueObject) query).objectHelper());
      System.out.println(((ValueObject) pathQuery1).objectHelper());
    }
    assertEquals(
        ((ValueObject) path1).objectHelper().toString(),
        "ObjectHelperClass{doCache=true, objectClass=class com.stevewedig.foundation.url.path.PathClass, fieldNamesAndValues=[parts, [blog, posts]]}");
    assertEquals(
        ((ValueObject) query).objectHelper().toString(),
        "ObjectHelperClass{doCache=true, objectClass=class com.stevewedig.foundation.url.query.QueryClass, fieldNamesAndValues=[params, {id=1}]}");
    assertEquals(
        ((ValueObject) pathQuery1).objectHelper().toString(),
        "ObjectHelperClass{doCache=true, objectClass=class com.stevewedig.foundation.url.path_query.PathQueryClass, fieldNamesAndValues=[path, PathClass{parts=[blog, posts]}, query, QueryClass{params={id=1}}]}");

    // toString() of object helper helpers
    if (DO_PRINT) {
      System.out.println("\nprinting value object helper helper (for science)...");
      ValueObjectHelper helper = ((ValueObject) pathQuery1).objectHelper();
      ValueObjectHelper helperHelper = ((ValueObject) helper).objectHelper();
      System.out.println(helperHelper);
    }
  }

}
