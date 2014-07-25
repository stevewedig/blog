package com.stevewedig.blog.value_objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.stevewedig.blog.format.url_path.UrlPath;
import com.stevewedig.blog.format.url_path.UrlPathLib;
import com.stevewedig.blog.format.url_query.UrlQuery;
import com.stevewedig.blog.format.url_query.UrlQueryLib;
import com.stevewedig.blog.format.url_request.UrlRequest;
import com.stevewedig.blog.format.url_request.UrlRequestLib;

/**
 * Verify and demonstrate Value Objects defined via this library
 */
public class TestValueExampleComposite {

  private static boolean DO_PRINT = false;

  // Here is what the object tree looks like:
  //
  // Request
  // ---- path = Path
  // --------- parts = ImmutableList<String>
  // ---- query = Query
  // --------- map = ImmutableMap<String, String>
  @Test
  public void testValueExampleComposite() {

    // 3 paths
    UrlPath path1 = UrlPathLib.dir(ImmutableList.of("blog", "posts"));
    UrlPath path2 = UrlPathLib.dir(ImmutableList.of("blog", "posts"));
    UrlPath path3 = UrlPathLib.dir(ImmutableList.of("blog", "comments"));

    // path 1 and 2 are different instances, but have same value
    CompareLib.assertSameValueAndSameString(path1, path2);

    // path 1 and 3 have different value
    CompareLib.assertDifferentValueAndDifferentString(path1, path3);

    // 1 query
    UrlQuery query = UrlQueryLib.query(ImmutableMap.of("id", "1"));

    // 3 path queries
    UrlRequest request1 = UrlRequestLib.request(path1, query);
    UrlRequest request2 = UrlRequestLib.request(path2, query);
    UrlRequest request3 = UrlRequestLib.request(path3, query);

    // request 1 and 2 are different instances, but have same value
    CompareLib.assertSameValueAndSameString(request1, request2);

    // request 1 and 3 have different value
    CompareLib.assertDifferentValueAndDifferentString(request1, request3);

    // toString() of value objects
    if (DO_PRINT) {
      System.out.println("\nprinting value objects...");
      System.out.println(path1);
      System.out.println(query);
      System.out.println(request1);
    }
    assertEquals(path1.toString(), "UrlPathClass{isFile=false, parts=[blog, posts]}");
    assertEquals(query.toString(), "UrlQueryClass{params={id=1}}");
    assertEquals(
        request1.toString(),
        "UrlRequestClass{path=UrlPathClass{isFile=false, parts=[blog, posts]}, query=UrlQueryClass{params={id=1}}}");

  }

}
