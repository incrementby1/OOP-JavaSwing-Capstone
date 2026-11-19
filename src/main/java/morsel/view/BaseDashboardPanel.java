package morsel.view;

import morsel.model.actors.Actor;

import javax.swing.*;
import java.awt.*;

/**
 * Base dashboard panel with common navigation sidebar.
 * All dashboard panels should extend this for consistent navigation.
 */
public abstract class BaseDashboardPanel extends JPanel {
    
    protected final Actor actor;
    protected final Runnable onLogout;
    protected final Runnable onProfile;
    
    private JPanel navigationPanel;
    private JPanel contentPanel;
    
    public BaseDashboardPanel(Actor actor, Runnable onLogout, Runnable onProfile) {
        this.actor = actor;
        this.onLogout = onLogout;
        this.onProfile = onProfile;
        
        setLayout(new BorderLayout());
        createNavigationPanel();
        createContentPanel();
    }
    
    private void createNavigationPanel() {
        navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        navigationPanel.setPreferredSize(new Dimension(200, 0));
        navigationPanel.setBackground(new Color(240, 240, 240));
        
        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome, " + getActorDisplayName());
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        navigationPanel.add(welcomeLabel);
        navigationPanel.add(Box.createVerticalStrut(30));
        
        // Role label
        JLabel roleLabel = new JLabel("Role: " + actor.getRole().toString());
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        navigationPanel.add(roleLabel);
        navigationPanel.add(Box.createVerticalStrut(40));
        
        // Dashboard button
        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        dashboardButton.setMaximumSize(new Dimension(180, 40));
        dashboardButton.addActionListener(e -> onDashboard());
        navigationPanel.add(dashboardButton);
        navigationPanel.add(Box.createVerticalStrut(10));
        
        // Profile button
        JButton profileButton = new JButton("Profile");
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileButton.setMaximumSize(new Dimension(180, 40));
        profileButton.addActionListener(e -> onProfile.run());
        navigationPanel.add(profileButton);
        navigationPanel.add(Box.createVerticalStrut(10));
        
        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setMaximumSize(new Dimension(180, 40));
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                onLogout.run();
            }
        });
        navigationPanel.add(logoutButton);
        
        add(navigationPanel, BorderLayout.WEST);
    }
    
    private void createContentPanel() {
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel titleLabel = new JLabel(getDashboardTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(createMainContent(), BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Override to provide dashboard-specific main content.
     */
    protected abstract JPanel createMainContent();
    
    /**
     * Override to provide dashboard title.
     */
    protected abstract String getDashboardTitle();
    
    /**
     * Get display name for the actor.
     */
    protected String getActorDisplayName() {
        return "User";
    }
    
    /**
     * Called when dashboard button is clicked.
     */
    protected abstract void onDashboard();
}
