package no.jib.nattcup.forms;


import no.jib.nattcup.utillities.TextHandler;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ShowPDF extends JFrame {
    private JScrollPane scrollPane;
    private ArrayList<ImageIcon> ipages = new ArrayList<>();
    private int max = 0;
    private int curr = 0;

    public void showPdfForm(JFrame hovedRamme) {
        JDialog selectDialog = new JDialog(hovedRamme, "User Manuak", true); // 'true' for modal dialog
        selectDialog.setSize(1400, 1000);
        selectDialog.setLocationRelativeTo(hovedRamme); // Center relative to the parent frame

        TextHandler txt;
        try {
            txt = new TextHandler();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Language support: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        layout.rowHeights = new int[] {900, 100};

        try (PDDocument document = Loader.loadPDF(new File("config/userman.pdf"))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            PDPageTree pages = document.getPages();
            max = pages.getCount();
            for (int a = 0; a < max; a++) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(a, 150);
                ImageIcon image = new ImageIcon(bim);
                ipages.add(image);
            }
            JLabel label = new JLabel(ipages.get(0));
            scrollPane = new JScrollPane(label);
            scrollPane.setSize(1390, 900);
            scrollPane.setPreferredSize(new Dimension(1390, 900));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(hovedRamme, "Error loading PDF");
        }

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.weighty = 1.0;
        gbc.weightx = 0.1;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);
        JButton button1 = new JButton(txt.getPriorPage());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(button1, gbc);
        button1.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(curr > 0) {
                    curr--;
                    JLabel label = new JLabel(ipages.get(curr));
                    scrollPane.add(label);
                    scrollPane.setViewportView(label);
                    scrollPane.revalidate();
                    scrollPane.repaint();
                }
            }
        });
        JButton button2 = new JButton(txt.getNextPage());
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(button2, gbc);
        button2.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(curr < max-1) {
                    curr++;
                    JLabel label = new JLabel(ipages.get(curr));
                    scrollPane.add(label);
                    scrollPane.setViewportView(label);
                    scrollPane.revalidate();
                    scrollPane.repaint();
                }
            }
        });

        // Add the JPanel to the JDialog's content pane
        selectDialog.getContentPane().add(panel, BorderLayout.CENTER);

        // Make the JDialog visible
        selectDialog.setVisible(true);
    }
}
