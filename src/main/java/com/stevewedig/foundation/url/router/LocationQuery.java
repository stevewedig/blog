package com.stevewedig.foundation.url.router;

import com.stevewedig.foundation.url.query.Query;

public interface LocationQuery<Location> {

  Location location();

  Query query();
}
