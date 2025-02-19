package com.rubrica;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Rubrica {
    private Vector<Persona> contatti;
    private Map<String, Utente> utenti;
    private static final String DIRECTORY_NAME = "informazioni";
    private static final String USERS_FILE = "utenti.txt";

    public Rubrica() {
        contatti = new Vector<>();
        utenti = new HashMap<>();
        caricaDaCartella();
        caricaUtenti();
    }

    public Vector<Persona> getContatti() {
        return contatti;
    }

    public void eliminaPersona(Persona p) {
        String fileName = generateFileName(p);
        File file = new File(DIRECTORY_NAME, fileName);

        if (file.exists() && file.delete()) {
            contatti.remove(p);
        }
        caricaDaCartella();
    }

    public void aggiornaPersona(Persona oldPersona, Persona newPersona) {
        String oldFileName = generateFileName(oldPersona);
        File oldFile = new File(DIRECTORY_NAME, oldFileName);

        if (oldFile.exists()) {
            oldFile.delete();
        }

        contatti.remove(oldPersona);
        contatti.add(newPersona);
        salvaSuFile(newPersona);
        caricaDaCartella();
    }

    public void aggiungiPersona(Persona p) {
        for (Persona existing : contatti) {
            if (existing.getNome().equalsIgnoreCase(p.getNome()) && existing.getCognome().equalsIgnoreCase(p.getCognome())) {
                return; // Avoid duplicate contacts
            }
        }
        contatti.add(p);
        salvaSuFile(p);
    }

    public boolean verificaCredenziali(String username, String password) {
        Utente utente = utenti.get(username);
        return utente != null && utente.checkPassword(password);
    }

    private void salvaSuFile(Persona p) {
        File directory = new File(DIRECTORY_NAME);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String fileName = generateFileName(p);
        File file = new File(directory, fileName);

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) {
            writer.println(p.getNome());
            writer.println(p.getCognome());
            writer.println(p.getIndirizzo());
            writer.println(p.getTelefono());
            writer.println(p.getEta());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void caricaDaCartella() {
        contatti.clear();
        File directory = new File(DIRECTORY_NAME);
        if (!directory.exists()) {
            directory.mkdir();
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String nome = reader.readLine();
                String cognome = reader.readLine();
                String indirizzo = reader.readLine();
                String telefono = reader.readLine();
                int eta = Integer.parseInt(reader.readLine());

                contatti.add(new Persona(nome, cognome, indirizzo, telefono, eta));
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void caricaUtenti() {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("admin;password");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    utenti.put(parts[0], new Utente(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateFileName(Persona p) {
        return (p.getNome() + "-" + p.getCognome() + ".txt").replaceAll("[^a-zA-Z0-9.-]", "_");
    }
}
