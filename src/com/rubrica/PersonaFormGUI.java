package com.rubrica;

import javax.swing.*;
import java.awt.*;

public class PersonaFormGUI extends JDialog {
    private JTextField nomeField, cognomeField, indirizzoField, telefonoField, etaField;
    private Persona persona;
    private RubricaGUI parent;
    private boolean isNew;


    public PersonaFormGUI(RubricaGUI parent, Persona persona, boolean isNew) {
        super(parent, isNew ? "Aggiungi Persona" : "Modifica Persona", true);
        this.parent = parent;
        this.persona = persona;
        this.isNew = isNew;

        setSize(450, 450);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(216, 191, 216));


        // Main Panel using GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(216, 191, 216));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fonts
        Font labelFont = new Font("Ubuntu", Font.BOLD, 17);
        Font fieldFont = new Font("Ubuntu", Font.PLAIN, 17);

        // Row Index Counter
        int row = 0;

        // Nome
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setFont(labelFont);
        nomeLabel.setForeground(new Color(75, 0, 130)); // Set text color

        mainPanel.add(nomeLabel, gbc);


        gbc.gridx = 1;
        gbc.weightx = 0.7;
        nomeField = new JTextField(persona != null ? persona.getNome() : "");
        nomeField.setForeground(new Color(75, 0, 130));
        nomeField.setFont(fieldFont);
        mainPanel.add(nomeField, gbc);
        row++;

        // Cognome
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        JLabel cognomeLabel = new JLabel("Cognome:");
        cognomeLabel.setFont(labelFont);
        cognomeLabel.setForeground(new Color(75, 0, 130)); // Set text color
        mainPanel.add(cognomeLabel, gbc);


        gbc.gridx = 1;
        gbc.weightx = 0.7;
        cognomeField = new JTextField(persona != null ? persona.getCognome() : "");
        cognomeField.setForeground(new Color(75, 0, 130));
        cognomeField.setFont(fieldFont);
        mainPanel.add(cognomeField, gbc);
        row++;

        // Indirizzo
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;

        JLabel indirizzoLabel = new JLabel("Indirizzo:");
        indirizzoLabel.setFont(labelFont);
        indirizzoLabel.setForeground(new Color(75, 0, 130)); // Set text color

        mainPanel.add(indirizzoLabel, gbc);


        gbc.gridx = 1;
        gbc.weightx = 0.7;
        indirizzoField = new JTextField(persona != null ? persona.getIndirizzo() : "");
        indirizzoField.setForeground(new Color(75, 0, 130));
        indirizzoField.setFont(fieldFont);
        mainPanel.add(indirizzoField, gbc);
        row++;

        // Telefono
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        JLabel telefonoLabel = new JLabel("Telefono:");
        telefonoLabel.setFont(labelFont);
        telefonoLabel.setForeground(new Color(75, 0, 130));
        mainPanel.add(telefonoLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        telefonoField = new JTextField(persona != null ? persona.getTelefono() : "");
        telefonoField.setForeground(new Color(75, 0, 130));
        telefonoField.setFont(fieldFont);
        mainPanel.add(telefonoField, gbc);
        row++;

        // Età
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        JLabel etaLabel = new JLabel("Età:");
        etaLabel.setFont(labelFont);
        etaLabel.setForeground(new Color(75, 0, 130));
        mainPanel.add(etaLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        etaField = new JTextField(persona != null ? String.valueOf(persona.getEta()) : "");
        etaField.setForeground(new Color(75, 0, 130));
        etaField.setFont(fieldFont);
        etaField.setForeground(new Color(75, 0, 130));
        mainPanel.add(etaField, gbc);
        row++;

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(216, 191, 216));
        JButton saveButton = new JButton("Salva");
        JButton cancelButton = new JButton("Annulla");


        saveButton.setFont(new Font("Ubuntu", Font.BOLD, 17));
        saveButton.setForeground(new Color(75, 0, 130));
        saveButton.setBackground(new Color(216, 191, 216));

        cancelButton.setFont(new Font("Ubuntu", Font.BOLD, 17));
        cancelButton.setForeground(new Color(75, 0, 130));
        cancelButton.setBackground(new Color(216, 191, 216));

        saveButton.addActionListener(e -> savePersona());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void savePersona() {
        String nome = nomeField.getText().trim();
        String cognome = cognomeField.getText().trim();
        String indirizzo = indirizzoField.getText().trim();
        String telefono = telefonoField.getText().trim();
        int eta;

        try {
            eta = Integer.parseInt(etaField.getText().trim());
            if (eta <= 0) {
                JOptionPane.showMessageDialog(this, "Errore: L'età deve essere un numero positivo.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Errore: Età non valida. Inserisci un numero intero.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (!telefono.matches("\\d{10}")) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Errore: Il numero di telefono deve contenere esattamente 10 cifre.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Persona updatedPersona = new Persona(nome, cognome, indirizzo, telefono, eta);
        parent.addOrUpdatePersona(updatedPersona, persona, isNew);
        dispose();
    }
}
