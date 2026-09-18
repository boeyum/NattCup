package no.jib.nattcup;


import no.jib.nattcup.domain.NCLib;
import no.jib.nattcup.forms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class NattCup {
    private NCLib BG = new NCLib();
    private JFrame frame;
    private BufferedImage bgImage;
    private BufferedImage bufferedImage;
    private ImageIcon imageIcon;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NattCup window = new NattCup();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public NattCup() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int baseWidth = (screenWidth/2)-250;
        int baseHeight = (screenHeight/2)-150;
        frame = new JFrame("");
        frame.setBounds(baseWidth, baseHeight, 400, 265);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        frame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        try {
            bgImage = BG.getLogo();
            imageIcon = new ImageIcon(bgImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel label = new JLabel(imageIcon);
        frame.getContentPane().add(label);

        frame.setVisible(true);
        Timer timer = new Timer(5000, new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                frame.dispose();
                frame.setVisible(false);
                MainScreen web = new MainScreen();
                web.main(null);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}