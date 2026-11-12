package foodDelivery.controller;

import foodDelivery.controller.states.ClientAuthState;
import foodDelivery.controller.states.ClientState;
import foodDelivery.model.ClientContext;

import javax.swing.*;
import java.util.Stack;

/**
 * Main client controller.
 * Call Client.run() in Main
 * to start the application.
 */
public class Client {
    private static final int INITIAL_WIDTH = 1280;
    private static final int INITIAL_HEIGHT = 720;
    private static final String NAME = "Food Delivery (DEBUG)";
    /**
     * Client State enum.
     * Linked 1:1 to a ClientState
     * subclass.
     */
    public enum State {
        AUTHENTICATION,
    }
    /**
     * Internal state stack.
     * MUST BE PRIVATE. Do not implement
     * getters/setters for this as
     * it is an implementation detail.
     */
    private final Stack<ClientState> state;
    private final JFrame mainFrame;
    private final ClientContext context;
    public final Server server;

    public Client(Server server) {
        this.server = server;
        state = new Stack<>();
        context = new ClientContext();
        mainFrame = new JFrame(NAME);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
        mainFrame.setLocationRelativeTo(null); // null to center on screen.
    }
    public JFrame getMainFrame() {
        return mainFrame;
    }
    /**
     * Pushes a new client state on top of the stack.
     * suspend the current state by calling
     * state.onSuspend() and runs state.onEntry() on the
     * specified client state.
     * @param cs Client state to be pushed on the stack.
     */
    public void transitionTo(ClientState cs) {
        assert cs != null;

        if (!state.isEmpty()) {
            ClientState top = state.peek();
            top.onSuspend();
            top.enabled = false;
        }

        state.push(cs);
        cs.onEntry();
        cs.enabled = true;

        // Render. Called to apply change at runtime.
        mainFrame.setContentPane(cs.getView());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /**
     * Backtracks the entire stack by N states.
     * Useful for resetting states (e.g. logging out).
     * Pass -1 as an argument to pop the entire
     * stack except for the very last state.
     * @param n The number of states to backtrack. Must not
     *          exceed size-1 of the stack. (or -1).
     */
    public void backtrack(int n) {
        assert n != 0 && n >= -1;
        if (n == -1) {
            n = state.size() - 1;
        }
        assert n > 0;
        for (int i = 0; i < n; ++i) {
            ClientState top = state.pop();

            // top.onResume() is intentionally not called
            // as these are intermediate states being
            // popped off of the stack. top.enabled
            // is also not set to true for the same reason.
            top.onExit();
        }
        ClientState current = state.peek();
        current.enabled = true;
        current.onResume();

        // When only authentication
        // remains, reset state.
        if (state.size() == 1) {
            context.clear();
        }

        // Render. Called to apply change at runtime.
        mainFrame.setContentPane(current.getView());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /**
     * Entry point function.
     * Call in main() via client.run()
     * to start the application.
     */
    public void run() {
        transitionTo(new ClientAuthState(this));
        mainFrame.setVisible(true);
    }
}
