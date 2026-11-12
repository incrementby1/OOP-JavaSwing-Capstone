package morsel.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import morsel.model.actors.Actor;
import morsel.model.actors.Rider;
import morsel.model.data.Order;
import morsel.model.data.UserEntry;
import morsel.model.data.MenuItem;
import morsel.model.data.OrderEntry;
import morsel.model.exceptions.*;
import morsel.service.StorageService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

/**
 * <p>Mock server. Handles
 * IPC with other server instances.
 * Exposes the API required to fulfill
 * set tasks.
 *
 * <p>WARNING: This class is not thread-safe
 * not within this JVM, and not with other processes either.
 * Relies on the fact human input gives it breathing room without
 * hitting a race condition. If data gets corrupted look into this first.
 */
public class Server {
    /**
     * Shared state across
     * several server instances for
     * disk-backed multi-client communication.
     */
    private static class SharedState {
        public long uuid = 0;
        public long oid = 0;
    }
    private MessageDigest md;
    private StorageService<SharedState> stateStore;
    private StorageService<HashMap<String, UserEntry>> userStore;
    private StorageService<HashMap<Long, OrderEntry>> orderStore;
    private StorageService<HashMap<Long, Rider>> activeRiderStore;
    private StorageService<List<MenuItem>> catalogStore;

    /**
     * Sets up the storage services
     * for each of the stores needed.
     * @throws NoSuchAlgorithmException Thrown when environment does not support SHA-256
     * @throws IOException When storage service fails to create a store.
     */
    public void run() throws NoSuchAlgorithmException, IOException {
        md = MessageDigest.getInstance("SHA-256");
        stateStore = new StorageService<>("runtimeData/sharedState.json", new TypeReference<>() {}, SharedState::new);
        userStore = new StorageService<>("runtimeData/userStore.json", new TypeReference<>() {}, HashMap::new);
        orderStore = new StorageService<>("runtimeData/orderStore.json", new TypeReference<>() {}, HashMap::new);
        activeRiderStore = new StorageService<>("runtimeData/activeRiderStore.json", new TypeReference<>() {}, HashMap::new);
        catalogStore = new StorageService<>("runtimeData/catalogStore.json", new TypeReference<>() {}, ArrayList::new);
    }

    /**
     * Registers a given user's credentials
     * and their role in the server.
     *
     * @throws IOException Throws if registration fails due to IO error.
     */
    public void registerCredentials(String user, String pass, String vpass, Actor.Role role)
            throws UnlikeValidationPasswordException, IOException, UserAlreadyExistsException {
        assert user != null && pass != null && vpass != null && role != null;
        if (!pass.equals(vpass)) {
            throw new UnlikeValidationPasswordException("");
        }
        SharedState state = stateStore.readFrom();
        HashMap<String, UserEntry> store = userStore.readFrom();
        if (store.containsKey(user)) {
            throw new UserAlreadyExistsException("");
        }
        long uuid = state.uuid++;

        byte[] rawBytes = md.digest(pass.getBytes(StandardCharsets.UTF_8));
        String hashedPassword = Base64.getEncoder().encodeToString(rawBytes);
        store.put(user, new UserEntry(user, hashedPassword, role, uuid));
        userStore.writeTo(store);
        stateStore.writeTo(state);
    }

    /**
     * Validates the specified credentials against the stored
     * credentials in the server.
     * @param user Specified username.
     * @param pass Specified password.
     * @return The auth entry for this specific credential.
     * @throws InvalidCredentialsException When credentials don't match or user does not exist.
     */
    public UserEntry validateCredentials(String user, String pass)
            throws InvalidCredentialsException, IOException, IllegalStateException {
        assert user != null && pass != null;

        HashMap<String, UserEntry> store = userStore.readFrom();
        if (!store.containsKey(user)) {
            throw new InvalidCredentialsException("");
        }
        UserEntry ae = store.get(user);
        if (ae == null) {
            throw new IllegalStateException("");
        }
        byte[] rawBytes = md.digest(pass.getBytes(StandardCharsets.UTF_8));
        String hashedPassword = Base64.getEncoder().encodeToString(rawBytes);
        if (!ae.hashedPassword.equals(hashedPassword)) {
            throw new InvalidCredentialsException("");
        }
        return ae;
    }

    /**
     * Deletes the specified credentials from the user store.
     * @param user The specified user to delete.
     * @param pass The associated user's password.
     * @throws InvalidCredentialsException When credentials don't match.
     * @throws IOException When an error occurs during deletion due to an IO error.
     * @throws IllegalStateException When the UserEntry is unexpectedly null.
     */
    public void deleteCredentials(String user, String pass)
            throws InvalidCredentialsException, IOException, IllegalStateException {
        assert user != null && pass != null;

        HashMap<String, UserEntry> store = userStore.readFrom();
        if (!store.containsKey(user)) {
            throw new InvalidCredentialsException("");
        }
        UserEntry ae = store.get(user);
        if (ae == null) {
            throw new IllegalStateException("");
        }
        byte[] rawBytes = md.digest(pass.getBytes(StandardCharsets.UTF_8));
        String hashedPassword = Base64.getEncoder().encodeToString(rawBytes);
        if (!ae.hashedPassword.equals(hashedPassword)) {
            throw new InvalidCredentialsException("");
        }
        store.remove(user);
        userStore.writeTo(store);
    }

    /**
     * Creates a given order and pushes the entry onto disk.
     * @param order The specified order.
     * @return The specified OrderEntry.
     * @throws IOException When order creation fails due to an IO error.
     */
    public OrderEntry createOrder(Order order, long merchant, long customer) throws IOException {
        assert order != null;
        SharedState state = stateStore.readFrom();
        HashMap<Long, OrderEntry> store = orderStore.readFrom();

        long orderId = state.oid++;
        OrderEntry oe = new OrderEntry(order, orderId, customer, merchant);
        store.put(orderId, oe);

        orderStore.writeTo(store);
        stateStore.writeTo(state);
        return oe;
    }

    /**
     * Updates the order associated with the given order ID with the
     * specified status provided.
     * @param orderId The specified order ID.
     * @param status The specified status.
     * @return Boolean indicating order update success.
     * @throws IOException Thrown when order update failure is due to an IO error.
     * @throws OrderAlreadyCancelledException Thrown when associated order is cancelled.
     * @throws IllegalStateException Thrown when UUID does not match order's existing UUIDs.
     */
    public boolean updateOrderStatus(long uuid, long orderId, Order.Status status)
            throws IOException, OrderAlreadyCancelledException, IllegalStateException {
        HashMap<Long, OrderEntry> store = orderStore.readFrom();
        OrderEntry entry = store.get(orderId);
        if (entry == null) {
            return false;
        }
        if (entry.status == Order.Status.CANCELLED) {
            throw new OrderAlreadyCancelledException("");
        }
        if (uuid != entry.customerId && uuid != entry.merchantId && uuid != entry.riderId) {
            throw new IllegalStateException("");
        }
        entry.lastStateUpdateId = uuid;
        entry.status = status;
        orderStore.writeTo(store);
        return true;
    }

    /**
     * Retrieves a list of order entries associated
     * to the specified user ID.
     * @param userId The specified user ID.
     * @return A list of order entries associated to the specified user ID.
     * @throws IOException When retrieval fails due to an IO error.
     */
    public List<OrderEntry> getManagedOrders(long userId) throws IOException {
        HashMap<Long, OrderEntry> store = orderStore.readFrom();
        return store.values().stream()
                .filter(entry -> {
                    return entry != null && (
                            entry.customerId == userId ||
                            entry.merchantId == userId ||
                            entry.riderId == userId
                    );
                }).toList();
    }

    /**
     * Retrieves the order status associated with a given order ID.
     * @param orderId The specified user ID.
     * @return The status associated with the given ID.
     * @throws IOException Thrown when retrieval failure stems from an IO error.
     * @throws NoSuchOrderException Thrown when no such order ID exists in the store.
     */
    public Order.Status getOrderStatus(long orderId) throws IOException, NoSuchOrderException {
        HashMap<Long, OrderEntry> store = orderStore.readFrom();
        if (!store.containsKey(orderId)) {
            throw new NoSuchOrderException("");
        }
        return store.get(orderId).status;
    }

    /**
     * Registers a given rider as active and available
     * for delivery routing.
     * @param rider The specified rider.
     * @throws IOException Thrown when registration failure stems from an IO error.
     * @throws IllegalStateException Thrown when Rider is already active.
     */
    public void registerActiveRider(Rider rider) throws IOException, IllegalStateException {
        assert rider != null;
        HashMap<Long, Rider> store = activeRiderStore.readFrom();
        if (store.containsKey(rider.getUuid())) {
            throw new IllegalStateException("");
        }
        store.put(rider.getUuid(), rider);
        activeRiderStore.writeTo(store);
    }

    /**
     * Unregisters a given driver and sets them as inactive and unavailable
     * for delivery routing.
     * @param riderUuid The UUID of the given rider.
     * @return Boolean indicating success or failure of unregistration.
     * @throws IOException Thrown when unregistration failure stems from an IO error.
     */
    public boolean unregisterActiveRider(long riderUuid) throws IOException {
        HashMap<Long, Rider> store = activeRiderStore.readFrom();
        if (!store.containsKey(riderUuid)) {
            return false;
        }
        store.remove(riderUuid);
        activeRiderStore.writeTo(store);
        return true;
    }

    /**
     * Assigns the first active rider to the given order ID.
     * If no available rider exists the method returns false.
     * @param orderId The specified order ID.
     * @return The success status for the assignment, true for success, false otherwise.
     * @throws IOException When assignment failure stems from an IO error.
     * @throws NoSuchOrderException When the order does not exist in the data.
     * @throws OrderAlreadyCancelledException When order is already cancelled.
     */
    public boolean assignActiveRider(long orderId)
            throws IOException, NoSuchOrderException, OrderAlreadyCancelledException {
        HashMap<Long, OrderEntry> orders = orderStore.readFrom();
        HashMap<Long, Rider> riders = activeRiderStore.readFrom();

        OrderEntry order = orders.get(orderId);
        if (order == null) {
            throw new NoSuchOrderException("");
        }
        if (order.status == Order.Status.CANCELLED) {
            throw new OrderAlreadyCancelledException("");
        }
        if (riders.isEmpty()) {
            return false;
        }
        Rider assigned = riders.values().iterator().next();
        order.riderId = assigned.getUuid();
        orderStore.writeTo(orders);
        return true;
    }

    /**
     * Retrieves the menu item catalog from the data.
     * @return A list of menu items from the entire catalog
     * @throws IOException Thrown when retrieval failure stems from an IO error.
     */
    public List<MenuItem> getCatalog() throws IOException {
        return catalogStore.readFrom();
    }

    /**
     * Searches the catalog for menu items that fit the description.
     * @param query String query.
     * @return A list of menu items that meet the query.
     * @throws IOException Thrown when retrieval failure stems from an IO error.
     */
    public List<MenuItem> searchCatalog(String query) throws IOException {
        List<MenuItem> all = catalogStore.readFrom();
        if (query == null || query.isBlank()) {
            return all;
        }
        String q = query.toLowerCase().trim();
        return all.stream()
                .filter(item -> item.getName() != null && item.getName().toLowerCase().contains(q))
                .toList();
    }
}