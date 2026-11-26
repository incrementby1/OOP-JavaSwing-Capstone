package morsel.controller.states;

import morsel.controller.Client;
import morsel.model.actors.Rider;
import morsel.view.RiderDashboardPanel;

import javax.swing.*;

/**
 * Rider dashboard state for the client application.
 * Manages delivery operations for riders.
 */
public class ClientRiderDashboardState extends ClientState {
    
    private final RiderDashboardPanel dashboardPanel;
    private final Client client;
    
    public ClientRiderDashboardState(Client client) {
        super(client, Client.State.RIDER_DASHBOARD);
        this.client = client;
        
        Rider rider = (Rider) client.getContext().actor;
        this.dashboardPanel = new RiderDashboardPanel(
                rider,
                this::logout,
                this::navigateToProfile,
                client.server
        );
        
        setupEventListeners();
    }

    private void setupEventListeners() {
        dashboardPanel.getRefreshButton().addActionListener(e -> {
            refreshDeliveries();
        });
    }
    
    private void refreshDeliveries() {
        try {
            // In a real implementation:
            // long userId = client.getContext().actor.getUuid();
            // var orders = client.server.getManagedOrders(userId);
            // StringBuilder sb = new StringBuilder();
            // for (var entry : orders) {
            //     sb.append(String.format("Delivery #%d: %s\n", entry.orderId, entry.status));
            // }
            // dashboardPanel.setDeliveriesText(sb.toString());
            
            dashboardPanel.refreshDeliveries();
            JOptionPane.showMessageDialog(
                dashboardPanel,
                "Deliveries refreshed successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                dashboardPanel,
                "Failed to refresh deliveries: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void navigateToProfile() {
        client.transitionTo(new ClientProfileState(client));
    }
    
    private void logout() {
        client.backtrack(-1); // Go back to authentication
    }
    
    @Override
    public JPanel getView() {
        return dashboardPanel;
    }
    
    @Override
    public void onEntry() {
        refreshDeliveries();
    }
    
    @Override
    public void onExit() {
        // Clean up if needed
    }
    
    @Override
    public void onSuspend() {
        // Save state if needed
    }
    
    @Override
    public void onResume() {
        refreshDeliveries();
    }
}
