package com.stevewedig.blog.util;

import java.util.Collection;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.stevewedig.blog.errors.NotThrown;

public class TestCollectLib {

  // ===========================================================================
  // is empty?
  // ===========================================================================

  @Test
  public void testAssertEmptyOrNot() {

    String[] emptyArray = {};
    String[] fullArray = {"a"};

    Collection<String> emptyCollection = ImmutableSet.of();
    Collection<String> fullCollection = ImmutableSet.of("a");

    // =================================
    // not empty (array)
    // =================================
    
    CollectLib.assertNotEmpty(fullArray);
    
    try {
      CollectLib.assertNotEmpty(emptyArray);
      throw new NotThrown(AssertionError.class);
    }
    catch(AssertionError e) {
    }

    // =================================
    // not empty (collection)
    // =================================
    
    CollectLib.assertNotEmpty(fullCollection);
    
    try {
      CollectLib.assertNotEmpty(emptyCollection);
      throw new NotThrown(AssertionError.class);
    }
    catch(AssertionError e) {
    }

    // =================================
    // is empty (array)
    // =================================

    CollectLib.assertIsEmpty(emptyArray);
    
    try {
      CollectLib.assertIsEmpty(fullArray);
      throw new NotThrown(AssertionError.class);
    }
    catch(AssertionError e) {
    }

    // =================================
    // is empty (collection)
    // =================================

    CollectLib.assertIsEmpty(emptyCollection);
    
    try {
      CollectLib.assertIsEmpty(fullCollection);
      throw new NotThrown(AssertionError.class);
    }
    catch(AssertionError e) {
    }

  }

  // ===========================================================================
  // is even?
  // ===========================================================================
  
   @Test
   public void testEvenSizeOrNot() {
     
     String[] oddArray = {"a"};
     String[] evenArray = {};

     Collection<String> oddCollection = ImmutableSet.of("a");
     Collection<String> evenCollection = ImmutableSet.of();

     // =================================
     // is even (array)
     // =================================

     CollectLib.assertSizeIsEven(evenArray);
     
     try {
       CollectLib.assertSizeIsEven(oddArray);
       throw new NotThrown(AssertionError.class);
     }
     catch(AssertionError e) {
     }

     // =================================
     // is even (collection)
     // =================================

     CollectLib.assertSizeIsEven(evenCollection);
     
     try {
       CollectLib.assertSizeIsEven(oddCollection);
       throw new NotThrown(AssertionError.class);
     }
     catch(AssertionError e) {
     }
     
     // =================================
     // not even (array)
     // =================================
     
     CollectLib.assertSizeIsOdd(oddArray);
     
     try {
       CollectLib.assertSizeIsOdd(evenArray);
       throw new NotThrown(AssertionError.class);
     }
     catch(AssertionError e) {
     }

     // =================================
     // not even (collection)
     // =================================
     
     CollectLib.assertSizeIsOdd(oddCollection);
     
     try {
       CollectLib.assertSizeIsOdd(evenCollection);
       throw new NotThrown(AssertionError.class);
     }
     catch(AssertionError e) {
     }     
     
   }


}
