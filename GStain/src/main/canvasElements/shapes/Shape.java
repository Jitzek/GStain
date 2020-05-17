package main.canvasElements.shapes;

import javafx.scene.paint.Color;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.SelectionBox;
import main.strategies.canvasElementStrategies.deselect.DeselectElementStrategy;
import main.strategies.canvasElementStrategies.select.SelectElementStrategy;

import java.util.UUID;

public abstract class Shape implements CanvasElement {
    private final Canvas parent;
    private SelectionBox selectionBox = null;
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

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void select() {
        selected = true;
        new SelectElementStrategy().select(getParent(), this);
    }

    @Override
    public void deselect() {
        selected = false;
        new DeselectElementStrategy().deselect(getParent(), this);
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void position(double x, double y) {

    }

    @Override
    public SelectionBox getSelectionBox() {
        return selectionBox;
    }

    @Override
    public void setSelectionBox(SelectionBox selectionBox) {
        this.selectionBox = selectionBox;
    }
}
