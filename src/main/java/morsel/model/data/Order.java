package morsel.model.data;

public class Order {
    public enum Status {
        SENT,
        PREPARING,
        DRIVER_ASSIGNMENT,
        DELIVERY,
        DELIVERED,
        RECEIVED,
        CANCELLED
    }
}
