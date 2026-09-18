package no.jib.nattcup.forms;



import no.jib.nattcup.utillities.TempHandler;
import no.jib.nattcup.utillities.TextHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TimeForm extends JFrame {
    private TempHandler TH = new TempHandler();

    public void showTimeDialog(JFrame hovedRamme) {
        // Create the JDialog
        JDialog selectDialog = new JDialog(hovedRamme, "REGISTRER DATA", true); // 'true' for modal dialog
        selectDialog.setSize(400, 200);
        selectDialog.setLocationRelativeTo(hovedRamme); // Center relative to the parent frame

        TextHandler txt;
        try {
            txt = new TextHandler();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Language support: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
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
        p.add(new JLabel(txt.getTimeTurnament()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.weightx = 0.6;
        JTextField varig = new JTextField();
        p.add(varig,gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(5, 10, 5, 10);
        p.add(new JLabel(txt.getTimeMatch()),gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.weightx = 0.6;
        JTextField spt = new JTextField();
        p.add(spt,gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.weightx = 0.1;
        JButton create = new JButton(txt.getTimeSave());
        p.add(create,gbc);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TH.save(String.format("%s;%s;",varig.getText(),spt.getText()));
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                selectDialog.setVisible(true);
                selectDialog.dispose();
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.weightx = 0.1;
        JButton slutt = new JButton(txt.getTimeCancel());
        p.add(slutt,gbc);
        slutt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TH.save("CANCEL");
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
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
