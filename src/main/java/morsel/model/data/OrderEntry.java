package morsel.model.data;

/**
 * Order entry. Can be active/inactive specified
 * by the given activeStatus. Stores an order ID
 * for convenient tracking.
 */
public class OrderEntry {
    public Order order;
    public long orderId;
    public long customerId;
    public long riderId;
    public long merchantId;
    public long lastStateUpdateId;
    public Order.Status status;
    public OrderEntry() {}
    public OrderEntry(Order o, long oid, long cid, long mid) {
        order = o;
        orderId = oid;
        customerId = cid;
        merchantId = mid;
    }
}
