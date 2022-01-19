package com.tomtomscenery.scenery_backend.services.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataProcessorServiceImplTest {
     int counter = 0;
     int limit = 10;
     int[] testArray;

    @Test
     void checkIfLengthResultArrayIsEqual() {
         testArray = new int[10];
         assertEquals(testArray.length, limit);
     }

     @Test
     void setLimitToResultArrayLengthIfNotEqual() {
         testArray = new int[4];

         if(testArray.length < limit) {
              limit = testArray.length;
         }

         assertEquals(testArray.length, limit);
     }

     @Test
     void checkIfResultArrayIsEmpty() {

          testArray = new int[0];

          boolean isEmpty = (testArray.length == 0) ? true : false;

          assertTrue(isEmpty);
     }
}