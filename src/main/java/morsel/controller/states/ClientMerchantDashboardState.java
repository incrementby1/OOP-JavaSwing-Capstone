package morsel.controller.states;

import morsel.controller.Client;
import morsel.model.actors.Merchant;
import morsel.view.MerchantDashboardPanel;

import javax.swing.*;

/**
 * Merchant dashboard state for the client application.
 * Manages merchant operations including order management and menu editing.
 */
public class ClientMerchantDashboardState extends ClientState {
    
    private final MerchantDashboardPanel dashboardPanel;
    private final Client client;
    
    public ClientMerchantDashboardState(Client client) {
        super(client, Client.State.MERCHANT_DASHBOARD);
        this.client = client;
        
        Merchant merchant = (Merchant) client.getContext().actor;
        this.dashboardPanel = new MerchantDashboardPanel(
            merchant,
            this::logout,
            this::navigateToProfile
        );
        
        setupEventListeners();
    }
    
    private void setupEventListeners() {
        dashboardPanel.getRefreshButton().addActionListener(e -> {
            refreshOrders();
        });
    }
    
    private void refreshOrders() {
        try {
            // In a real implementation:
            // long userId = client.getContext().actor.getUuid();
            // var orders = client.server.getManagedOrders(userId);
            // StringBuilder sb = new StringBuilder();
            // for (var entry : orders) {
            //     sb.append(String.format("Order #%d: %s\n", entry.orderId, entry.status));
            // }
            // dashboardPanel.setOrdersText(sb.toString());
            
            dashboardPanel.refreshOrders();
            JOptionPane.showMessageDialog(
                dashboardPanel,
                "Orders refreshed successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                dashboardPanel,
                "Failed to refresh orders: " + ex.getMessage(),
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
        refreshOrders();
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
        refreshOrders();
    }
}
