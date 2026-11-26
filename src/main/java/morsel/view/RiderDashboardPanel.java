package morsel.view;

import morsel.model.actors.Rider;
import morsel.controller.Server;

import java.io.IOException;
import javax.swing.*;
import java.awt.*;

/**
 * Rider dashboard panel for delivery management.
 */
public class RiderDashboardPanel extends BaseDashboardPanel {
    
    private JTextArea deliveryTextArea;
    private JButton refreshButton;
    private JButton toggleAvailabilityButton;
    private JLabel availabilityLabel;
    private boolean isAvailable;
    private Server server;
    private Rider rider;

    public RiderDashboardPanel(
            Rider rider,
            Runnable onLogout,
            Runnable onProfile,
            Server server) {

        super(rider, onLogout, onProfile);

        this.rider = rider;
        this.server = server;
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
        availabilityLabel = new JLabel("Status: Unavailable");
        availabilityLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        availabilityLabel.setForeground(Color.RED);
        availabilityPanel.add(availabilityLabel);
        
        toggleAvailabilityButton = new JButton("Go Online");
        toggleAvailabilityButton.setBackground(new Color(0, 150, 0));
        toggleAvailabilityButton.setForeground(Color.WHITE);

        toggleAvailabilityButton.addActionListener(
                e -> toggleAvailability()
        );

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

        try {
            loadAvailability();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load availability: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public void refreshDeliveries() {
        deliveryTextArea.setText("Loading deliveries...");
        // In a real implementation, this would fetch deliveries from the server
        deliveryTextArea.setText("No available deliveries.\n\nDeliveries will appear here when available.");
    }


    /**
     * Toggles the rider's availability status between online
     * and offline and updates the server accordingly.
     *
     * Handles IO errors when updating rider availability.
     */
    public void toggleAvailability() {
        boolean newAvailability = !isAvailable;

        try {
            server.updateRiderAvailability(rider, newAvailability);

            System.out.println("Requested availability: " + newAvailability);
            System.out.println("Active riders in server: "
                    + server.getActiveRiders().size());

            System.out.println("Rider found in server: "
                    + server.getActiveRiders().stream()
                    .anyMatch(r -> r.getUuid() == rider.getUuid()));

            isAvailable = newAvailability;

            if (isAvailable) {
                availabilityLabel.setText("Status: Available");
                availabilityLabel.setForeground(new Color(0, 150, 0));

                toggleAvailabilityButton.setText("Go Offline");
                toggleAvailabilityButton.setBackground(Color.RED);

                JOptionPane.showMessageDialog(
                        this,
                        "You are now available for deliveries!",
                        "Status Changed",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {
                availabilityLabel.setText("Status: Unavailable");
                availabilityLabel.setForeground(Color.RED);

                toggleAvailabilityButton.setText("Go Online");
                toggleAvailabilityButton.setBackground(new Color(0, 150, 0));

                JOptionPane.showMessageDialog(
                        this,
                        "You are now offline.",
                        "Status Changed",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update availability: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Retrieves the rider's current availability status from the server.
     *
     * @throws IOException When retrieving active riders fails.
     */
    private void loadAvailability() throws IOException {
        isAvailable = server.getActiveRiders().stream()
                .anyMatch(activeRider -> activeRider.getUuid() == rider.getUuid());

        if (isAvailable) {
            availabilityLabel.setText("Status: Available");
            availabilityLabel.setForeground(new Color(0, 150, 0));
            toggleAvailabilityButton.setText("Go Offline");
            toggleAvailabilityButton.setBackground(Color.RED);
        } else {
            availabilityLabel.setText("Status: Unavailable");
            availabilityLabel.setForeground(Color.RED);
            toggleAvailabilityButton.setText("Go Online");
            toggleAvailabilityButton.setBackground(new Color(0, 150, 0));
        }
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    public JButton getRefreshButton() {
        return refreshButton;
    }
    
    public JButton getToggleAvailabilityButton() {
        return toggleAvailabilityButton;
    }
}
