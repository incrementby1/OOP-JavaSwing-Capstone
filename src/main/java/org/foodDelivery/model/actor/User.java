package org.foodDelivery.model.actor;
import org.foodDelivery.model.BaseEntity;
import org.foodDelivery.model.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User extends BaseEntity {
    private String name;
    List<Address> savedAddresses;
    private Address activeAddress;

    public User(UUID id, String name) {
        super(id);
        this.name = name;
        savedAddresses = new ArrayList<>();
        activeAddress = null;
    }

    public User(String name, UUID id, Address activeAddress) {
        this(id,name);
        this.activeAddress = activeAddress;
    }

    public final String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Address> getAddresses() {
        return savedAddresses;
    }
    public void setAddress(Address address) {
        this.activeAddress = address;
        savedAddresses.add(address);
    }

//    For Debugging/Logging
    @Override
    public String toString() {
        return String.format("User{ uuid={ %s }, name='%s', address='%s' }", this.getId().toString(), name, activeAddress);
    }
}