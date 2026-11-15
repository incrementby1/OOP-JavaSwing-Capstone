package org.foodDelivery.model.order;
import org.foodDelivery.model.actor.User;
import org.foodDelivery.model.order.OrderStatus;
import org.foodDelivery.model.actor.DeliveryRider;
import org.foodDelivery.model.actor.Restaurant;

import  java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class System {
    List<User> users;
    List<Restaurant> restaurant;

    System()
    {
        users = new ArrayList<>();
        restaurant = new ArrayList<>();
    }

    public Order placeOrder(MenuItem entry, User user)
    {
        UUID user_id = user.getId();
        return new Order();
    }

    public List<MenuItem> retrieveCatalog(Restaurant rstrnt)
    {
        return rstrnt.getMenu();
    }

    /**
     * Testing return statements, tidy up after
     */
    public boolean placePayment(Restaurant rstrnt, User user)
    {
        return true;
    }

    public OrderStatus assignOrder(Order order)
    {
         return new OrderStatus();
    }

    public DeliveryRider assignRider(Order order)
    {
        // Testing
        return new DeliveryRider("Nisan");
    }
}