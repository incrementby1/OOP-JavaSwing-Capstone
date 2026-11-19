package morsel.controller.states;

import morsel.controller.Client;
import morsel.view.ProfilePanel;

import javax.swing.*;

/**
 * Profile state for viewing and editing user profile.
 */
public class ClientProfileState extends ClientState {
    
    private final ProfilePanel profilePanel;
    private final Client client;
    
    public ClientProfileState(Client client) {
        super(client, Client.State.PROFILE);
        this.client = client;
        
        this.profilePanel = new ProfilePanel(
            client.getContext().actor,
            this::goBack,
            this::logout
        );
    }
    
    private void goBack() {
        // Go back to the previous state (the dashboard)
        client.backtrack(1);
    }
    
    private void logout() {
        client.backtrack(-1); // Go back to authentication
    }
    
    @Override
    public JPanel getView() {
        return profilePanel;
    }
    
    @Override
    public void onEntry() {
        // Refresh profile data if needed
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
        // Refresh profile data if needed
    }
}
