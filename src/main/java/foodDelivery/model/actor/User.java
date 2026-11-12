package foodDelivery.model.actor;

import java.util.Objects;

public class User{
    private String name;
    private String address;
    private int uuid;

    public User() {
    }

    public User(String name, String address, int uuid) {
        this.name = name;
        this.address = address;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    For hashmap lookup
    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, address);
    }

//    In case two separately identical instances but different memory addresses
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.uuid == user.uuid &&
                Objects.equals(name, user.name) &&
                Objects.equals(address, user.address);
    }

//    For Debugging/Logging
    @Override
    public String toString() {
        return String.format("User{ uuid={ %d }, name='%s', address='%s' }", uuid, name, address);
    }
}