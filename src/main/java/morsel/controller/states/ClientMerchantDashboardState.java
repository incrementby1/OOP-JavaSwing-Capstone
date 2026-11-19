package morsel.controller.states;

import morsel.controller.Client;
import morsel.model.actors.Merchant;
import morsel.model.data.MenuItem;
import morsel.view.MerchantDashboardPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

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
        refreshMenu();
    }
    
    private void setupEventListeners() {
        dashboardPanel.getRefreshButton().addActionListener(e -> {
            refreshOrders();
            refreshMenu();
        });
        
        dashboardPanel.getAddItemButton().addActionListener(e -> {
            showAddMenuItemDialog();
        });
        
        dashboardPanel.getEditItemButton().addActionListener(e -> {
            int selectedRow = dashboardPanel.getSelectedMenuItemRow();
            if (selectedRow >= 0) {
                showEditMenuItemDialog(selectedRow);
            } else {
                JOptionPane.showMessageDialog(
                    dashboardPanel,
                    "Please select a menu item to edit.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        });
        
        dashboardPanel.getRemoveItemButton().addActionListener(e -> {
            int selectedRow = dashboardPanel.getSelectedMenuItemRow();
            if (selectedRow >= 0) {
                removeMenuItem(selectedRow);
            } else {
                JOptionPane.showMessageDialog(
                    dashboardPanel,
                    "Please select a menu item to remove.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        });
    }
    
    private void showAddMenuItemDialog() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JTextField nameField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JTextField categoryField = new JTextField(20);
        
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        
        int result = JOptionPane.showConfirmDialog(
            dashboardPanel,
            panel,
            "Add Menu Item",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String description = descriptionField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                String category = categoryField.getText().trim();
                
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        dashboardPanel,
                        "Name cannot be empty.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                
                long merchantId = dashboardPanel.getMerchant().getUuid();
                MenuItem newItem = new MenuItem(name, description, price, merchantId, category);
                
                // Add to server
                client.server.addMenuItem(newItem);
                
                // Update UI
                dashboardPanel.addMenuItem(newItem);
                
                JOptionPane.showMessageDialog(
                    dashboardPanel,
                    "Menu item added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    dashboardPanel,
                    "Invalid price format. Please enter a valid number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                    dashboardPanel,
                    "Failed to add menu item: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    private void showEditMenuItemDialog(int row) {
        List<MenuItem> currentItems = getCurrentMenuItems();
        if (row >= currentItems.size()) {
            JOptionPane.showMessageDialog(
                dashboardPanel,
                "Invalid menu item selection.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        MenuItem item = currentItems.get(row);
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JTextField nameField = new JTextField(item.getName(), 20);
        JTextField descriptionField = new JTextField(item.getDescription() != null ? item.getDescription() : "", 20);
        JTextField priceField = new JTextField(String.valueOf(item.getPrice()), 20);
        JTextField categoryField = new JTextField(item.getCategory() != null ? item.getCategory() : "", 20);
        
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        
        int result = JOptionPane.showConfirmDialog(
            dashboardPanel,
            panel,
            "Edit Menu Item",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String description = descriptionField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                String category = categoryField.getText().trim();
                
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        dashboardPanel,
                        "Name cannot be empty.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                
                long merchantId = dashboardPanel.getMerchant().getUuid();
                MenuItem updatedItem = new MenuItem(name, description, price, merchantId, category);
                
                // Update on server
                client.server.updateMenuItem(row, updatedItem);
                
                // Update UI
                dashboardPanel.updateMenuItem(row, updatedItem);
                
                JOptionPane.showMessageDialog(
                    dashboardPanel,
                    "Menu item updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    dashboardPanel,
                    "Invalid price format. Please enter a valid number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                    dashboardPanel,
                    "Failed to update menu item: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    private void removeMenuItem(int row) {
        int confirm = JOptionPane.showConfirmDialog(
            dashboardPanel,
            "Are you sure you want to remove this menu item?",
            "Remove Menu Item",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Remove from server
                client.server.removeMenuItem(row);
                
                // Update UI
                dashboardPanel.removeMenuItem(row);
                
                JOptionPane.showMessageDialog(
                    dashboardPanel,
                    "Menu item removed successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                    dashboardPanel,
                    "Failed to remove menu item: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    private void refreshOrders() {
        try {
            long userId = client.getContext().actor.getUuid();
            var orders = client.server.getManagedOrders(userId);
            StringBuilder sb = new StringBuilder();
            for (var entry : orders) {
                sb.append(String.format("Order #%d: %s\n", entry.orderId, entry.status));
            }
            if (sb.length() == 0) {
                sb.append("No pending orders.");
            }
            dashboardPanel.setOrdersText(sb.toString());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                dashboardPanel,
                "Failed to refresh orders: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void refreshMenu() {
        try {
            long merchantId = dashboardPanel.getMerchant().getUuid();
            List<MenuItem> items = client.server.getMerchantMenu(merchantId);
            dashboardPanel.setMenuItems(items);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                dashboardPanel,
                "Failed to load menu items: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private List<MenuItem> getCurrentMenuItems() {
        try {
            long merchantId = dashboardPanel.getMerchant().getUuid();
            return client.server.getMerchantMenu(merchantId);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                dashboardPanel,
                "Failed to get menu items: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return List.of();
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
        refreshMenu();
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
        refreshMenu();
    }
}
