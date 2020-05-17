package main.canvasElements.shapes;

import javafx.scene.paint.Color;
import main.Canvas;
import main.strategies.canvasElementStrategies.draw.DrawEllipseStrategy;
import main.strategies.canvasElementStrategies.size.ResizeEllipseStrategy;
import main.visitor.CanvasElementVisitor;

public class Ellipse extends Shape {
    private javafx.scene.shape.Ellipse ellipse;

    public Ellipse(Canvas parent, double x, double y, Color color, double width, double height) {
        super(parent, x, y, color, width, height);
    }

    public javafx.scene.shape.Ellipse getEllipse() {
        return ellipse;
    }

    public void setEllipse(javafx.scene.shape.Ellipse ellipse) {
        this.ellipse = ellipse;
    }

    @Override
    public boolean insideBounds(double x, double y) {
        return false;
    }

    @Override
    public boolean overlaps(double x, double y, double width, double height) {
        // This is only an approximation
        double distX = x  - getX();
        double distY = y - getY();
        double distanceX = Math.sqrt( (distX*distX));
        double distanceY = Math.sqrt( (distY*distY));
        return (distanceX <= width + getWidth() / 2) && (distanceY <= height + getHeight() / 2);
    }

    @Override
    public void hide() {
        getParent().getChildren().get(getParent().getChildren().indexOf(ellipse)).setOpacity(0.1);
    }

    @Override
    public void show() {
        getParent().getChildren().get(getParent().getChildren().indexOf(ellipse)).setOpacity(getElementOpacity());
    }

    @Override
    public void recolor(Color color) {
        setColor(color);
        ellipse.setFill(color);
    }

    @Override
    public void draw() {
        new DrawEllipseStrategy().draw(getParent(), this);
    }

    @Override
    public void remove() {
        deselect();
        getParent().getChildren().remove(ellipse);
    }

    @Override
    public void drag(double x, double y) {
        setX(getX() + x);
        setY(getY() + y);

        if (isSelected()) {
            getSelectionBox().drag(x, y);
        }

        ellipse.setTranslateX(ellipse.getTranslateX() + x);
        ellipse.setTranslateY(ellipse.getTranslateY() + y);
    }

    @Override
    public void resize(double width, double height) {
        new ResizeEllipseStrategy().resize(getParent(), this, width, height);
    }

    @Override
    public void resizeWidth(double width) {
        new ResizeEllipseStrategy().resize(getParent(), this, width, getHeight());
    }

    @Override
    public void resizeHeight(double height) {
        new ResizeEllipseStrategy().resize(getParent(), this, getWidth(), height);
    }
    @Override
    public String accept(CanvasElementVisitor visitor){
        return visitor.visitCircle(this);
    }
}
