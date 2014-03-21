package com.stevewedig.foundation.url.path_query;

import com.stevewedig.foundation.url.path.Path;
import com.stevewedig.foundation.url.query.Query;
import com.stevewedig.foundation.value_object.ValueMixin;
import com.stevewedig.foundation.value_object.ValueObjectHelper;

class PathQueryClass extends ValueMixin implements PathQuery {
  private final Path path;
  private final Query query;

  public PathQueryClass(Path path, Query query) {
    super();
    this.path = path;
    this.query = query;
  }

  @Override
  public Path path() {
    return path;
  }

  @Override
  public Query query() {
    return query;
  }

  // ===========================================================================
  // ValueMixin
  // ===========================================================================

  @Override
  public ValueObjectHelper objectHelper() {
    return objectHelper("path", path, "query", query);
  }

}
