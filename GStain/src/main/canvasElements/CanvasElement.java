package main.canvasElements;

import javafx.scene.paint.Color;
import main.Canvas;

import java.util.UUID;

public interface CanvasElement {
    UUID getUUID();
    void setUUID(UUID uuid);
    double getX();
    void setX(double x);
    double getY();
    void setY(double y);
    Color getColor();
    void setColor(Color color);
    double getElementOpacity();
    void setElementOpacity(double opacity);
    double getWidth();
    void setWidth(double width);
    double getHeight();
    void setHeight(double height);
    void select();
    void deselect();
    boolean isSelected();
    boolean insideBounds(double x, double y);
    boolean overlaps(double x, double y, double width, double height);
    void hide(Canvas canvas);
    void show(Canvas canvas);
    void enableSelectionStyle(Canvas canvas);
    void disableSelectionStyle(Canvas canvas);
}
