package morsel.model.data;

import morsel.model.actors.Actor; /**
 * Authentication file entry.
 * Used for serialization to JSON to
 * credentials.json.
 */
public class UserEntry {
    public String username;
    public String hashedPassword;
    public Actor.Role role;
    public long uuid;

    public UserEntry() {}
    public UserEntry(UserEntry other) {
        username = other.username;
        hashedPassword = other.hashedPassword;
        role = other.role;
        uuid = other.uuid;
    }
    public UserEntry(String un, String hp, Actor.Role r, long uuid) {
        username = un;
        hashedPassword = hp;
        this.uuid = uuid;
        role = r;
    }
}
