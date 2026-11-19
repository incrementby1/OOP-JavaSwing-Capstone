package morsel.view;

import javax.swing.*;
import java.awt.*;

/**
 * Authentication panel for user login and registration.
 * Supports both login and registration with role selection.
 */
public class AuthPanel extends JPanel {
    
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    
    // Login components
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;
    private JButton showRegisterButton;
    
    // Register components
    private JTextField registerUsernameField;
    private JPasswordField registerPasswordField;
    private JPasswordField registerConfirmPasswordField;
    private JComboBox<String> registerRoleComboBox;
    private JButton registerButton;
    private JButton showLoginButton;
    
    public AuthPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        createLoginPanel();
        createRegisterPanel();
        
        cardPanel.add(createLoginPanel(), "LOGIN");
        cardPanel.add(createRegisterPanel(), "REGISTER");
        
        add(cardPanel, BorderLayout.CENTER);
        
        // Start with login panel
        cardLayout.show(cardPanel, "LOGIN");
    }
    
    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        JLabel titleLabel = new JLabel("Login to Morsel");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);
        
        // Username
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        loginUsernameField = new JTextField(20);
        loginPanel.add(loginUsernameField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        loginPasswordField = new JPasswordField(20);
        loginPanel.add(loginPasswordField, gbc);
        
        // Login button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(150, 40));
        loginPanel.add(loginButton, gbc);
        
        // Switch to register
        gbc.gridy = 4;
        showRegisterButton = new JButton("Don't have an account? Register");
        showRegisterButton.setContentAreaFilled(false);
        showRegisterButton.setBorderPainted(false);
        showRegisterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginPanel.add(showRegisterButton, gbc);
        
        return loginPanel;
    }
    
    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        registerPanel.add(titleLabel, gbc);
        
        // Username
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        registerPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        registerUsernameField = new JTextField(20);
        registerPanel.add(registerUsernameField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        registerPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        registerPasswordField = new JPasswordField(20);
        registerPanel.add(registerPasswordField, gbc);
        
        // Confirm Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        registerPanel.add(new JLabel("Confirm Password:"), gbc);
        
        gbc.gridx = 1;
        registerConfirmPasswordField = new JPasswordField(20);
        registerPanel.add(registerConfirmPasswordField, gbc);
        
        // Role
        gbc.gridx = 0;
        gbc.gridy = 4;
        registerPanel.add(new JLabel("Role:"), gbc);
        
        gbc.gridx = 1;
        String[] roles = {"CUSTOMER", "MERCHANT", "RIDER"};
        registerRoleComboBox = new JComboBox<>(roles);
        registerPanel.add(registerRoleComboBox, gbc);
        
        // Register button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(150, 40));
        registerPanel.add(registerButton, gbc);
        
        // Switch to login
        gbc.gridy = 6;
        showLoginButton = new JButton("Already have an account? Login");
        showLoginButton.setContentAreaFilled(false);
        showLoginButton.setBorderPainted(false);
        showLoginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerPanel.add(showLoginButton, gbc);
        
        return registerPanel;
    }
    
    // Getters for action listeners
    public JButton getLoginButton() {
        return loginButton;
    }
    
    public JButton getRegisterButton() {
        return registerButton;
    }
    
    public JButton getShowRegisterButton() {
        return showRegisterButton;
    }
    
    public JButton getShowLoginButton() {
        return showLoginButton;
    }
    
    // Getters for field values
    public String getLoginUsername() {
        return loginUsernameField.getText().trim();
    }
    
    public char[] getLoginPassword() {
        return loginPasswordField.getPassword();
    }
    
    public String getRegisterUsername() {
        return registerUsernameField.getText().trim();
    }
    
    public char[] getRegisterPassword() {
        return registerPasswordField.getPassword();
    }
    
    public char[] getRegisterConfirmPassword() {
        return registerConfirmPasswordField.getPassword();
    }
    
    public String getSelectedRole() {
        return (String) registerRoleComboBox.getSelectedItem();
    }
    
    // Navigation
    public void showLoginPanel() {
        cardLayout.show(cardPanel, "LOGIN");
    }
    
    public void showRegisterPanel() {
        cardLayout.show(cardPanel, "REGISTER");
    }
    
    // Clear fields
    public void clearLoginFields() {
        loginUsernameField.setText("");
        loginPasswordField.setText("");
        loginUsernameField.requestFocus();
    }
    
    public void clearRegisterFields() {
        registerUsernameField.setText("");
        registerPasswordField.setText("");
        registerConfirmPasswordField.setText("");
        registerUsernameField.requestFocus();
    }
}
