package main.strategies.canvasElementStrategies.size;

import javafx.collections.ObservableList;
import javafx.scene.shape.*;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.shapes.Rectangle;

public class ResizeRectangleStrategy implements ResizeElementStrategy {
    @Override
    public void resize(Canvas canvas, CanvasElement element, double width, double height) {
        element.deselect();

        element.setWidth(width);
        element.setHeight(height);

        // Draw Rectangle logic (Paint on GUI)
        // TODO find way to change size of existing shape instead of replacing it
        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(element.getX() - element.getWidth()/2, element.getY() - element.getHeight()/2, element.getWidth(), element.getHeight());
        rectangle.setFill(element.getColor());

        canvas.getChildren().set(canvas.getIndexOfElement(element, true), rectangle);

        ((Rectangle) element).setRectangle(rectangle);

        element.select();
    }
}
