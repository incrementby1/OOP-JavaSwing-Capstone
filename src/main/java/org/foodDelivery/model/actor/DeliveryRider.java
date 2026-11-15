package org.foodDelivery.model.actor;

public class DeliveryRider {
    public String name;
    // Replace later with x-y location
    private String vehicleType;
    private String address;
    private boolean isAvailable;
    private String activeOrder;

    public DeliveryRider(String vehicleType) {
        this.vehicleType = vehicleType;
        this.isAvailable = false;
        this.activeOrder = null;
    }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public String getActiveOrder() { return activeOrder; }
    public void setActiveOrder(String activeOrder) { this.activeOrder = activeOrder; }
}
