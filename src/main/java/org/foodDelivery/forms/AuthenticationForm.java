package org.foodDelivery.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthenticationForm extends JFrame {

    private JPanel pnlMain;
    private JLabel lblTitle;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblStatus;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox cbShowPassword;
    private JButton btnLogin;
    private JButton btnClear;

    // Placeholder credentials. Replace with a real authentication check.
    private final String validUsername = "admin";
    private final String validPassword = "1234";

    private AuthenticationForm() {
        setTitle("Authentication");
        buildForm();

        cbShowPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbShowPassword.isSelected()) {
                    txtPassword.setEchoChar((char) 0);
                } else {
                    txtPassword.setEchoChar('\u2022');
                }
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtUsername.setText("");
                txtPassword.setText("");
                lblStatus.setText(" ");
                txtUsername.requestFocus();
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    lblStatus.setText("Please enter your username and password.");
                    return;
                }

                if (username.equals(validUsername) && password.equals(validPassword)) {
                    JOptionPane.showMessageDialog(null, "Welcome, " + username + "!");
                    // Next step: transition the Client to State.DASHBOARD here.
                } else {
                    lblStatus.setText("Invalid username or password.");
                    txtPassword.setText("");
                }
            }
        });

        getRootPane().setDefaultButton(btnLogin);
    }

    // Builds the components by hand so the form runs without an IntelliJ .form file.
    private void buildForm() {
        pnlMain = new JPanel(new GridBagLayout());
        pnlMain.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        lblTitle = new JLabel("Sign In", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 22f));
        lblUsername = new JLabel("Username:");
        lblPassword = new JLabel("Password:");
        lblStatus = new JLabel(" ", SwingConstants.CENTER);
        lblStatus.setForeground(Color.RED);

        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        cbShowPassword = new JCheckBox("Show password");
        btnLogin = new JButton("Login");
        btnClear = new JButton("Clear");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        pnlMain.add(lblTitle, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1;
        pnlMain.add(lblUsername, gbc);
        gbc.gridx = 1;
        pnlMain.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        pnlMain.add(lblPassword, gbc);
        gbc.gridx = 1;
        pnlMain.add(txtPassword, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        pnlMain.add(cbShowPassword, gbc);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pnlButtons.add(btnLogin);
        pnlButtons.add(btnClear);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        pnlMain.add(pnlButtons, gbc);

        gbc.gridy = 5;
        pnlMain.add(lblStatus, gbc);
    }

    public static void main(String[] args) {
        AuthenticationForm app = new AuthenticationForm();
        app.setContentPane(app.pnlMain);
        app.setSize(400, 330);
        app.setLocationRelativeTo(null);
        app.setDefaultCloseOperation(EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
