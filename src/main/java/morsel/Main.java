package morsel;

import com.formdev.flatlaf.FlatLightLaf;
import morsel.controller.Server;
import morsel.controller.Client;

import javax.swing.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup(); // Must-call.

        // Client-Server model separation.
        Server server = new Server();
        Client client = new Client(server);
        // Dispatch to EDT.
        SwingUtilities.invokeLater(() -> {
            try {
                server.run();
            } catch (IOException | NoSuchAlgorithmException e){
                System.err.printf("%s", e);
            }
        });
        SwingUtilities.invokeLater(client::run);

    }
}