package morsel.model;
import morsel.model.actors.Actor;

/**
 * Client context. Stores shared
 * data to be passed around to
 * individual ClientState subclasses.
 */
public class ClientContext  {
    public Actor actor;
    public ClientContext() {
        actor = null;
    }
    /**
     * Clears the shared context. Used
     * when logging out.
     */
    public void clear() {
        actor = null;
    }
}
