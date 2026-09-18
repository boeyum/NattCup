package no.jib.nattcup.domain;

public class Lag {
    private String navn = new String();
    private int minutter = 0;
    private int maal = 0;
    private int kamper = 0;

    public Lag(String navn) {
        this.navn = navn;
        this.minutter = 0;
        this.maal = 0;
        this.kamper = 0;
    }

    public Lag(String navn, int minutter, int maal) {
        this.navn = navn;
        this.minutter = minutter;
        this.maal = maal;
        this.kamper = 0;
    }

    public void result(int minutter, int maal, boolean vinner) {
        this.minutter += minutter;
        this.maal += maal;
        if (vinner) {
            this.kamper += 1;
        } else {
            this.kamper = 0;
        }
    }

    public void setKamp() {
        this.kamper++;
    }

    public boolean isMaxKamper() {
        if(this.kamper > 2) {
            this.kamper = 0;
            return true;
        }
        return false;
    }

    public String getLagInfo() {
        return String.format("%-40s     Min.: %04d    -    %d", this.navn, this.minutter, this.maal);
    }

    public String getInfo() {
        return String.format("%03d min.         (%3d)            %s", this.minutter, this.maal, this.navn);
    }

    public String getNavn() {
        return navn;
    }

    public int getMinutter() {
        return minutter;
    }

    public int getMaal() {
        return maal;
    }

    public void modMinutter(int minutter) {
        this.minutter = minutter;
    }

    public void modMaal(int maal) {
        this.maal = maal;
    }
}
