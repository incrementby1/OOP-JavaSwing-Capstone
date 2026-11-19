package morsel.view;

import morsel.model.actors.Merchant;

import javax.swing.*;
import java.awt.*;

/**
 * Merchant dashboard panel for managing restaurant operations.
 */
public class MerchantDashboardPanel extends BaseDashboardPanel {
    
    private JTextArea ordersTextArea;
    private JTextArea menuTextArea;
    private JButton refreshButton;
    
    public MerchantDashboardPanel(Merchant merchant, Runnable onLogout, Runnable onProfile) {
        super(merchant, onLogout, onProfile);
    }
    
    @Override
    protected String getDashboardTitle() {
        return "Merchant Dashboard";
    }
    
    @Override
    protected String getActorDisplayName() {
        return "Merchant";
    }
    
    @Override
    protected JPanel createMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header with refresh button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        refreshButton = new JButton("Refresh");
        headerPanel.add(refreshButton, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Orders tab
        JPanel ordersPanel = new JPanel(new BorderLayout());
        ordersPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel ordersLabel = new JLabel("Pending Orders");
        ordersLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ordersPanel.add(ordersLabel, BorderLayout.NORTH);
        
        ordersTextArea = new JTextArea();
        ordersTextArea.setEditable(false);
        ordersTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane ordersScrollPane = new JScrollPane(ordersTextArea);
        ordersPanel.add(ordersScrollPane, BorderLayout.CENTER);
        
        tabbedPane.addTab("Orders", ordersPanel);
        
        // Menu tab
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel menuLabel = new JLabel("Your Menu Items");
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuPanel.add(menuLabel, BorderLayout.NORTH);
        
        menuTextArea = new JTextArea();
        menuTextArea.setEditable(false);
        menuTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane menuScrollPane = new JScrollPane(menuTextArea);
        menuPanel.add(menuScrollPane, BorderLayout.CENTER);
        
        JPanel menuButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addItemButton = new JButton("Add Menu Item");
        JButton editItemButton = new JButton("Edit Menu Item");
        JButton removeItemButton = new JButton("Remove Menu Item");
        
        menuButtonPanel.add(addItemButton);
        menuButtonPanel.add(editItemButton);
        menuButtonPanel.add(removeItemButton);
        menuPanel.add(menuButtonPanel, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Menu Management", menuPanel);
        
        // Analytics tab
        JPanel analyticsPanel = new JPanel(new BorderLayout());
        analyticsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel analyticsLabel = new JLabel("Sales Analytics");
        analyticsLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        analyticsPanel.add(analyticsLabel, BorderLayout.NORTH);
        
        JTextArea analyticsTextArea = new JTextArea();
        analyticsTextArea.setEditable(false);
        analyticsTextArea.setText("Sales analytics will be displayed here.");
        analyticsTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane analyticsScrollPane = new JScrollPane(analyticsTextArea);
        analyticsPanel.add(analyticsScrollPane, BorderLayout.CENTER);
        
        tabbedPane.addTab("Analytics", analyticsPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    @Override
    protected void onDashboard() {
        refreshOrders();
    }
    
    public void refreshOrders() {
        ordersTextArea.setText("Loading orders...");
        // In a real implementation, this would fetch orders from the server
        ordersTextArea.setText("No pending orders.\n\nOrders will appear here when customers place them.");
    }
    
    public void setOrdersText(String text) {
        ordersTextArea.setText(text);
    }
    
    public JButton getRefreshButton() {
        return refreshButton;
    }
}
