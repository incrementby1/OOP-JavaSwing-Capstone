package morsel.model.exceptions;

public class OrderAlreadyCancelledException extends RuntimeException {
    public OrderAlreadyCancelledException(String message) {
        super(message);
    }
}
