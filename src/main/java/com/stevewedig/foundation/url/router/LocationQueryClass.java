package com.stevewedig.foundation.url.router;

import com.stevewedig.foundation.url.query.Query;
import com.stevewedig.foundation.value_object.ValueMixin;
import com.stevewedig.foundation.value_object.ValueObjectHelper;

class LocationQueryClass<Location> extends ValueMixin implements LocationQuery<Location> {
  private final Location location;
  private final Query query;

  public LocationQueryClass(Location location, Query query) {
    super();
    this.location = location;
    this.query = query;
  }

  @Override
  public Location location() {
    return location;
  }

  @Override
  public Query query() {
    return query;
  }

  @Override
  public ValueObjectHelper objectHelper() {
    return objectHelper("location", location, "query", query);
  }

}
