package no.jib.nattcup.domain;

public class FormSizes {
    private int width = 0;
    private int height = 0;
    private int top = 0;
    private int middle = 0;
    private int bottom = 0;
    private int split = 0;

    public void createFormSizes(int twidth, int theight) {
        this.width = twidth;
        this.height = theight;
        this.top = Math.abs(height / 10) - 100;
        this.bottom = this.top;
        this.middle = (this.height - (this.top + this.bottom + 200));
        this.split = Math.abs(twidth / 2);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTop() {
        return top;
    }

    public int getMiddle() {
        return middle;
    }

    public int getBottom() {
        return bottom;
    }

    public int getSplitWidth() {
        return split;
    }
}
