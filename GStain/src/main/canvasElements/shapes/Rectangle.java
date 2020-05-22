package main.canvasElements.shapes;

import javafx.scene.paint.Color;
import main.Canvas;
import main.strategies.canvasElementStrategies.draw.DrawRectangleStrategy;
import main.strategies.canvasElementStrategies.size.ResizeRectangleStrategy;

public class Rectangle extends Shape {
    private javafx.scene.shape.Rectangle rectangle;

    public Rectangle(Canvas parent, double x, double y, Color color, double width, double height) {
        super(parent, x, y, color, width, height);
    }

    public javafx.scene.shape.Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(javafx.scene.shape.Rectangle rectangle) {
        this.rectangle = rectangle;
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
    public void recolor(Color color) {
        setColor(color);
        rectangle.setFill(color);
    }

    public void hide() {
        getRectangle().setOpacity(0.1);
        //getParent().getChildren().get(getParent().getChildren().indexOf(rectangle)).setOpacity(0.1);
    }

    public void show() {
        getRectangle().setOpacity(getElementOpacity());
        //getParent().getChildren().get(getParent().getChildren().indexOf(rectangle)).setOpacity(getElementOpacity());
    }

    @Override
    public void draw() {
        new DrawRectangleStrategy().draw(getParent(), this);
    }

    @Override
    public void remove() {
        deselect();
        getParent().getChildren().remove(rectangle);
    }

    @Override
    public void drag(double x, double y) {
        setX(getX() + x);
        setY(getY() + y);

        if (isSelected()) {
            getSelectionBox().drag(x, y);
        }

        rectangle.setTranslateX(rectangle.getTranslateX() + x);
        rectangle.setTranslateY(rectangle.getTranslateY() + y);
    }

    @Override
    public void resize(double width, double height) {
        new ResizeRectangleStrategy().resize(getParent(), this, width, height);
    }

    @Override
    public void resizeWidth(double width) {
        new ResizeRectangleStrategy().resize(getParent(), this, width, getHeight());
    }

    @Override
    public void resizeHeight(double height) {
        new ResizeRectangleStrategy().resize(getParent(), this, getWidth(), height);
    }
}
