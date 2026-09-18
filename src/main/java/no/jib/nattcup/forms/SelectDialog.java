package no.jib.nattcup.forms;



import no.jib.nattcup.utillities.TempHandler;
import no.jib.nattcup.utillities.TextHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SelectDialog extends JFrame {
    private TempHandler TH = new TempHandler();
    private ArrayList<String> lagnavn = new ArrayList<>();

    public void showSelectDialog(JFrame hovedRamme) {
        // Create the JDialog
        JDialog selectDialog = new JDialog(hovedRamme, "SELECT TEAM", true); // 'true' for modal dialog
        selectDialog.setSize(400, 170);
        selectDialog.setLocationRelativeTo(hovedRamme); // Center relative to the parent frame

        TextHandler txt;
        try {
            txt = new TextHandler();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Language support: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
             return;
        }
        String rec;
        try {
            rec = TH.fetch();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String [] base = rec.split(";");
        for (int i = 0; i < base.length; i++) {
            lagnavn.add(base[i]);
        }

        JPanel p = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        p.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(5, 10, 5, 10);
        p.add(new JLabel(txt.getValg()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.weightx = 0.6;
        JComboBox navn = new JComboBox(lagnavn.toArray());
        p.add(navn,gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.weightx = 0.1;
        JButton create = new JButton("SELECT");
        p.add(create,gbc);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TH.save(navn.getSelectedItem().toString());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                selectDialog.setVisible(true);
                selectDialog.dispose();
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.weightx = 0.1;
        JButton slutt = new JButton("CANCEL");
        p.add(slutt,gbc);
        slutt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectDialog.setVisible(true);
                selectDialog.dispose();
            }
        });

        // Add the JPanel to the JDialog's content pane
        selectDialog.getContentPane().add(p, BorderLayout.CENTER);

        // Make the JDialog visible
        selectDialog.setVisible(true);
    }
}
