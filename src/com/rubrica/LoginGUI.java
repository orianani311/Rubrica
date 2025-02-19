package com.rubrica;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Rubrica rubrica;
    private Font ubuntuFont = new Font("Ubuntu", Font.BOLD, 17); // Thicker text

    public LoginGUI() {
        rubrica = new Rubrica();

        setTitle("Login - Rubrica Telefonica");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set lilac background color
        getContentPane().setBackground(new Color(216, 191, 216)); // Lilac

        // Main Panel with Padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(216, 191, 216)); // Lilac
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Smaller spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        // Username Label
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(ubuntuFont);
        usernameLabel.setForeground(new Color(75, 0, 130)); // Dark purple text
        mainPanel.add(usernameLabel, gbc);

        // Username Input
        gbc.gridy = 1;
        usernameField = new JTextField(15);
        usernameField.setFont(ubuntuFont);
        usernameField.setBackground(new Color(230, 230, 230)); // Light grey background
        usernameField.setForeground(new Color(75, 0, 130)); // Dark purple text
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(140, 104, 140), 2));
        mainPanel.add(usernameField, gbc);

        // Password Label
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(ubuntuFont);
        passwordLabel.setForeground(new Color(75, 0, 130)); // Dark purple text
        mainPanel.add(passwordLabel, gbc);

        // Password Input
        gbc.gridy = 3;
        passwordField = new JPasswordField(15);
        passwordField.setFont(ubuntuFont);
        passwordField.setBackground(new Color(230, 230, 230)); // Light grey background
        passwordField.setForeground(new Color(75, 0, 130)); // Dark purple text
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(140, 104, 140), 2));
        mainPanel.add(passwordField, gbc);

        // Spacer to push the button to the bottom
        gbc.gridy = 4;
        gbc.weighty = 1;
        mainPanel.add(Box.createVerticalGlue(), gbc);

        // Login Button at the bottom
        gbc.gridy = 5;
        gbc.weighty = 0;
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Ubuntu", Font.BOLD, 17));
        loginButton.setBackground(new Color(200, 162, 200)); // Light lilac button
        loginButton.setForeground(new Color(75, 0, 130)); // Dark purple text
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(140, 104, 140), 2));
        loginButton.setOpaque(true);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginButton.addActionListener(e -> verificaLogin());
        mainPanel.add(loginButton, gbc);

        // Add ActionListener to enter key
        usernameField.addActionListener(e -> verificaLogin());
        passwordField.addActionListener(e -> verificaLogin());

        add(mainPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void verificaLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Errore: Inserisci username e password.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (rubrica.verificaCredenziali(username, password)) {
            this.dispose();
            SwingUtilities.invokeLater(() -> new RubricaGUI().setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this, "Credenziali errate!", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}