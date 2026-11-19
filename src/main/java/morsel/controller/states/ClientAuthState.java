package morsel.controller.states;

import morsel.controller.Client;
import morsel.model.ClientContext;
import morsel.model.actors.Actor;
import morsel.model.actors.Customer;
import morsel.model.actors.Merchant;
import morsel.model.actors.Rider;
import morsel.model.data.UserEntry;
import morsel.model.exceptions.InvalidCredentialsException;
import morsel.model.exceptions.UnlikeValidationPasswordException;
import morsel.model.exceptions.UserAlreadyExistsException;
import morsel.view.AuthPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Authentication state for user login and registration.
 * Handles authentication flow with JOptionPane dialogs for feedback.
 */
public class ClientAuthState extends ClientState {
    
    private final AuthPanel authPanel;
    private final Client client;
    
    public ClientAuthState(Client client) {
        super(client, Client.State.AUTHENTICATION);
        this.client = client;
        this.authPanel = new AuthPanel();
        
        setupLoginListener();
        setupRegisterListener();
        setupNavigationListeners();
    }
    
    private void setupLoginListener() {
        authPanel.getLoginButton().addActionListener(e -> {
            String username = authPanel.getLoginUsername();
            char[] password = authPanel.getLoginPassword();
            
            // Validate input
            if (username.isEmpty() || password.length == 0) {
                JOptionPane.showMessageDialog(
                    authPanel,
                    "Please enter both username and password.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            try {
                String passwordStr = new String(password);
                UserEntry userEntry = client.server.validateCredentials(username, passwordStr);
                
                // Create appropriate actor based on role
                Actor actor = switch (userEntry.role) {
                    case ADMIN -> new Admin(userEntry.uuid);
                    case CUSTOMER -> new Customer(userEntry.uuid);
                    case MERCHANT -> new Merchant(userEntry.uuid);
                    case RIDER -> new Rider(userEntry.uuid);
                };
                
                // Set context
                ClientContext context = client.getContext();
                context.actor = actor;
                
                // Navigate to appropriate dashboard
                navigateToDashboard(userEntry.role);
                
                authPanel.clearLoginFields();
                
            } catch (InvalidCredentialsException ex) {
                JOptionPane.showMessageDialog(
                    authPanel,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
                );
                authPanel.clearLoginFields();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                    authPanel,
                    "Failed to validate credentials: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (NoSuchAlgorithmException ex) {
                JOptionPane.showMessageDialog(
                    authPanel,
                    "Security error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    authPanel,
                    "An unexpected error occurred: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
    
    private void setupRegisterListener() {
        authPanel.getRegisterButton().addActionListener(e -> {
            String username = authPanel.getRegisterUsername();
            char[] password = authPanel.getRegisterPassword();
            char[] confirmPassword = authPanel.getRegisterConfirmPassword();
            String roleStr = authPanel.getSelectedRole();
            
            // Validate input
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(
                    authPanel,
                    "Please enter a username.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            if (password.length == 0) {
                JOptionPane.showMessageDialog(
                    authPanel,
                    "Please enter a password.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            if (!new String(password).equals(new String(confirmPassword))) {
                JOptionPane.showMessageDialog(
                    authPanel,
                    "Passwords do not match.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            // Parse role
            Actor.Role role;
            try {
                role = Actor.Role.valueOf(roleStr);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                    authPanel,
                    "Invalid role selected.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            try {
                String passwordStr = new String(password);
                String confirmPasswordStr = new String(confirmPassword);
                
                client.server.registerCredentials(username, passwordStr, confirmPasswordStr, role);
                
                JOptionPane.showMessageDialog(
                    authPanel,
                    "Registration successful! Please login.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                authPanel.showLoginPanel();
                authPanel.clearRegisterFields();
                authPanel.clearLoginFields();
                
            } catch (UnlikeValidationPasswordException ex) {
                JOptionPane.showMessageDialog(
                    authPanel,
                    "Passwords do not match.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (UserAlreadyExistsException ex) {
                JOptionPane.showMessageDialog(
                    authPanel,
                    "Username already exists. Please choose another.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(
                    authPanel,
                    "Registration failed: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (NoSuchAlgorithmException ex) {
                JOptionPane.showMessageDialog(
                    authPanel,
                    "Security error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    authPanel,
                    "An unexpected error occurred: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
    
    private void setupNavigationListeners() {
        authPanel.getShowRegisterButton().addActionListener(e -> {
            authPanel.showRegisterPanel();
            authPanel.clearRegisterFields();
        });
        
        authPanel.getShowLoginButton().addActionListener(e -> {
            authPanel.showLoginPanel();
            authPanel.clearLoginFields();
        });
    }
    
    private void navigateToDashboard(Actor.Role role) {
        switch (role) {
            case ADMIN -> client.transitionTo(new ClientAdminDashboardState(client));
            case CUSTOMER -> client.transitionTo(new ClientCustomerHomeState(client));
            case MERCHANT -> client.transitionTo(new ClientMerchantDashboardState(client));
            case RIDER -> client.transitionTo(new ClientRiderDashboardState(client));
        }
    }
    
    @Override
    public JPanel getView() {
        return authPanel;
    }
    
    @Override
    public void onEntry() {
        authPanel.showLoginPanel();
        authPanel.clearLoginFields();
        authPanel.requestFocus();
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
        authPanel.showLoginPanel();
        authPanel.clearLoginFields();
        authPanel.requestFocus();
    }
}
