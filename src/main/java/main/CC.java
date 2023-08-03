package main;

public enum CC {
    RESET("\u001B[0m"),

    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    BLUE("\u001B[34m"),
    CYAN("\u001B[36m"),
    YELLOW("\u001B[33m");

    private final String color;

    CC(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
