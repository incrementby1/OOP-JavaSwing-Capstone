package org.foodDelivery.model.actor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.foodDelivery.model.BaseEntity;
import org.foodDelivery.model.Address;
import org.foodDelivery.model.order.MenuItem;

public class Restaurant extends BaseEntity {
    private String name;
    public UUID id;
    private String phone;
    private List<Address> branches;
    private String operatingHours;
    private List<MenuItem> menu;

    public Restaurant(UUID id, String name, String phone, List<Address> branches) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.branches = branches;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Specific Location of a Branch provided the city
     * TODO: filter List for records that match the city (from JSON)
     */
    public String getLocation(String city) {
        return " ";
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getOperatingHours() {
        return operatingHours;
    }
    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }
    public List<MenuItem> getMenu() {
        return menu;
    }

    /**
     * accept user address @param1
     * determine nearest branch by filtering using distanceTo()
     */
    public Address getNearestBranch(Address userAddress) {
        return branches.stream().min(Comparator.comparingDouble(branch -> branch.distanceTo(userAddress)))
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu == null ? new ArrayList<>() : new ArrayList<>(menu);
    }

    public void addMenuItem(MenuItem menuItem) {
        if (menuItem != null) {
            menu.add(menuItem);
        }
    }

    public void removeMenuItem(MenuItem menuItem) {
        menu.remove(menuItem);
    }

    @Override
    public String toString() {
        return String.format("Restaurant{name='%s', location='%s', operatingHours='%s', menuSize=%d}",
                name, location, operatingHours, menu.size());
    }
}
