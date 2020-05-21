package main.canvasElements.decorators.border;

import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import main.canvasElements.CanvasElement;
import main.canvasElements.decorators.border.border.BorderStyle;
import main.factories.BorderFactory;
import main.strategies.canvasElementStrategies.size.ResizeBorderStrategy;
import main.visitor.CanvasElementVisitor;

public abstract class BorderDecorator implements CanvasElement {
    private final CanvasElement element;
    private Path border;
    private BorderStyle borderStyle = BorderStyle.SOLID;
    private double borderThickness = 1.0;
    private Color borderColor = Color.YELLOW;

    public BorderDecorator(CanvasElement element) {
        this.element = element;
        setBorder(BorderFactory.getBorder(
                element.getClass().getSimpleName().toUpperCase(),
                element.getX(),
                element.getY(),
                element.getWidth(),
                element.getHeight(),
                getBorderStyle(),
                getBorderThickness(),
                getBorderColor()));
    }

    public BorderDecorator(CanvasElement element, BorderStyle borderStyle, double borderThickness, Color borderColor) {
        this.element = element;
        this.borderStyle = borderStyle;
        this.borderThickness = borderThickness;
        this.borderColor = borderColor;
        setBorder(BorderFactory.getBorder(
                element.getClass().getSimpleName().toUpperCase(),
                element.getX(),
                element.getY(),
                element.getWidth(),
                element.getHeight(),
                getBorderStyle(),
                getBorderThickness(),
                getBorderColor()));
    }

    public CanvasElement getElement() {
        return element;
    }

    public Path getBorder() {
        return border;
    }

    public void setBorder(Path border) {
        this.border = border;
    }

    public BorderStyle getBorderStyle() {
        return borderStyle;
    }

    public void setBorderStyle(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }

    public double getBorderThickness() {
        return borderThickness;
    }

    public void setBorderThickness(double borderThickness) {
        this.borderThickness = borderThickness;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    public void setSelected(boolean selected) {
        getElement().setSelected(selected);
    }

    @Override
    public void resize(double width, double height) {
        getElement().resize(width, height);
        new ResizeBorderStrategy().resize(getElement().getParent(), this, width, height);
    }

    @Override
    public void resizeWidth(double width) {
        getElement().resizeWidth(width);

        new ResizeBorderStrategy().resize(getElement().getParent(), this, width, getElement().getHeight());
    }

    @Override
    public void resizeHeight(double height) {
        getElement().resizeHeight(height);

        new ResizeBorderStrategy().resize(getElement().getParent(), this, getElement().getWidth(), height);
    }

    public void recolorBorder(Color color) {
        setBorderColor(color);
        getBorder().setStroke(color);
    }


    @Override
    public void remove() {
        deselect();
        element.remove();

        element.getParent().getChildren().remove(border);
    }


}
