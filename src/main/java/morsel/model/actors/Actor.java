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
    protected String username;
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

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
