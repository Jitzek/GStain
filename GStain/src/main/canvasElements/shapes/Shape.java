package main.canvasElements.shapes;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import main.Canvas;
import main.canvasElements.CanvasElement;

import java.util.UUID;

public abstract class Shape implements CanvasElement {
    private final Canvas parent;
    private Path selectionStyle = null;
    private UUID uuid;
    private double x;
    private double y;
    private Color color;
    private double opacity;
    private double width;
    private double height;
    private boolean selected = false;

    public Shape(Canvas parent, double x, double y, Color color, double width, double height) {
        this.parent =parent;
        this.uuid = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.color = color;
        this.width = width;
        this.height = height;
        opacity = 1;
    }

    @Override
    public Canvas getParent() {
        return parent;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) { this.uuid = uuid; }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getElementOpacity() {
        return opacity;
    }

    public void setElementOpacity(double opacity) { this.opacity = opacity; }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void hide(Canvas canvas) {
        canvas.getChildren().get(canvas.getIndexOfElement(this, true)).setOpacity(0.1);
    }

    public void show(Canvas canvas) {
        canvas.getChildren().get(canvas.getIndexOfElement(this, true)).setOpacity(getElementOpacity());
    }

    @Override
    public void select() {
        selected = true;
    }

    @Override
    public void deselect() {
        selected = false;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelectionStyle(Path selectionStyle) {
        this.selectionStyle = selectionStyle;
    }

    @Override
    public Path getSelectionStyle() {
        return selectionStyle;
    }
}
