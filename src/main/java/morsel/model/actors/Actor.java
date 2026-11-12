package morsel.model.actors;

public class Actor {
    public enum Role {
        ADMIN,
        CUSTOMER,
        MERCHANT,
        RIDER
    }
    protected long uuid;
    protected Role role;
    public Actor() {}
    public Actor(Role role, long uuid) {
        this.uuid = uuid;
        this.role = role;
    }
    public long getUuid() {
        return uuid;
    }
    public Role getRole() {
        return role;
    }
}
