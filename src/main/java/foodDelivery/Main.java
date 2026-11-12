package foodDelivery;

import com.formdev.flatlaf.FlatLightLaf;
import foodDelivery.controller.Server;
import foodDelivery.controller.Client;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Application Startup.");

        JFrame.setDefaultLookAndFeelDecorated(true);
        FlatLightLaf.setup();

        Server server = new Server();
        Client client = new Client(server);

        // Started on Event Dispatch Tree (EDT).
        SwingUtilities.invokeLater(server::run);
        SwingUtilities.invokeLater(client::run);
    }
}