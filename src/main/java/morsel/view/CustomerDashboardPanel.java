package morsel.view;

import morsel.model.actors.Actor;
import morsel.model.actors.Customer;

import javax.swing.*;
import java.awt.*;

/**
 * Customer dashboard panel for viewing and managing orders.
 */
public class CustomerDashboardPanel extends BaseDashboardPanel {
    
    private JTextArea ordersTextArea;
    private JButton refreshButton;
    private JButton searchButton;
    private JTextField searchField;
    
    public CustomerDashboardPanel(Customer customer, Runnable onLogout, Runnable onProfile) {
        super(customer, onLogout, onProfile);
    }
    
    @Override
    protected String getDashboardTitle() {
        return "Customer Dashboard";
    }
    
    @Override
    protected String getActorDisplayName() {
        return "Customer";
    }
    
    @Override
    protected JPanel createMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        searchField = new JTextField(20);
        searchButton = new JButton("Search Menu");
        refreshButton = new JButton("Refresh Orders");
        
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        
        // Content tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Orders tab
        JPanel ordersPanel = new JPanel(new BorderLayout());
        ordersPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel ordersLabel = new JLabel("Your Orders");
        ordersLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ordersPanel.add(ordersLabel, BorderLayout.NORTH);
        
        ordersTextArea = new JTextArea();
        ordersTextArea.setEditable(false);
        ordersTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(ordersTextArea);
        ordersPanel.add(scrollPane, BorderLayout.CENTER);
        
        tabbedPane.addTab("Orders", ordersPanel);
        
        // Menu tab
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel menuLabel = new JLabel("Available Menu Items");
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuPanel.add(menuLabel, BorderLayout.NORTH);
        
        JTextArea menuTextArea = new JTextArea();
        menuTextArea.setEditable(false);
        menuTextArea.setText("Menu items will be displayed here.");
        menuTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane menuScrollPane = new JScrollPane(menuTextArea);
        menuPanel.add(menuScrollPane, BorderLayout.CENTER);
        
        tabbedPane.addTab("Menu", menuPanel);
        
        // Cart tab
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel cartLabel = new JLabel("Your Cart");
        cartLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cartPanel.add(cartLabel, BorderLayout.NORTH);
        
        JTextArea cartTextArea = new JTextArea();
        cartTextArea.setEditable(false);
        cartTextArea.setText("Your cart is empty.");
        cartTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane cartScrollPane = new JScrollPane(cartTextArea);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);
        
        JPanel cartButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton checkoutButton = new JButton("Checkout");
        cartButtonPanel.add(checkoutButton);
        cartPanel.add(cartButtonPanel, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Cart", cartPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    @Override
    protected void onDashboard() {
        // Already on dashboard, refresh
        refreshOrders();
    }
    
    public void refreshOrders() {
        ordersTextArea.setText("Loading orders...");
        // In a real implementation, this would fetch orders from the server
        ordersTextArea.setText("No orders found.\n\nPlace your first order by browsing the menu!");
    }
    
    public void setOrdersText(String text) {
        ordersTextArea.setText(text);
    }
    
    public JButton getRefreshButton() {
        return refreshButton;
    }
    
    public JButton getSearchButton() {
        return searchButton;
    }
    
    public String getSearchQuery() {
        return searchField.getText().trim();
    }
}
