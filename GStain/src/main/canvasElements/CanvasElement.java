package main.canvasElements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import main.Canvas;
import main.visitor.CanvasElementVisitor;

import java.util.UUID;

public interface CanvasElement {
    Canvas getParent();
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
    void setSelected(boolean selected);
    void select();
    void deselect();
    boolean isSelected();
    boolean insideBounds(double x, double y);
    boolean overlaps(double x, double y, double width, double height);
    void hide();
    void show();
    void recolor(Color color);
    void draw();
    void remove();
    void drag(double x, double y);
    void resize(double width, double height);
    void resizeWidth(double width);
    void resizeHeight(double height);
    void position(double x, double y);
    String accept(CanvasElementVisitor canvasElementVisitor);
    SelectionBox getSelectionBox();
    void setSelectionBox(SelectionBox selectionBox);
}
