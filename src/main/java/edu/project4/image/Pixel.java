package edu.project4.image;

public class Pixel {
    private int r;
    private int g;
    private int b;
    private int hitCount;

    public Pixel() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.hitCount = 0;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void incrementHitCount() {
        hitCount++;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public synchronized void modifyColor(int red, int green, int blue) {
        if (r == 0 && g == 0 && b == 0) {
            r = red;
            g = green;
            b = blue;
        } else {
            r = (r + red) / 2;
            g = (g + green) / 2;
            b = (b + blue) / 2;
        }
        hitCount++;
    }

    public synchronized void modifyBrightness(double coefficient) {
        r = (int) (r * coefficient);
        g = (int) (g * coefficient);
        b = (int) (b * coefficient);
    }
}
