package morsel.view;

import morsel.model.actors.Actor;

import javax.swing.*;
import java.awt.*;

/**
 * Profile panel for viewing and editing user profile information.
 */
public class ProfilePanel extends JPanel {
    
    private final Actor actor;
    private final Runnable onBack;
    private final Runnable onLogout;
    
    private JTextField usernameField;
    private JComboBox<String> roleField;
    private JTextField uuidField;
    
    public ProfilePanel(Actor actor, Runnable onBack, Runnable onLogout) {
        this.actor = actor;
        this.onBack = onBack;
        this.onLogout = onLogout;
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        createHeader();
        createContent();
    }
    
    private void createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        JLabel titleLabel = new JLabel("Profile Settings");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> onBack.run());
        actionPanel.add(backButton);
        
        JButton logoutButton = new JButton("Logout");
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
        actionPanel.add(logoutButton);
        
        headerPanel.add(actionPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createContent() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        int row = 0;
        
        // Username
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        usernameField.setEditable(false);
        usernameField.setText(actor.getRole().toString().toLowerCase() + "_" + actor.getUuid());
        contentPanel.add(usernameField, gbc);
        row++;
        
        // Role
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPanel.add(new JLabel("Role:"), gbc);
        
        gbc.gridx = 1;
        String[] roles = {"ADMIN", "CUSTOMER", "MERCHANT", "RIDER"};
        roleField = new JComboBox<>(roles);
        roleField.setSelectedItem(actor.getRole().toString());
        roleField.setEnabled(false);
        contentPanel.add(roleField, gbc);
        row++;
        
        // UUID
        gbc.gridx = 0;
        gbc.gridy = row;
        contentPanel.add(new JLabel("User ID:"), gbc);
        
        gbc.gridx = 1;
        uuidField = new JTextField(20);
        uuidField.setEditable(false);
        uuidField.setText(String.valueOf(actor.getUuid()));
        contentPanel.add(uuidField, gbc);
        row++;
        
        // Separator
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        contentPanel.add(new JSeparator(), gbc);
        row++;
        
        gbc.gridwidth = 1;
        
        // Stats based on role
        if (actor.getRole() == Actor.Role.CUSTOMER) {
            gbc.gridx = 0;
            gbc.gridy = row;
            contentPanel.add(new JLabel("Total Orders:"), gbc);
            
            gbc.gridx = 1;
            contentPanel.add(new JLabel("0"), gbc);
            row++;
        } else if (actor.getRole() == Actor.Role.MERCHANT) {
            gbc.gridx = 0;
            gbc.gridy = row;
            contentPanel.add(new JLabel("Restaurant Name:"), gbc);
            
            gbc.gridx = 1;
            contentPanel.add(new JLabel("My Restaurant"), gbc);
            row++;
            
            gbc.gridx = 0;
            gbc.gridy = row;
            contentPanel.add(new JLabel("Menu Items:"), gbc);
            
            gbc.gridx = 1;
            contentPanel.add(new JLabel("0"), gbc);
            row++;
        } else if (actor.getRole() == Actor.Role.RIDER) {
            gbc.gridx = 0;
            gbc.gridy = row;
            contentPanel.add(new JLabel("Deliveries Completed:"), gbc);
            
            gbc.gridx = 1;
            contentPanel.add(new JLabel("0"), gbc);
            row++;
        }
        
        // Action buttons
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton editProfileButton = new JButton("Edit Profile");
        JButton changePasswordButton = new JButton("Change Password");
        
        editProfileButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                this,
                "Edit profile functionality will be implemented.",
                "Edit Profile",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        
        changePasswordButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                this,
                "Change password functionality will be implemented.",
                "Change Password",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        
        buttonPanel.add(editProfileButton);
        buttonPanel.add(changePasswordButton);
        contentPanel.add(buttonPanel, gbc);
        
        add(contentPanel, BorderLayout.CENTER);
    }
}
