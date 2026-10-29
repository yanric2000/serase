package com.startup.domain.event;

public final class GeoDistance {

  private static final double EARTH_RADIUS_KM = 6371.0;

  private GeoDistance() {}

  public static double kilometers(double latitude1, double longitude1, double latitude2, double longitude2) {
    double latitudeDelta = Math.toRadians(latitude2 - latitude1);
    double longitudeDelta = Math.toRadians(longitude2 - longitude1);
    double a =
        Math.sin(latitudeDelta / 2) * Math.sin(latitudeDelta / 2)
            + Math.cos(Math.toRadians(latitude1))
                * Math.cos(Math.toRadians(latitude2))
                * Math.sin(longitudeDelta / 2)
                * Math.sin(longitudeDelta / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return EARTH_RADIUS_KM * c;
  }
}
