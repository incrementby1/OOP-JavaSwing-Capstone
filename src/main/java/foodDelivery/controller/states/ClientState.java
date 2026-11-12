package foodDelivery.controller.states;
import foodDelivery.controller.Client;

import javax.swing.*;

/**
 * Base class for all
 * client states. Extend this class
 * to create a new client state. Every ClientState
 * subclass must link to a Client.State field and
 * must expose their own view.
 */
public abstract class ClientState {

    /**
     * Client.State enum value that corresponds
     * to this specific state. (Fixed at definition)
     * Note that this assumes a 1:1 mapping between the
     * enum value and a ClientState subclass.
     */
    public final Client.State linkedState;
    public final Client client;

    /**
     * Whether the current state is considered
     * disabled or enabled by the client. This
     * state is disabled when it is not the
     * topmost state on the client state stack.
     */
    public boolean enabled;
    public ClientState(Client client, Client.State state) {
        this.client = client;
        linkedState = state;
        enabled = true;
    }
    public abstract JPanel getView();

    /**
     * Life-cycle method. Called upon
     * state entry in Client.
     */
    public abstract void onEntry();

    /**
     * Lifecycle method. Called upon
     * state exit in Client. When enabled
     * is set to false as this is called,
     * the state is exiting as a suspended
     * intermediate rather than the currently
     * active state.
     */
    public abstract void onExit();

    /**
     * Lifecycle method. Called when
     * this state is disabled as a new state
     * is pushed on top of it.
     */
    public abstract void onSuspend();

    /**
     * Lifecycle method. Called when this
     * state is re-enabled as the active state
     * on top of this state is removed. Not
     * called when this state exits as a
     * suspended intermediate.
     */
    public abstract void onResume();
}
