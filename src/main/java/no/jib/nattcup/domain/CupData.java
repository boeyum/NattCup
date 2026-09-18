package no.jib.nattcup.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class CupData {
    private String fileName = new String();
    private int spilletid = 0;
    private int lengde = 0;
    private HashMap<String, Lag> lagbase = new HashMap();
    private ArrayList<String> laglist = new ArrayList<>();
    private String kampHjemme = new String();
    private String kampBorte = new String();

    public void clear() {
        lagbase.clear();
        laglist.clear();
        kampHjemme = "";
        kampBorte = "";
        spilletid = 0;
        lengde = 0;
        fileName = "";
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void initierCup() {
        ArrayList<String> tempListe = new ArrayList<>();
        ArrayList<String> keySet = getKeySet();
        for (String key : keySet) {
            Random rand = new Random();
            int randomNumber = rand.nextInt(100); // Generates a number from 0 to 9
            String line = String.format("%03d;%s;", randomNumber, key);
            tempListe.add(line);
        }
        tempListe.sort(String.CASE_INSENSITIVE_ORDER);
        laglist.clear();
        for(int i = 0; i < tempListe.size(); i++) {
            String [] base = tempListe.get(i).split(";");
            if(i == 0) {
                kampHjemme = base[1];
            } else if(i == 1) {
                kampBorte = base[1];
            } else {
                laglist.add(base[1]);
            }
        }
    }

    public void setSpilletid(int matchlength) {
        this.spilletid = matchlength;
    }

    public void setLengde(int turnamentlenght) {
        this.lengde = turnamentlenght;
    }

    public void addLag(String navn) {
        Lag lag = new Lag(navn);
        lagbase.put(navn, lag);
        laglist.add(navn);
    }

    public void addLag(String navn, Lag lag) {
        lagbase.put(navn, lag);
        laglist.add(navn);
    }

    public ArrayList<String> getKeySet() {
        return new ArrayList(lagbase.keySet());
    }

    public Lag getLag(String key) {
        return lagbase.get(key);
    }

    public void setLag(String key, Lag lag) {
        lagbase.put(key, lag);
    }

    public void setResult(int spilletid, String hjlag, int hj, String bolag, int bo) {
        Lag l1 = lagbase.get(hjlag);
        boolean hkode = l1.isMaxKamper();
        l1.result(spilletid,hj, hjemmeVinner(hj, bo));
        lagbase.put(hjlag,l1);
        Lag l2 = lagbase.get(bolag);
        l2.result(spilletid,bo, borteVinner(hj, bo));
        lagbase.put(bolag,l2);
        if(hj == bo) {
            laglist.add(bolag);
            laglist.add(hjlag);
            kampHjemme = laglist.get(0);
            kampBorte = laglist.get(1);
            roll(2);
        } else if(hj < bo) {
            laglist.add(hjlag);
            kampHjemme = bolag;
            kampBorte = laglist.get(0);
            roll(1);
        } else if(hj > bo) {
            if(hkode) {
                laglist.add(bolag);
                laglist.add(hjlag);
                kampHjemme = laglist.get(0);
                kampBorte = laglist.get(1);
                roll(2);
            } else {
                laglist.add(bolag);
                kampHjemme = hjlag;
                kampBorte = laglist.get(0);
                roll(1);
            }
        }
    }

    public boolean isInit() {
        int spmin = 0;
        ArrayList<String> keySet = new ArrayList(lagbase.keySet());
        for(int x = 0; x < keySet.size(); x++) {
            spmin += lagbase.get(keySet.get(x)).getMaal();
        }
        if(spmin == 0) {
            return false;
        }
        return true;
    }

    public String getHjemmeLag() {
        return kampHjemme;
    }

    public String getBorteLag() {
        return kampBorte;
    }

    public ArrayList<String> getLagListe() {
        ArrayList<String> temp = new ArrayList();
        for (int x = 0; x < laglist.size(); x++) {
            temp.add(lagbase.get(laglist.get(x)).getLagInfo());
        }
        return temp;
    }

    public ArrayList<String> getResultatListe() {
        ArrayList<String> keySet = new ArrayList(lagbase.keySet());
        ArrayList<String> templiste = new ArrayList<>();
        for(int x = 0; x < keySet.size(); x++) {
            templiste.add(lagbase.get(keySet.get(x)).getInfo());
        }
        templiste.sort(String.CASE_INSENSITIVE_ORDER.reversed());
        return templiste;
    }

    private void roll(int num) {
        ArrayList<String> temp = new ArrayList();
        for(int x = num; x < laglist.size(); x++) {
            temp.add(laglist.get(x));
        }
        laglist.clear();
        for(int x = 0; x < temp.size(); x++) {
            laglist.add(temp.get(x));
        }
    }

    public String getFileName() {
        return fileName;
    }

    public int getMatchLength() {
        return spilletid;
    }

    public int getTurnamentLenght() {
        return lengde;
    }

    private boolean hjemmeVinner(int h, int b) {
        if(h > b) {
            return true;
        }
        return false;
    }

    private boolean borteVinner(int h, int b) {
        if(h < b) {
            return true;
        }
        return false;
    }
}
