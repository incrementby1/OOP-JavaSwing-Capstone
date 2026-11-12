package foodDelivery.controller.states;

import foodDelivery.controller.Client;

import javax.swing.*;
import java.awt.*;

public class ClientAuthState extends ClientState {
    private final JPanel panel;

    public ClientAuthState() {
        super(Client.State.AUTHENTICATION);
        this.panel = new JPanel();

        // Build your UI manually with standard Swing components:
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Username:"));
        panel.add(new JTextField(15));

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            System.out.println("Login clicked!");
            // e.g., client.transitionTo(new ClientDashboardState(client));
        });
        panel.add(loginButton);
    }

    @Override
    public JPanel getView() {
        return panel;
    }

    @Override
    public void onEntry() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void onSuspend() {

    }

    @Override
    public void onResume() {

    }
}
