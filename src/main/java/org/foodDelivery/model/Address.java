package org.foodDelivery.model;

public class Address {
    private final String street;
    private final String postalCode;
    private final GeoPoint coordinates;

    public Address(String street, String postalCode, GeoPoint coordinates) {
        this.street = street;
        this.postalCode = postalCode;
        this.coordinates = coordinates;
    }
    public String getStreet() { return street; }
    public String getPostalCode() { return postalCode; }
    public GeoPoint getCoordinates() { return coordinates; }
    public double distanceTo(Address other) { return this.coordinates.distanceTo(other.getCoordinates()); }
}
