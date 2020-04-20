package main.canvasElements.shapes;

import javafx.scene.paint.Color;
import main.Canvas;

public class Rectangle extends Shape {
    private boolean selected;

    public Rectangle(double x, double y, Color color, double width, double height) {
        super(x, y, color, width, height);
    }

    @Override
    public boolean insideBounds(double x, double y) {
        return false;
    }

    @Override
    public boolean overlaps(double x, double y, double width, double height) {
        return !((x + width/2 < getX() - getWidth()/2)
                ||
                (x - width/2 > getX() + getWidth()/2)
                ||
                (y + height/2 < getY() - getHeight()/2)
                ||
                (y - height/2 > getY() + getHeight()/2));
    }

    @Override
    public void enableSelectionStyle(Canvas canvas) {

    }

    @Override
    public void disableSelectionStyle(Canvas canvas) {

    }
}
