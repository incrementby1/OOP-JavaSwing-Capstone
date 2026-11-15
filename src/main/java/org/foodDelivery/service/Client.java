package org.foodDelivery.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.foodDelivery.model.actor.Restaurant;
import org.foodDelivery.model.actor.User;
import org.foodDelivery.model.order.MenuItem;
import org.foodDelivery.model.order.Order;
import org.foodDelivery.model.order.OrderEntry;
import org.foodDelivery.model.order.OrderStatus;

public class Client {

    private static final Path USERS_FILE = Path.of("../data/users.json");
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private User user;
    private List<Order> activeOrders;
    private List<MenuItem> cart;

    public Client(User user) {
        this.user = user;
        this.activeOrders = new ArrayList<>();
        this.cart = new ArrayList<>();
    }

    // ---------- JSON Helpers ----------

    public List<User> loadUsers() throws IOException {
        if (Files.notExists(USERS_FILE) || Files.size(USERS_FILE) == 0) {
            return new ArrayList<>();
        }
        return MAPPER.readValue(USERS_FILE.toFile(), new TypeReference<List<User>>() {});
    }

    public void saveUsers(List<User> users) throws IOException {
        if (USERS_FILE.getParent() != null) {
            Files.createDirectories(USERS_FILE.getParent());
        }
        MAPPER.writeValue(USERS_FILE.toFile(), users);
    }

    // Generic helpers if you also want to save/load Orders, Menus, etc.
    public <T> void writeJson(Path path, T data) throws IOException {
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }
        MAPPER.writeValue(path.toFile(), data);
    }

    public <T> T readJson(Path path, TypeReference<T> typeRef) throws IOException {
        return MAPPER.readValue(path.toFile(), typeRef);
    }

    // ---------- Registration / Auth Example ----------

    public boolean userRegistration() {
        if (user == null || user.getName() == null || user.getName().isBlank()) {
            return false;
        }
        try {
            List<User> users = loadUsers();
            boolean exists = users.stream()
                    .anyMatch(u -> u.getName() != null && u.getName().equalsIgnoreCase(user.getName()));
            if (exists) {
                return false;
            }
            users.add(user);
            saveUsers(users);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean userAuth() {
        if (user == null || user.getName() == null || user.getName().isBlank()) {
            return false;
        }
        try {
            return loadUsers().stream()
                    .anyMatch(u -> u.getName() != null && u.getName().equalsIgnoreCase(user.getName()));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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
//        order.setAddress(user.getAddress());
        order.setPaymentMode("Cash");
        activeOrders.add(order);
        cart.clear();
        return order;
    }

    public static void main() {

    }
}
