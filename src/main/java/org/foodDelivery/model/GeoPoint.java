package org.foodDelivery.model;

public class GeoPoint {
    private final double latitude;
    private final double longitutde;

    public GeoPoint(double latitude, double longitutde) {
        this.latitude = latitude;
        this.longitutde = longitutde;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitutde() {
        return longitutde;
    }

    public double distanceTo(GeoPoint other) {
        final double EARTH_RADIUS_KM = 6371.0;

        double latDistance = Math.toRadians(other.getLatitude() - this.latitude);
        double lonDistance = Math.toRadians(other.getLongitutde() - this.longitutde);
        double a = Math.pow(Math.sin(latDistance / 2), 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.getLatitude()))
                * Math.pow(Math.sin(lonDistance / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}
