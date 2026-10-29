package com.startup.domain.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GeoDistanceTest {

  @Test
  void samePointIsZero() {
    assertEquals(0, GeoDistance.kilometers(-23.55, -46.63, -23.55, -46.63), 0.001);
  }

  @Test
  void oneDegreeOfLatitudeIsAbout111Kilometers() {
    double distance = GeoDistance.kilometers(-23.55, -46.63, -24.55, -46.63);
    assertTrue(distance > 110 && distance < 112);
  }
}
