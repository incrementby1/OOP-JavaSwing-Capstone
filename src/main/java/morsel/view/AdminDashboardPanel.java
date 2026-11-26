package morsel.view;

import morsel.controller.Server;
import morsel.model.actors.Actor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Admin dashboard panel for system management.
 */
public class AdminDashboardPanel extends BaseDashboardPanel {

    private final Server server;
    private JTextArea usersTextArea;
    private JTextArea ordersTextArea;
    private JButton refreshUsersButton;
    private JButton refreshOrdersButton;
    private JButton refreshRidersButton;
    private JLabel activeRidersLabel;
    
    public AdminDashboardPanel(Actor admin, Runnable onLogout, Runnable onProfile, Server server) {
        super(admin, onLogout, onProfile);
        this.server = server;
    }
    
    @Override
    protected String getDashboardTitle() {
        return "Admin Dashboard";
    }
    
    @Override
    protected String getActorDisplayName() {
        return "Admin";
    }
    
    @Override
    protected JPanel createMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Content tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Users tab
        JPanel usersPanel = new JPanel(new BorderLayout());
        usersPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JPanel usersHeaderPanel = new JPanel(new BorderLayout());
        JLabel usersLabel = new JLabel("System Users");
        usersLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        usersHeaderPanel.add(usersLabel, BorderLayout.WEST);
        
        refreshUsersButton = new JButton("Refresh Users");
        usersHeaderPanel.add(refreshUsersButton, BorderLayout.EAST);
        usersPanel.add(usersHeaderPanel, BorderLayout.NORTH);
        
        usersTextArea = new JTextArea();
        usersTextArea.setEditable(false);
        usersTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane usersScrollPane = new JScrollPane(usersTextArea);
        usersPanel.add(usersScrollPane, BorderLayout.CENTER);
        
        tabbedPane.addTab("Users", usersPanel);
        
        // Orders tab
        JPanel ordersPanel = new JPanel(new BorderLayout());
        ordersPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JPanel ordersHeaderPanel = new JPanel(new BorderLayout());
        JLabel ordersLabel = new JLabel("All Orders");
        ordersLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ordersHeaderPanel.add(ordersLabel, BorderLayout.WEST);
        
        refreshOrdersButton = new JButton("Refresh Orders");
        ordersHeaderPanel.add(refreshOrdersButton, BorderLayout.EAST);
        ordersPanel.add(ordersHeaderPanel, BorderLayout.NORTH);
        
        ordersTextArea = new JTextArea();
        ordersTextArea.setEditable(false);
        ordersTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane ordersScrollPane = new JScrollPane(ordersTextArea);
        ordersPanel.add(ordersScrollPane, BorderLayout.CENTER);
        
        tabbedPane.addTab("Orders", ordersPanel);
        
        // System tab
        JPanel systemPanel = new JPanel(new BorderLayout());
        systemPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel systemLabel = new JLabel("System Information");
        systemLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        systemPanel.add(systemLabel, BorderLayout.NORTH);
        
        JPanel systemInfoPanel = new JPanel(new GridBagLayout());
        systemInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        systemInfoPanel.add(new JLabel("Total Users: 0"), gbc);
        gbc.gridy++;
        systemInfoPanel.add(new JLabel("Total Orders: 0"), gbc);
        gbc.gridy++;
        activeRidersLabel = new JLabel("Active Riders: 0");
        systemInfoPanel.add(activeRidersLabel, gbc);
        gbc.gridy++;
        refreshRidersButton = new JButton("Refresh Active Riders");
        systemInfoPanel.add(refreshRidersButton, gbc);
        gbc.gridy++;
        systemInfoPanel.add(new JLabel("Active Merchants: 0"), gbc);
        
        systemPanel.add(systemInfoPanel, BorderLayout.CENTER);
        
        tabbedPane.addTab("System", systemPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        return mainPanel;
    }

    @Override
    protected void onDashboard() {
        refreshUsers();
        refreshOrders();
        refreshActiveRiders();
    }
    
    public void refreshUsers() {
        usersTextArea.setText("Loading users...");
        // In a real implementation, this would fetch all users from the server
        usersTextArea.setText("No users loaded.\n\nClick 'Refresh Users' to load user data.");
    }
    
    public void refreshOrders() {
        ordersTextArea.setText("Loading orders...");
        // In a real implementation, this would fetch all orders from the server
        ordersTextArea.setText("No orders loaded.\n\nClick 'Refresh Orders' to load order data.");
    }

    /**
     * Refreshes the active rider count using the server.
     *
     * @throws IOException When retrieving active riders fails.
     */
    public void refreshActiveRiders() {
        try {
            int count = server.getActiveRiders().size();
            activeRidersLabel.setText("Active Riders: " + count);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to retrieve active riders: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Returns the button used to refresh the active rider count.
     *
     * @return The active rider refresh button.
     */
    
    public void setUsersText(String text) {
        usersTextArea.setText(text);
    }
    
    public void setOrdersText(String text) {
        ordersTextArea.setText(text);
    }
    
    public JButton getRefreshUsersButton() {
        return refreshUsersButton;
    }
    
    public JButton getRefreshOrdersButton() {
        return refreshOrdersButton;
    }

    public JButton getRefreshRidersButton() { return refreshRidersButton; }
}
