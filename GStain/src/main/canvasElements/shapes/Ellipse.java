package main.canvasElements.shapes;

import javafx.scene.paint.Color;
import main.Canvas;

public class Ellipse extends Shape {
    public Ellipse(Canvas parent, double x, double y, Color color, double width, double height) {
        super(parent, x, y, color, width, height);
    }

    @Override
    public boolean insideBounds(double x, double y) {
        return false;
    }

    @Override
    public boolean overlaps(double x, double y, double width, double height) {
        // This is only an approximation
        double distX = x  - getX();
        double distY = y - getY();
        double distanceX = Math.sqrt( (distX*distX));
        double distanceY = Math.sqrt( (distY*distY));
        return (distanceX <= width + getWidth() / 2) && (distanceY <= height + getHeight() / 2);
    }

    @Override
    public void enableSelectionStyle(Canvas canvas) {

    }

    @Override
    public void disableSelectionStyle(Canvas canvas) {

    }
}
