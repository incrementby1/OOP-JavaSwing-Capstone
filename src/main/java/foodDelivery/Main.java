package foodDelivery;

import com.formdev.flatlaf.FlatLightLaf;
import foodDelivery.controller.Client;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Application Startup.");

        JFrame.setDefaultLookAndFeelDecorated(true);
        FlatLightLaf.setup();

        // Started on Event Dispatch Tree (EDT).
        SwingUtilities.invokeLater(() -> {
            Client client = new Client();
            client.run();
        });
    }
}