package foodDelivery.service;

import java.util.ArrayList;
import java.util.List;

import foodDelivery.model.actor.Restaurant;
import foodDelivery.model.actor.User;
import foodDelivery.model.order.MenuItem;
import foodDelivery.model.order.Order;
import foodDelivery.model.order.OrderEntry;
import foodDelivery.model.order.OrderStatus;

public class Client {

    private User user;
    private List<Order> activeOrders;
    private List<MenuItem> cart;

    public Client(User user) {
        this.user = user;
        this.activeOrders = new ArrayList<>();
        this.cart = new ArrayList<>();
    }

    public boolean userAuth() {
        return user != null && user.getName() != null && !user.getName().isBlank();
    }

    public void userLogout() {
        this.user = null;
        this.cart.clear();
    }

    public List<MenuItem> retrieveCatalog(Restaurant restaurant) {
        if (restaurant == null) {
            return new ArrayList<>();
        }
        return restaurant.getMenu();
    }

    public List<Order> monitorActiveOrders() {
        return activeOrders;
    }

    public void retrieveUserOrderFeedback(OrderStatus os) {
        if (os != null) {
            os.pollOrderStatus();
        }
    }

    public Order placeOrder(MenuItem item, User user) {
        if (item == null || user == null) {
            return null;
        }

        OrderEntry entry = new OrderEntry(item, 1);
        Order order = new Order();
        order.getEntries().add(entry);
        order.setAddress(user.getAddress());
        order.setPaymentMode("Cash");
        activeOrders.add(order);
        cart.clear();
        return order;
    }
}
