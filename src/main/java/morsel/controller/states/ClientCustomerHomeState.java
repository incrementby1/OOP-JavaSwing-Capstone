package morsel.controller.states;

import morsel.controller.Client;
import morsel.model.actors.Customer;
import morsel.view.CustomerDashboardPanel;

import javax.swing.*;

/**
 * Customer home state for the client application.
 * Manages the customer dashboard with order and menu functionality.
 */
public class ClientCustomerHomeState extends ClientState {
    
    private final CustomerDashboardPanel dashboardPanel;
    private final Client client;
    
    public ClientCustomerHomeState(Client client) {
        super(client, Client.State.CUSTOMER_HOME);
        this.client = client;
        
        Customer customer = (Customer) client.getContext().actor;
        this.dashboardPanel = new CustomerDashboardPanel(
            customer,
            this::logout,
            this::navigateToProfile
        );
        
        setupEventListeners();
    }
    
    private void setupEventListeners() {
        dashboardPanel.getRefreshButton().addActionListener(e -> {
            refreshOrders();
        });
        
        dashboardPanel.getSearchButton().addActionListener(e -> {
            String query = dashboardPanel.getSearchQuery();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(
                    dashboardPanel,
                    "Please enter a search term.",
                    "Search",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            try {
                // Search catalog through server
                // var results = client.server.searchCatalog(query);
                JOptionPane.showMessageDialog(
                    dashboardPanel,
                    "Search functionality will be implemented.",
                    "Search",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    dashboardPanel,
                    "Search failed: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
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
