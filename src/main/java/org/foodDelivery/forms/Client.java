package org.foodDelivery.forms;

import org.foodDelivery.service.ClientContext;

import java.util.*;

/**
 * Primary client class.
 * Call start() from main.
 */
public class Client {
    public enum State {
        AUTHENTICATION,
        DASHBOARD,
        PROFILE,
        SEARCH,
        SEARCH_RESULTS,
        ORDER,
        CHECKOUT,
        PAYMENT,
        ORDER_TRACKING,
        ORDER_FEEDBACK
    }
    private static class ClientStateEntry {
        State state;
        ClientContext context;

        public ClientStateEntry(State s, ClientContext cc) {
            // Allows null contexts. Check accordingly.
            state = s;
            context = cc;
        }
        public ClientStateEntry(ClientContext cc) {
            if (cc == null) {
                String msg = "Cannot push null client context.";
                throw new IllegalArgumentException(msg);
            }
            state = cc.getLinkedState();
            context = cc;
        }
    }
    private Deque<ClientStateEntry> stateEntries;

    /**
     * Backtracks the current state into the
     * previous state, calling transitionFrom on the
     * existing state to update it.
     */
    public void backtrack() {
        if (stateEntries.size() == 1) {
            String msg = "Cannot backtrack from only active state.";
            throw new IllegalStateException(msg);
        }
        ClientStateEntry topmost = stateEntries.pop();
        ClientStateEntry current = stateEntries.peek();
        if (current == null) {
            throw new IllegalStateException("Unreachable.");
        }
        current.context.onResurface(topmost.context);
        topmost.context.onExit(false);
    }
    public void backtrackN(int n) {
        if (n == -1) {
            n = stateEntries.size() - 1;
            if (n == 0) {
                String msg = "Cannot backtrack from only active state.";
                throw new IllegalArgumentException(msg);
            }
        }
        if (n <= 0 || n >= stateEntries.size() - 1) {
            throw new IllegalArgumentException("n cannot be <=0 or >= size-1.");
        }
        for (int i = 0; i < n - 1; ++i) {
            ClientStateEntry topmost = stateEntries.pop();
            topmost.context.onExit(true);
        }
        ClientStateEntry topmost = stateEntries.pop();
        topmost.context.onExit(false);
    }

    public Client() {
        ClientStateEntry cse = new ClientStateEntry(State.AUTHENTICATION, null);
        stateEntries = new ArrayDeque<>();
        stateEntries.push(cse);
    }

    // =============== API =============== //
    public ClientContext getTopmostContext() {
        if (stateEntries.isEmpty()) {
            return null;
        }
        return stateEntries.peek().context;
    }
    public State getTopmostState() {
        if (stateEntries.isEmpty()) {
            return null;
        }
        return stateEntries.peek().state;
    }
    public void transitionClientState(ClientContext newContext) {
        if (stateEntries.isEmpty()) {
            throw new IllegalStateException("State cannot be empty.");
        }
        if (newContext == null) {
            throw new IllegalArgumentException("Client context cannot be null.");
        }
        ClientStateEntry top = stateEntries.peek();
        if (top == null) {
            throw new IllegalStateException("Unreachable.");
        }
        top.context.onSubmerge(newContext);
        newContext.onEntry(top.context);
        State nstate = newContext.getLinkedState();
        ClientStateEntry newEntry = new ClientStateEntry(nstate, newContext);
        stateEntries.push(newEntry);
    }

    /**
     * Client entry point method.
     */
    public void start() {

    }
}
