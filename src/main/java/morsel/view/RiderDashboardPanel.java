package morsel.view;

import morsel.model.actors.Rider;

import javax.swing.*;
import java.awt.*;

/**
 * Rider dashboard panel for delivery management.
 */
public class RiderDashboardPanel extends BaseDashboardPanel {
    
    private JTextArea deliveryTextArea;
    private JButton refreshButton;
    private JButton toggleAvailabilityButton;
    private boolean isAvailable;
    
    public RiderDashboardPanel(Rider rider, Runnable onLogout, Runnable onProfile) {
        super(rider, onLogout, onProfile);
        this.isAvailable = false;
    }
    
    @Override
    protected String getDashboardTitle() {
        return "Rider Dashboard";
    }
    
    @Override
    protected String getActorDisplayName() {
        return "Rider";
    }
    
    @Override
    protected JPanel createMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JPanel availabilityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel availabilityLabel = new JLabel("Status: Unavailable");
        availabilityLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        availabilityLabel.setForeground(Color.RED);
        availabilityPanel.add(availabilityLabel);
        
        toggleAvailabilityButton = new JButton("Go Online");
        toggleAvailabilityButton.setBackground(new Color(0, 150, 0));
        toggleAvailabilityButton.setForeground(Color.WHITE);
        availabilityPanel.add(toggleAvailabilityButton);
        
        headerPanel.add(availabilityPanel, BorderLayout.WEST);
        
        refreshButton = new JButton("Refresh");
        headerPanel.add(refreshButton, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Available Deliveries tab
        JPanel deliveriesPanel = new JPanel(new BorderLayout());
        deliveriesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel deliveriesLabel = new JLabel("Available Deliveries");
        deliveriesLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        deliveriesPanel.add(deliveriesLabel, BorderLayout.NORTH);
        
        deliveryTextArea = new JTextArea();
        deliveryTextArea.setEditable(false);
        deliveryTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane deliveriesScrollPane = new JScrollPane(deliveryTextArea);
        deliveriesPanel.add(deliveriesScrollPane, BorderLayout.CENTER);
        
        JPanel deliveryActionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton acceptButton = new JButton("Accept Selected");
        JButton rejectButton = new JButton("Reject Selected");
        deliveryActionPanel.add(acceptButton);
        deliveryActionPanel.add(rejectButton);
        deliveriesPanel.add(deliveryActionPanel, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Available Deliveries", deliveriesPanel);
        
        // Current Deliveries tab
        JPanel currentDeliveriesPanel = new JPanel(new BorderLayout());
        currentDeliveriesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel currentLabel = new JLabel("Current Deliveries");
        currentLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        currentDeliveriesPanel.add(currentLabel, BorderLayout.NORTH);
        
        JTextArea currentTextArea = new JTextArea();
        currentTextArea.setEditable(false);
        currentTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane currentScrollPane = new JScrollPane(currentTextArea);
        currentDeliveriesPanel.add(currentScrollPane, BorderLayout.CENTER);
        
        tabbedPane.addTab("Current Deliveries", currentDeliveriesPanel);
        
        // History tab
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel historyLabel = new JLabel("Delivery History");
        historyLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        historyPanel.add(historyLabel, BorderLayout.NORTH);
        
        JTextArea historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);
        historyTextArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane historyScrollPane = new JScrollPane(historyTextArea);
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);
        
        tabbedPane.addTab("Delivery History", historyPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    @Override
    protected void onDashboard() {
        refreshDeliveries();
    }
    
    public void refreshDeliveries() {
        deliveryTextArea.setText("Loading deliveries...");
        // In a real implementation, this would fetch deliveries from the server
        deliveryTextArea.setText("No available deliveries.\n\nDeliveries will appear here when available.");
    }
    
    public void toggleAvailability() {
        isAvailable = !isAvailable;
        if (isAvailable) {
            toggleAvailabilityButton.setText("Go Offline");
            toggleAvailabilityButton.setBackground(Color.RED);
            JOptionPane.showMessageDialog(
                this,
                "You are now available for deliveries!",
                "Status Changed",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            toggleAvailabilityButton.setText("Go Online");
            toggleAvailabilityButton.setBackground(new Color(0, 150, 0));
            JOptionPane.showMessageDialog(
                this,
                "You are now offline.",
                "Status Changed",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    public JButton getRefreshButton() {
        return refreshButton;
    }
    
    public JButton getToggleAvailabilityButton() {
        return toggleAvailabilityButton;
    }
}
