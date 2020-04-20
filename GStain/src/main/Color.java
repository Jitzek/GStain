package main;

public enum Color {
    BLACK(0,0,0);

    private final int r;
    private final int g;
    private final int b;

    Color(final int r, final int g, final int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public String getRGB() {
        return r + ", " + g + ", " + b;
    }

    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return b;
    }
}
