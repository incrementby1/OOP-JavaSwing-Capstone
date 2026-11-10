package org.foodDelivery.model.actor;

import java.util.ArrayList;
import java.util.List;

import org.foodDelivery.model.order.MenuItem;

public class Restaurant {
    private String name;
    private String location;
    private String operatingHours;
    private List<MenuItem> menu;

    public Restaurant() {
        this.menu = new ArrayList<>();
    }

    public Restaurant(String name, String location, String operatingHours) {
        this.name = name;
        this.location = location;
        this.operatingHours = operatingHours;
        this.menu = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
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
