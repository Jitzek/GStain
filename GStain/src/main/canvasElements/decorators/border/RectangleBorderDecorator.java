package main.canvasElements.decorators.border;

import javafx.scene.paint.Color;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.SelectionBox;
import main.canvasElements.decorators.border.border.BorderStyle;
import main.strategies.canvasElementStrategies.deselect.DeselectElementStrategy;
import main.strategies.canvasElementStrategies.draw.DrawBorderStrategy;
import main.strategies.canvasElementStrategies.select.SelectElementStrategy;

import java.util.UUID;

public class RectangleBorderDecorator extends BorderDecorator {
    public RectangleBorderDecorator(CanvasElement element) {
        super(element);
    }

    public RectangleBorderDecorator(CanvasElement element, BorderStyle style, double thickness, Color color) {
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
        setSelected(true);
        new SelectElementStrategy().select(getParent(), this);
    }

    @Override
    public void deselect() {
        setSelected(false);
        new DeselectElementStrategy().deselect(getParent(), this);
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
        return !((x + width/2 < getX() - getWidth()/2 - getBorderThickness()/2)
                ||
                (x - width/2 > getX() + getWidth()/2 + getBorderThickness()/2)
                ||
                (y + height/2 < getY() - getHeight()/2 - getBorderThickness()/2)
                ||
                (y - height/2 > getY() + getHeight()/2 + getBorderThickness()/2));
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
    public void draw() {
        new DrawBorderStrategy().draw(getParent(), this);

        getElement().draw();
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
    public SelectionBox getSelectionBox() {
        return getElement().getSelectionBox();
    }

    @Override
    public void setSelectionBox(SelectionBox selectionBox) {
        getElement().setSelectionBox(selectionBox);
    }
}
