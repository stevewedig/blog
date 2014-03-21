package com.stevewedig.foundation.collect;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.stevewedig.foundation.fn.LambdaLib.Fn1;

public abstract class MapLib {

  // =========================================================================
  // http://stackoverflow.com/questions/13991710/why-guava-do-not-provide-a-way-to-transform-map-keys
  // =========================================================================

  public static <K1, K2, V> ImmutableMap<K2,V> transformKeys(Map<K1,V> map1, Fn1<K1, K2> transform) {

    ImmutableMap.Builder<K2,V> map2 = ImmutableMap.builder();
    
    for(Entry<K1,V> entry1 : map1.entrySet()) {
      K2 key2 = transform.apply(entry1.getKey());
      map2.put(key2, entry1.getValue());
    }
    
    return map2.build();
  }
  
  // =========================================================================
  // =========================================================================

  public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterable<V> values, Function<V, K> value__key) {
    return ImmutableMap.copyOf(Maps.uniqueIndex(values, value__key));
  }

  // =========================================================================
  // =========================================================================

  public static <Key, Value> ImmutableMap<Key, Value> unsafe(Object[] keysAndValues) {
    if (keysAndValues.length % 2 != 0)
      throw new RuntimeException("keysAndValues must be of even length: pairs of keys and values");
    
    ImmutableMap.Builder<Key,Value> map = ImmutableMap.builder();
    
    for(int i=0; i < keysAndValues.length;i +=2) {
      
      @SuppressWarnings("unchecked")
      Key key = (Key) keysAndValues[i];
      
      @SuppressWarnings("unchecked")
      Value value = (Value) keysAndValues[i+1];
      
      map.put(key, value);
    }
    
    return map.build();
  }

  // =========================================================================
  // =========================================================================

  // TODO shouldn't this already exist eslewhere?
  public static <K,V> V safeGet(Map<K,V> key__val, K key) {

    V val = key__val.get(key);
    requireNonNull(val);

    return val;
  }
  
  public static <K,V> void safePutAll(Map<K,V> map1, Map<K,V> map2, boolean allowEqualDuplicates) {
    
    for(Entry<K,V> entry : map2.entrySet()) {
      K key = entry.getKey();
      V value2 = entry.getValue();
      
      if(map1.containsKey(key)) {
        if(! allowEqualDuplicates) throw new RuntimeException("duplicate key: " + key);
        
        V value1 = map1.get(key);
        if(! value1.equals(value2))
          throw new RuntimeException("duplicate key and not equal: " + key + ", " + value1 + ", " + value2);
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
