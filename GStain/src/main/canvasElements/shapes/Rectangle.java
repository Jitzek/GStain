package main.canvasElements.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import main.Canvas;
import main.strategies.canvasElementStrategies.deselect.DeselectElementStrategy;
import main.strategies.canvasElementStrategies.select.SelectRectangleStrategy;

public class Rectangle extends Shape {
    public Rectangle(Canvas parent, double x, double y, Color color, double width, double height) {
        super(parent, x, y, color, width, height);
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
        new SelectRectangleStrategy().select(canvas, this);
    }

    @Override
    public void disableSelectionStyle(Canvas canvas) {
        new DeselectElementStrategy().deselect(canvas, this);
    }
}
