package no.jib.nattcup.forms;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DisplayDialog extends JFrame {

    public void showHtmlDialog(JFrame hovedRamme, String title) { // Center relative to the parent frame

        // Create the JDialog
        JDialog selectDialog = new JDialog(hovedRamme, title, true); // 'true' for modal dialog
        selectDialog.setSize(1080, 800);
        selectDialog.setLocationRelativeTo(hovedRamme);

        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);

        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setEditable(false);
        jEditorPane.setMargin(new Insets(10, 15, 10, 15));
        File file = new File("config/rules.html");
        URL url;
        try {
             url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try {
            jEditorPane.setPage(url);
        } catch (IOException e) {
            jEditorPane.setContentType("text/html");
            jEditorPane.setText("<html>Page not found.</html>");
        }

        JScrollPane jScrollPane = new JScrollPane(jEditorPane);
        jScrollPane.setPreferredSize(new Dimension(1050,800));

        panel.add(jScrollPane);

        // Add the JPanel to the JDialog's content pane
        selectDialog.getContentPane().add(panel, BorderLayout.CENTER);

        // Make the JDialog visible
        selectDialog.setVisible(true);
    }
}
