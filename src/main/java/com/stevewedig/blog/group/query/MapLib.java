package com.stevewedig.blog.group.query;

import static com.stevewedig.blog.util.StrLib.format;
import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.stevewedig.blog.util.LambdaLib.Fn0;
import com.stevewedig.blog.util.LambdaLib.Fn1;

public abstract class MapLib {

  // TODO seems slow because it iterates through all entries in the map?
  public static <K, V> ImmutableMap<K, V> keepKeys(final Map<K, V> map, final Set<K> keys) {
    return ImmutableMap.copyOf(Maps.filterKeys(map, new Predicate<K>() {
      @Override
      public boolean apply(K key) {
        return keys.contains(key);
      }
    }));
  }

  // =========================================================================
  // =========================================================================

  public static <Item> ImmutableSet<Item> allKeysAndValues(Map<Item, Item> map) {
    ImmutableSet.Builder<Item> items = ImmutableSet.builder();
    items.addAll(map.keySet());
    items.addAll(map.values());
    return items.build();
  }

  public static <Item> ImmutableSet<Item> allKeysAndValues(Multimap<Item, Item> map) {
    ImmutableSet.Builder<Item> items = ImmutableSet.builder();
    items.addAll(map.keySet());
    items.addAll(map.values());
    return items.build();
  }

  public static <Item> ImmutableSet<Item> allKeysAndValues(Multimap<Item, Item> map,
      Iterable<Item> extraKeys) {
    ImmutableSet.Builder<Item> items = ImmutableSet.builder();
    items.addAll(map.keySet());
    items.addAll(map.values());
    items.addAll(extraKeys);
    return items.build();
  }

  public static <Item> ImmutableSet<Item> oneKeyAndValues(Multimap<Item, Item> map, Item key) {
    ImmutableSet.Builder<Item> items = ImmutableSet.builder();
    items.add(key);
    items.addAll(map.get(key));
    return items.build();
  }

  // =========================================================================
  // =========================================================================

  // Iterable<Resource> resources = Iterables.transform(uris, MapLib.getter(uri__resource));

  public static <K, V> Fn0<Set<K>> keySetGetter(final Map<K, V> map) {
    return new Fn0<Set<K>>() {
      @Override
      public Set<K> apply() {
        return map.keySet();
      }
    };
  }

  public static <K, V> Function<K, V> valueGetter(final Map<K, V> map) {
    return new Function<K, V>() {
      @Override
      public V apply(K key) {
        return requireNonNull(map.get(key));
      }
    };
  }

  public static <K, V> Iterable<V> pipeIterator(Map<K, V> map, Iterable<K> keys) {
    return Iterables.transform(keys, valueGetter(map));
  }

  public static <K, V> ImmutableSet<V> pipe(Map<K, V> map, Iterable<K> keys) {
    return ImmutableSet.copyOf(pipeIterator(map, keys));
  }

  // =========================================================================
  // http://stackoverflow.com/questions/13991710/why-guava-do-not-provide-a-way-to-transform-map-keys
  // =========================================================================

  public static <K1, K2, V> ImmutableMap<K2, V> transformKeys(Map<K1, V> map1, Fn1<K1, K2> transform) {

    ImmutableMap.Builder<K2, V> map2 = ImmutableMap.builder();

    for (Entry<K1, V> entry1 : map1.entrySet()) {
      K2 key2 = transform.apply(entry1.getKey());
      map2.put(key2, entry1.getValue());
    }

    return map2.build();
  }

  public static <K1, K2, V> ImmutableMap<K2, V> transformKeys(Map<K1, V> k1__v,
      final Map<K1, K2> k1__k2) {
    return transformKeys(k1__v, new Fn1<K1, K2>() {
      @Override
      public K2 apply(K1 k1) {
        return requireNonNull(k1__k2.get(k1));
      }
    });
  }

  // =========================================================================
  // =========================================================================

  public static <K, V> ImmutableMap<K, V> fromKeys(Iterable<K> keys, final Map<K, V> key__value) {
    return fromKeys(keys, new Function<K, V>() {
      @Override
      public V apply(K key) {
        return get(key__value, key);
      }
    });
  }

  public static <K, V> ImmutableMap<K, V> fromKeys(Iterable<K> keys, Function<K, V> key__value) {
    ImmutableMap.Builder<K, V> map = ImmutableMap.builder();
    for (K key : keys) {
      V value = key__value.apply(key);
      map.put(key, value);
    }
    return map.build();
  }

  public static <K, V> ImmutableMap<K, V> fromValues(Iterable<V> values, Function<V, K> value__key) {
    return ImmutableMap.copyOf(Maps.uniqueIndex(values, value__key));
  }

  // =========================================================================
  // =========================================================================

  public static <Key, Value> ImmutableMap<Key, Value> unsafe(Object[] keysAndValues) {
    if (keysAndValues.length % 2 != 0)
      throw new RuntimeException("keysAndValues must be of even length: pairs of keys and values");

    ImmutableMap.Builder<Key, Value> map = ImmutableMap.builder();

    for (int i = 0; i < keysAndValues.length; i += 2) {

      @SuppressWarnings("unchecked")
      Key key = (Key) keysAndValues[i];

      @SuppressWarnings("unchecked")
      Value value = (Value) keysAndValues[i + 1];

      map.put(key, value);
    }

    return map.build();
  }

  // =========================================================================
  // =========================================================================

  // TODO shouldn't this already exist eslewhere?
  public static <K, V> V get(Map<K, V> key__val, K key, V defaultValue) {

    if (!key__val.containsKey(key))
      return defaultValue;

    return key__val.get(key);
  }

  public static <K, V> V get(Map<K, V> key__val, K key) {

    if (!key__val.containsKey(key))
      throw new RuntimeException(format("key %s not contained in map with keys %s", key,
          key__val.keySet()));

    return key__val.get(key);
  }

  public static <K, V> void safePutAll(Map<K, V> map1, Map<K, V> map2, boolean allowEqualDuplicates) {

    for (Entry<K, V> entry : map2.entrySet()) {
      K key = entry.getKey();
      V value2 = entry.getValue();

      if (map1.containsKey(key)) {
        if (!allowEqualDuplicates)
          throw new RuntimeException("duplicate key: " + key);

        V value1 = map1.get(key);
        if (!value1.equals(value2))
          throw new RuntimeException("duplicate key and not equal: " + key + ", " + value1 + ", "
              + value2);
      }

      map1.put(key, value2);
    }
  }

  // =========================================================================
  // =========================================================================

  // TODO cleanup
  public static <X> Optional<X> onlyVal(String id, Map<String, X> id__val) {
    if (id__val.size() == 0)
      return Optional.absent();
    else if (id__val.size() >= 2)
      throw new RuntimeException();
    else if (!id__val.containsKey(id))
      throw new RuntimeException();
    else
      return Optional.of(id__val.get(id));
  }

  // TODO cleanup
  public static <X> Set<X> onlyVals(String id, SetMultimap<String, X> id__vals) {
    if (id__vals.keys().size() == 0)
      return Sets.newHashSet();
    if (id__vals.keySet().size() >= 2)
      throw new RuntimeException();
    else if (!id__vals.containsKey(id))
      throw new RuntimeException();
    else
      return Sets.newHashSet(id__vals.get(id));
  }

}
