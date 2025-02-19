package com.rubrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RubricaGUI extends JFrame {
    private Rubrica rubrica;
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField searchField;
    private Font titleFont = new Font("Ubuntu", Font.BOLD, 17);
    private Font textFont = new Font("Ubuntu", Font.PLAIN, 17);

    public RubricaGUI() {
        rubrica = new Rubrica();

        setTitle("Rubrica Telefonica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(216, 191, 216));

        // Toolbar
        JToolBar toolbar = new JToolBar() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, 35);
            }
        };
        toolbar.setBackground(new Color(200, 162, 200));
        toolbar.setForeground(new Color(75, 0, 130));
        toolbar.setFloatable(false);

        JButton addButton = createStyledButton("Nuovo", new Color(75, 0, 130));
        JButton editButton = createStyledButton("Modifica", new Color(75, 0, 130));
        JButton deleteButton = createStyledButton("Elimina", new Color(75, 0, 130));

        addButton.addActionListener(e -> new PersonaFormGUI(this, null, true));
        editButton.addActionListener(e -> modificaPersona());
        deleteButton.addActionListener(e -> eliminaPersona());

        toolbar.add(addButton);
        toolbar.add(Box.createRigidArea(new Dimension(5, 10)));
        toolbar.add(editButton);
        toolbar.add(Box.createRigidArea(new Dimension(5, 10)));
        toolbar.add(deleteButton);
        toolbar.add(Box.createRigidArea(new Dimension(5, 10)));

        // Search Panel with spacing
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel(" Cerca: ");
        searchLabel.setForeground(new Color(75, 0, 130));
        searchLabel.setFont(titleFont);

        searchField = new JTextField(20);
        searchField.setFont(textFont);
        searchField.setBackground(new Color(230, 230, 230));
        searchField.setForeground(new Color(75, 0, 130));
        searchField.setBorder(BorderFactory.createLineBorder(new Color(140, 104, 140), 2));
        searchField.setToolTipText("Cerca per Nome o Cognome...");
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTable(searchField.getText().trim());
            }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(searchPanel);

        add(toolbar, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(216, 191, 216));
        mainPanel.setForeground(new Color(75, 0, 130));

        // Table Setup
        String[] columnNames = {"Nome", "Cognome", "Telefono"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(textFont);
        table.getTableHeader().setFont(titleFont);
        table.getTableHeader().setForeground(new Color(75, 0, 130));
        table.setRowHeight(25);
        table.setIntercellSpacing(new Dimension(8, 3));
        table.setForeground(new Color(75, 0, 130));


        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        updateTable();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(titleFont);
        button.setBackground(new Color(200, 162, 200));
        button.setForeground(new Color(75, 0, 130));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(140, 104, 140), 2));
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void modificaPersona() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Persona oldPersona = rubrica.getContatti().get(table.convertRowIndexToModel(selectedRow));
            new PersonaFormGUI(this, oldPersona, false);
        } else {
            JOptionPane.showMessageDialog(this, "Seleziona una persona da modificare.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminaPersona() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            Persona p = rubrica.getContatti().get(modelRow);
            rubrica.eliminaPersona(p);
            rubrica.caricaDaCartella();
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Seleziona una persona da eliminare.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addOrUpdatePersona(Persona persona, Persona oldPersona, boolean isNew) {
        if (isNew) {
            rubrica.aggiungiPersona(persona);
        } else {
            rubrica.aggiornaPersona(oldPersona, persona);
        }
        updateTable();
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Persona p : rubrica.getContatti()) {
            tableModel.addRow(new Object[]{p.getNome(), p.getCognome(), p.getTelefono()});
        }
        sorter.sort();
    }

    private void filterTable(String query) {
        if (query.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 0, 1));
        }
    }
}
