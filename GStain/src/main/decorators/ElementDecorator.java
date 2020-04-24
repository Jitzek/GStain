package main.decorators;

import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import main.BorderStyle;
import main.Canvas;
import main.canvasElements.CanvasElement;

import java.util.UUID;

public abstract class ElementDecorator implements CanvasElement {
    protected CanvasElement element;

    public ElementDecorator(CanvasElement element) {
        super();
        this.element = element;
    }

    public CanvasElement getElement() {
        return element;
    }

    @Override
    public Canvas getParent() {
        return element.getParent();
    }

    @Override
    public UUID getUUID() {
        return element.getUUID();
    }

    @Override
    public void setUUID(UUID uuid) {
        element.setUUID(uuid);
    }

    @Override
    public double getX() {
        return element.getX();
    }

    @Override
    public void setX(double x) {
        element.setX(x);
    }

    @Override
    public double getY() {
        return element.getY();
    }

    @Override
    public void setY(double y) {
        element.setY(y);
    }

    @Override
    public Color getColor() {
        return element.getColor();
    }

    @Override
    public void setColor(Color color) {
        element.setColor(color);
    }

    @Override
    public double getElementOpacity() {
        return element.getElementOpacity();
    }

    @Override
    public void setElementOpacity(double opacity) {
        element.setElementOpacity(opacity);
    }

    @Override
    public double getWidth() {
        return element.getWidth();
    }

    @Override
    public void setWidth(double width) {
        element.setWidth(width);
    }

    @Override
    public double getHeight() {
        return element.getHeight();
    }

    @Override
    public void setHeight(double height) {
        element.setHeight(height);
    }

    @Override
    public void select() {
        element.select();
    }

    @Override
    public void deselect() {
        element.deselect();
    }

    @Override
    public boolean isSelected() {
        return element.isSelected();
    }

    @Override
    public boolean insideBounds(double x, double y) {
        return element.insideBounds(x, y);
    }

    @Override
    public boolean overlaps(double x, double y, double width, double height) {
        return element.overlaps(x, y, width, height);
    }

    @Override
    public void setSelectionStyle(Path selectionStyle) {
        element.setSelectionStyle(selectionStyle);
    }

    @Override
    public Path getSelectionStyle() {
        return element.getSelectionStyle();
    }
}
