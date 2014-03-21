package com.stevewedig.foundation.url.path_query;

import com.stevewedig.foundation.url.path.Path;
import com.stevewedig.foundation.url.query.Query;

public interface PathQuery {
  Path path();
  Query query();
}
