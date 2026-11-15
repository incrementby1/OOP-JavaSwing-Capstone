package org.foodDelivery.service;

import org.foodDelivery.forms.Client;

/**
 * ClientContext class. Must be extended
 * for each Client.State enum value. Can only be
 * set at initialization.
 */
public abstract class ClientContext {
    private final org.foodDelivery.forms.Client.State linkedState;
    public ClientContext(org.foodDelivery.forms.Client.State state) {
        linkedState = state;
    }
    public Client.State getLinkedState() {
        return linkedState;
    }

    /**
     * Called when transitioning from an existing state,
     * whether backtracking or moving forward from an
     * existing state.
     * @param old The existing client context.
     */
    public abstract void onEntry(ClientContext oldState);
    public abstract void onExit(boolean fastTrack);
    public abstract void onResurface(ClientContext oldState);
    public abstract void onSubmerge(ClientContext newState);
}

