package com.stevewedig.foundation.url.router;

import org.junit.Ignore;
import org.junit.Test;

import com.stevewedig.foundation.url.router.PathRouter;
import com.stevewedig.foundation.url.router.PathRouterLib;

public class TestPathRouter {

  static enum Location {
    home, postList, postDetail, commentCreate, commentDetail, about, resume
  }


  @Ignore
  @Test
  public void test() {

    // // notice, no page at .../comments/
    PathRouter<Location> postRouter =
        PathRouterLib.<Location>builder().put("", Location.postDetail)
            .put("reply", Location.commentCreate)
            .put("comments/{commentId}", Location.commentDetail).build();

    PathRouter<Location> rootRouter =
        PathRouterLib.<Location>builder().put("", Location.home)
            .put("all/about/me", Location.about)
            .put("all/about/me/resume-{date}.pdf", Location.resume).put("posts", Location.postList)
            .put("posts/{postId}", postRouter).build();
  }
}
