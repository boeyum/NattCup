package no.jib.nattcup.forms;

import no.jib.nattcup.domain.CupData;
import no.jib.nattcup.domain.FormSizes;
import no.jib.nattcup.domain.Lag;
import no.jib.nattcup.domain.NCicon;
import no.jib.nattcup.utillities.CupDataHandler;
import no.jib.nattcup.utillities.JsonHandler;
import no.jib.nattcup.utillities.TempHandler;
import no.jib.nattcup.utillities.TextHandler;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainScreen {
    private TempHandler TH = new TempHandler();
    private JsonHandler JH = new JsonHandler();
    private CupDataHandler CDH = new CupDataHandler();
    private NCicon ncicon = new NCicon();
    private FormSizes frms = new FormSizes();
    private JMenuBar menuBar;
    private JMenu menu, wrkmenu;
    private JMenuItem nytt, aapne, steng, slett, slutt;
    private JMenuItem lag, grpset, rule, manual;
    private JButton start = null;
    private JButton pause = null;
    private JButton Borte = null;
    private JButton Hjemme = null;
    private JButton inits = null;
    private JButton lagre = null;
    private JList<String> res = null;
    private JList<String> lst = null;
    private TextHandler txt = null;
    private Timer gameTimer;
    private CupData CD = new CupData();
    private DefaultListModel<String> liste = new DefaultListModel<>();
    private DefaultListModel<String> resul = new DefaultListModel<>();
    private int timeLeft = 0;
    private int hmal = 0;
    private int bmal = 0;
    private boolean fileOpen = false;

    public MainScreen() {
        JFrame frame = new JFrame("NattCup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(dim.width,dim.height);
        frms.createFormSizes(dim.width,dim.height);
        try {
            BufferedImage bgImage = ncicon.getLogo();
            ImageIcon imageIcon = new ImageIcon(bgImage);
            frame.setIconImage(imageIcon.getImage());
            txt = new TextHandler();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Language support: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            progExit();
        }

        // Main frame uses BorderLayout by default
        frame.setLayout(new BorderLayout());
        // Menue setup
        menuBar = new JMenuBar();
        menu = new JMenu(txt.getFname());
        nytt = new JMenuItem(txt.getCreate());
        menu.add(nytt);
        aapne = new JMenuItem(txt.getOpen());
        menu.add(aapne);
        steng = new JMenuItem(txt.getSave());
        menu.add(steng);
        slutt = new JMenuItem(txt.getExit());
        menu.add(slutt);
        menuBar.add(menu);
        nytt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileOpen) {
                    int choice = JOptionPane.showConfirmDialog(null, txt.getMsg2(),
                            txt.getMsg2_head(), JOptionPane.YES_NO_OPTION);
                    if(choice == JOptionPane.YES_OPTION) {
                        String fname = CD.getFileName();
                        JSONObject data = JH.generateTurnamentJson(fname, CD);
                        try {
                            CDH.save(fname, data);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
                CD.clear();
                fileOpen = false;
            }
        });
        aapne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileFilter ff = new FileNameExtensionFilter("JSON file", "json");
                fileChooser.setCurrentDirectory(new File("data"));
                fileChooser.addChoosableFileFilter(ff);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fname = selectedFile.getAbsolutePath();
                    try {
                        JSONObject data = CDH.load(fname);
                        CupData cd = JH.getCupDataFromJson(data);
                        loadData(cd);
                        fileOpen = true;
                        if(CD.isInit()) {
                            start.setEnabled(true);
                        } else {
                            inits.setEnabled(true);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
        });
        steng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!fileOpen) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("data"));
                    int option = fileChooser.showSaveDialog(frame);
                    if(option == JFileChooser.APPROVE_OPTION){
                        File file = fileChooser.getSelectedFile();
                        CD.setFileName(file.getAbsolutePath());
                        fileOpen = true;
                    } else {
                        return;
                    }
                }
                if(fileOpen) {
                    String fname = CD.getFileName();
                    JSONObject data = JH.generateTurnamentJson(fname, CD);
                    try {
                        CDH.save(fname, data);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                CD.clear();
                fileOpen = false;
                JOptionPane.showMessageDialog(null, txt.getMsg1(), txt.getMsg1(), JOptionPane.PLAIN_MESSAGE);
            }
        });
        slutt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progExit();
            }
        });
        wrkmenu = new JMenu(txt.getSname());
        lag = new JMenuItem(txt.getTeam());
        wrkmenu.add(lag);
        grpset = new JMenuItem(txt.getTime());
        wrkmenu.add(grpset);
        rule = new JMenuItem(txt.getRules());
        wrkmenu.add(rule);
        manual = new JMenuItem(txt.getManual());
        wrkmenu.add(manual);
        menuBar.add(wrkmenu);
        lag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = new String();
                TeamForm tem = new TeamForm();
                tem.showTeamDialog(frame);
                try {
                    data = TH.fetch();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if(!data.contains("CANCEL")) {
                    CD.addLag(data);
                }
                liste.clear();
                ArrayList<String> listeb = CD.getLagListe();
                for(int i = 0; i < listeb.size(); i++) {
                    liste.addElement(String.format("%3d   %s", i+1, listeb.get(i)));
                }
            }
        });
        grpset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = new String();
                TimeForm tem = new TimeForm();
                tem.showTimeDialog(frame);
                try {
                    data = TH.fetch();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                if(!data.contains("CANCEL")) {
                    String [] base = data.split(";");
                    CD.setLengde(Integer.parseInt(base[0]));
                    CD.setSpilletid(Integer.parseInt(base[1]));
                }
            }
        });
        rule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayDialog frm = new DisplayDialog();
                frm.showHtmlDialog(frame, txt.getRules());
            }
        });
        manual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowPDF frm = new ShowPDF();
                frm.showPdfForm(frame);
            }
        });
        frame.setJMenuBar(menuBar);

        // Panel 1: Top navigation header
        JPanel headerPanel = new JPanel();
//        headerPanel.setBackground(Color.LIGHT_GRAY);
        headerPanel.setPreferredSize(new Dimension(1920, 150));
        headerPanel.setBorder(BorderFactory.createTitledBorder(txt.getTop()));
        Font newFont = new Font("Arial", Font.PLAIN, 30);
        JTextField klokke = new JTextField();
        klokke.setColumns(4);
        klokke.setEditable(false);
        klokke.setFont(newFont);
        headerPanel.add(klokke);
        headerPanel.add(new JLabel("                 TIME     "));
        JTextField tid = new JTextField();
        tid.setColumns(4);
        tid.setEditable(false);
        tid.setFont(newFont);

        headerPanel.add(tid);
        headerPanel.add(new JLabel("                        "));
        headerPanel.add(new JLabel("                        "));
        headerPanel.add(new JLabel("                        "));
        JTextField hjlag = new JTextField();
        hjlag.setColumns(14);
        hjlag.setEditable(false);
        hjlag.setFont(newFont);
        headerPanel.add(hjlag);
        headerPanel.add(new JLabel("     "));
        JTextField hjmal = new JTextField();
        hjmal.setColumns(2);
        hjmal.setEditable(false);
        hjmal.setFont(newFont);
        hjmal.setHorizontalAlignment(JTextField.RIGHT);
        headerPanel.add(hjmal);
        headerPanel.add(new JLabel("          VS          "));
        JTextField bolag = new JTextField();
        bolag.setColumns(14);
        bolag.setEditable(false);
        bolag.setFont(newFont);
        headerPanel.add(bolag);
        headerPanel.add(new JLabel("     "));
        JTextField bomal = new JTextField();
        bomal.setColumns(2);
        bomal.setEditable(false);
        bomal.setFont(newFont);
        bomal.setHorizontalAlignment(JTextField.RIGHT);
        headerPanel.add(bomal);
        klokke.setText("00:00");
        tid.setText("00:00");
        hjmal.setText("0");
        bomal.setText("0");

        // Panel 2: Sidebar
        TitledBorder centeredTitleBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.RED),
                txt.getCenter(),
                TitledBorder.CENTER, // Center justification
                TitledBorder.TOP     // Default top position
        );
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(1, 2, 20, 20));
//        middlePanel.setBackground(Color.GRAY);
        middlePanel.setPreferredSize(new Dimension(1920, 400));
        middlePanel.setBorder(centeredTitleBorder);
        res = new JList<>(resul);
        res.setPreferredSize(new Dimension(850,340));
        res.setEnabled(false);
        res.setFont(new Font("Monospaced", Font.PLAIN, 16));
        middlePanel.add(res);
        lst = new JList<>(liste);
        lst.setPreferredSize(new Dimension(850,340));
        lst.setEnabled(false);
        lst.setFont(new Font("Monospaced", Font.PLAIN, 16));
        middlePanel.add(lst);

        // Panel 3: Main dashboard area
        JPanel bottomPanel = new JPanel();
//        bottomPanel.setBackground(Color.BLUE);
        bottomPanel.setPreferredSize(new Dimension(1920, 200));
        bottomPanel.setBorder(BorderFactory.createTitledBorder(txt.getBottom()));
        start = new JButton(txt.getStart());
        start.setPreferredSize(new Dimension(200,50));
        bottomPanel.add(start);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft = (CD.getMatchLength() * 60);
                tid.setText(String.format("%02d:%02d", Math.abs(timeLeft/60), timeLeft - (Math.abs(timeLeft/60)*60)));

                gameTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        timeLeft--;
                        tid.setText(String.format("%02d:%02d", Math.abs(timeLeft/60), timeLeft - (Math.abs(timeLeft/60)*60)));
                        if(timeLeft <= 0) {
                            gameTimer.stop();
                            Toolkit.getDefaultToolkit().beep();
                            Hjemme.setEnabled(false);
                            Borte.setEnabled(false);
                            pause.setEnabled(true);
                            lagre.setEnabled(true);
                        }
                    }
                });
                Hjemme.setEnabled(true);
                Borte.setEnabled(true);
                pause.setEnabled(false);
                start.setEnabled(false);
                lagre.setEnabled(false);
                gameTimer.start();
            }
        });
        start.setEnabled(false);
        bottomPanel.add(new JLabel("     "));
        pause = new JButton(txt.getPause());
        pause.setPreferredSize(new Dimension(200,50));
        bottomPanel.add(pause);
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String home = hjlag.getText();
                String hgoal = hjmal.getText();
                String away = bolag.getText();
                String bgoal = bomal.getText();
                CD.setResult(5, home, Integer.parseInt(hgoal), away, Integer.parseInt(bgoal));
                loadResult(CD);
                hjlag.setText(CD.getHjemmeLag());
                bolag.setText(CD.getBorteLag());
                loadData(CD);
                hjmal.setText("0");
                bomal.setText("0");
                hmal = 0;
                bmal = 0;
                Hjemme.setEnabled(false);
                Borte.setEnabled(false);
                pause.setEnabled(false);
                start.setEnabled(true);
                lagre.setEnabled(true);
            }
        });
        pause.setEnabled(false);
        bottomPanel.add(new JLabel("                            "));
        bottomPanel.add(new JLabel("                            "));
        Hjemme = new JButton(txt.getHomeGoal());
        Hjemme.setPreferredSize(new Dimension(250,50));
        bottomPanel.add(Hjemme);
        Hjemme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hmal++;
                hjmal.setText(String.format("%d",hmal));
            }
        });
        Hjemme.setEnabled(false);
        bottomPanel.add(new JLabel("     "));
        Borte = new JButton(txt.getAwayGoal());
        Borte.setPreferredSize(new Dimension(250,50));
        bottomPanel.add(Borte);
        Borte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bmal++;
                bomal.setText(String.format("%d",bmal));
            }
        });
        Borte.setEnabled(false);
        bottomPanel.add(new JLabel("                                                  "));
        bottomPanel.add(new JLabel("                                                  "));
        inits = new JButton(txt.getInit());
        inits.setPreferredSize(new Dimension(200,50));
        bottomPanel.add(inits);
        inits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CD.initierCup();
                loadData(CD);
                loadResult(CD);
                hjlag.setText(CD.getHjemmeLag());
                bolag.setText(CD.getBorteLag());
                hjmal.setText("0");
                bomal.setText("0");
                inits.setEnabled(false);
                start.setEnabled(true);
            }
        });
        inits.setEnabled(false);
        bottomPanel.add(new JLabel("     "));
        lagre = new JButton(txt.getBackup());
        lagre.setPreferredSize(new Dimension(200,50));
        bottomPanel.add(lagre);
        lagre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> temp = CD.getKeySet();
                String rec = new  String(temp.get(0));
                rec += ";";
                for(int i = 1; i < temp.size(); i++) {
                    rec += temp.get(i);
                    rec += ";";
                }
                try {
                    TH.save(rec);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                SelectDialog selectDialog = new SelectDialog();
                selectDialog.showSelectDialog(  frame);
                String team;
                try {
                    team = TH.fetch();
                } catch (IOException ex) {
                    return;
                }
                Lag nu = CD.getLag(team);
                String rec2 = String.format("%s;%d;%d;",nu.getNavn(),nu.getMinutter(),nu.getMaal());
                try {
                    TH.save(rec2);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                EmergencyEdit frm = new EmergencyEdit();
                frm.showEditDialog(frame);
                String ret;
                try {
                    ret = TH.fetch();
                } catch (IOException ex) {
                    return;
                }
                String [] base = ret.split(";");
                nu.modMinutter(Integer.parseInt(base[1]));
                nu.modMaal(Integer.parseInt(base[2]));
                CD.setLag(base[0],nu);
            }
        });
        lagre.setEnabled(false);

        // Mount panels to the layout regions
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        Timer klokkeTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                klokke.setText(formatter.format(dateTime));
            }
        });
        klokkeTimer.start();
        frame.setVisible(true);
    }

    public void loadData(CupData cd) {
        CD = cd;
        liste.clear();
        ArrayList<String> base = cd.getLagListe();
        for(int x = 0; x < base.size(); x++) {
            liste.addElement(String.format("%3d   %s", x+1, base.get(x)));
        }
    }

    public void loadResult(CupData cd) {
        CD = cd;
        resul.clear();
        ArrayList<String> base = cd.getResultatListe();
        for(int x = 0; x < base.size(); x++) {
            resul.addElement(String.format("%3d   %s", x+1, base.get(x)));
        }
    }

    public void progExit() {
        System.exit(0);
    }

    public static void main(String args[]){
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new MainScreen();
            }
        });
    }
}
