package com.stevewedig.blog.value_objects;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Demonstrating that Guava's Optional and immutable collections are ValueObject   
 */
public class TestGuavaValues {

  @Test
  public void testGuavaOptional() {

    Optional<String> bob1 = Optional.of("bob");
    Optional<String> bob2 = Optional.of("bob");
    Optional<String> alice = Optional.of("alice");
    
    Optional<String> absent1 = Optional.absent();
    Optional<String> absent2 = Optional.absent();

    CompareLib.assertSameValueAndSameString(bob1, bob2);
    CompareLib.assertDifferentValueAndDifferentString(bob1, alice);
    CompareLib.assertDifferentValueAndDifferentString(bob1, absent1);
    
    // Guava does flywheel for absent, so these are the same instance
    assertThat(absent1, sameInstance(absent2));
  }

  @Test
  public void testGuavaImmutableList() {
    
    ImmutableList<String> bob1 = ImmutableList.of("bob");
    ImmutableList<String> bob2 = ImmutableList.of("bob");
    ImmutableList<String> alice = ImmutableList.of("alice");
    
    CompareLib.assertSameValueAndSameString(bob1, bob2);
    CompareLib.assertDifferentValueAndDifferentString(bob1, alice);
  }

  @Test
  public void testGuavaImmutableSet() {
    
    ImmutableSet<String> bob1 = ImmutableSet.of("bob");
    ImmutableSet<String> bob2 = ImmutableSet.of("bob");
    ImmutableSet<String> alice = ImmutableSet.of("alice");
    
    CompareLib.assertSameValueAndSameString(bob1, bob2);
    CompareLib.assertDifferentValueAndDifferentString(bob1, alice);
  }

  @Test
  public void testGuavaImmutableMap() {
    
    ImmutableMap<String, Integer> bob1 = ImmutableMap.of("bob", 1);
    ImmutableMap<String, Integer> bob2 = ImmutableMap.of("bob", 1);
    ImmutableMap<String, Integer> differentKey = ImmutableMap.of("alice", 1);
    ImmutableMap<String, Integer> differentValue = ImmutableMap.of("bob", 2);
    
    CompareLib.assertSameValueAndSameString(bob1, bob2);
    CompareLib.assertDifferentValueAndDifferentString(bob1, differentKey);
    CompareLib.assertDifferentValueAndDifferentString(bob1, differentValue);
  }

  // presumably Guava's other immutable collections work the same (ImmutableSetMultimap, etc.)
}

