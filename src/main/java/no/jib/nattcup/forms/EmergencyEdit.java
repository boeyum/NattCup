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

public class EmergencyEdit extends JFrame {
    private TempHandler TH = new TempHandler();
    private String name = new String();
    private int minu = 0;
    private int maal = 0;

    public void showEditDialog(JFrame hovedRamme) {
        // Create the JDialog
        JDialog selectDialog = new JDialog(hovedRamme, "EMERGENCY EDIT", true); // 'true' for modal dialog
        selectDialog.setSize(400, 350);
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
        name = base[0];
        minu = Integer.parseInt(base[1]);
        maal = Integer.parseInt(base[2]);

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
        p.add(new JLabel(txt.getTeamName()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.weightx = 0.6;
        JTextField navn = new JTextField();
        p.add(navn,gbc);
        navn.setText(name);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(5, 10, 5, 10);
        p.add(new JLabel(txt.getSpiltTid()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.weightx = 0.6;
        JTextField min = new JTextField();
        p.add(min,gbc);
        min.setText(String.format("%d", minu));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(5, 10, 5, 10);
        p.add(new JLabel(txt.getPlussMaal()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.weightx = 0.6;
        JTextField mal = new JTextField();
        p.add(mal,gbc);
        mal.setText(String.format("%d", maal));

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.weightx = 0.6;
        JButton bring = new JButton(txt.getNodSave());
        p.add(bring,gbc);
        bring.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String rec = String.format("%s;%d;%d;", navn.getText(), Integer.parseInt(min.getText()), Integer.parseInt(mal.getText()));
                try {
                    TH.save(rec);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.weightx = 0.6;
        JButton slut = new JButton(txt.getNodCancel());
        p.add(slut,gbc);
        slut.addActionListener(new ActionListener() {
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
