package com.rubrica;

import javax.swing.*;
import java.awt.*;

public class EditPersonaGUI extends JDialog {
    private RubricaGUI parent;
    private Persona persona;

    public EditPersonaGUI(RubricaGUI parent, Persona persona) {
        super(parent, "Modifica Persona", true);
        this.parent = parent;
        this.persona = persona;

        // Open the unified PersonaFormGUI for editing
        new PersonaFormGUI(parent, persona, false);
    }
}
