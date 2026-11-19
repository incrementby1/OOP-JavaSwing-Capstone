package morsel.model.actors;

public class Customer extends Actor {
    
    public Customer(long uuid) {
        super(Actor.Role.CUSTOMER, uuid);
    }
}
