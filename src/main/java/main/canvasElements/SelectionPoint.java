package main.canvasElements;

import javafx.scene.shape.Rectangle;

public class SelectionPoint {
    public static double WIDTH = 8;
    public static double HEIGHT = 8;

    private SelectionPointType type;
    private double[] coordinates;
    private Rectangle uiElement;
    private boolean selected = false;

    public SelectionPoint(SelectionPointType type, double[] coordinates, Rectangle uiElement) {
        this.type = type;
        this.coordinates = coordinates;
        this.uiElement = uiElement;
    }

    public boolean isSelected() {
        return selected;
    }

    public void select() {
        selected = true;
    }

    public void deselect() {
        selected = false;
    }

    public SelectionPointType getType() {
        return type;
    }

    public void setType(SelectionPointType type) {
        this.type = type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public Rectangle getUiElement() {
        return uiElement;
    }

    public void setUiElement(Rectangle uiElement) {
        this.uiElement = uiElement;
    }

    public boolean overlaps(double x, double y, double width, double height) {
        return !((x + width/2 < coordinates[0] - WIDTH/2)
                ||
                (x - width/2 > coordinates[0] + WIDTH/2)
                ||
                (y + height/2 < coordinates[1] - HEIGHT/2)
                ||
                (y - height/2 > coordinates[1] + HEIGHT/2));
    }
}
