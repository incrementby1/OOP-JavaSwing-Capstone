package morsel.model.actors;

public class Admin extends Actor {
    
    public Admin(long uuid) {
        super(Actor.Role.ADMIN, uuid);
    }
}
