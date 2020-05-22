package main.canvasElements.decorators.border;

import javafx.scene.paint.Color;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.SelectionBox;
import main.canvasElements.decorators.border.border.BorderStyle;
import main.visitor.CanvasElementVisitor;

import java.util.UUID;

public class EllipseBorderDecorator extends BorderDecorator {
    public EllipseBorderDecorator(CanvasElement element) {
        super(element);
    }

    public EllipseBorderDecorator(CanvasElement element, BorderStyle style, double thickness, Color color) {
        super(element, style, thickness, color);
    }

    @Override
    public Canvas getParent() {
        return getElement().getParent();
    }

    @Override
    public UUID getUUID() {
        return getElement().getUUID();
    }

    @Override
    public void setUUID(UUID uuid) {
        getElement().setUUID(uuid);
    }

    @Override
    public double getX() {
        return getElement().getX();
    }

    @Override
    public void setX(double x) {
        getElement().setX(x);
    }

    @Override
    public double getY() {
        return getElement().getY();
    }

    @Override
    public void setY(double y) {
        getElement().setY(y);
    }

    @Override
    public Color getColor() {
        return getElement().getColor();
    }

    @Override
    public void setColor(Color color) {
        getElement().setColor(color);
    }

    @Override
    public double getElementOpacity() {
        return getElement().getElementOpacity();
    }

    @Override
    public void setElementOpacity(double opacity) {
        getElement().setElementOpacity(opacity);
    }

    @Override
    public double getWidth() {
        return getElement().getWidth() + getBorderThickness();
    }

    @Override
    public void setWidth(double width) {
        getElement().setWidth(width);
    }

    @Override
    public double getHeight() {
        return getElement().getHeight() + getBorderThickness();
    }

    @Override
    public void setHeight(double height) {
        getElement().setHeight(height);
    }

    @Override
    public void select() {
        getElement().select();
    }

    @Override
    public void deselect() {
        getElement().deselect();
    }

    @Override
    public boolean isSelected() {
        return getElement().isSelected();
    }

    @Override
    public boolean insideBounds(double x, double y) {
        return getElement().insideBounds(x, y);
    }

    @Override
    public boolean overlaps(double x, double y, double width, double height) {
        // This is only an approximation
        double distX = x - getX();
        double distY = y - getY();
        double distanceX = Math.sqrt( (distX*distX));
        double distanceY = Math.sqrt( (distY*distY));
        return (distanceX <= width + getWidth() / 2 + getBorderThickness()/2) && (distanceY <= height + getHeight() / 2 + getBorderThickness()/2);
    }

    @Override
    public void hide() {
        getElement().hide();

        getBorder().setOpacity(0.1);
    }

    @Override
    public void show() {
        getElement().show();

        getBorder().setOpacity(getElementOpacity());
    }

    @Override
    public void recolor(Color color) {
        getElement().recolor(color);
    }

    @Override
    public void drag(double x, double y) {
        getElement().drag(x, y);

        getBorder().setTranslateX(getBorder().getTranslateX() + x);
        getBorder().setTranslateY(getBorder().getTranslateY() + y);
    }

    @Override
    public void position(double x, double y) {
        getElement().position(x, y);
    }

    @Override
    public String accept(CanvasElementVisitor canvasElementVisitor) {
        return null;
    }

    @Override
    public SelectionBox getSelectionBox() {
        return getElement().getSelectionBox();
    }

    @Override
    public void setSelectionBox(SelectionBox selectionBox) {
        getElement().setSelectionBox(selectionBox);
    }

}
