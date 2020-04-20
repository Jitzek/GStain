package main.strategies.canvasElementStrategies;

import javafx.scene.shape.Rectangle;
import main.Canvas;
import main.canvasElements.CanvasElement;

public class DrawRectangleStrategy implements DrawElementStrategy {
    @Override
    public void draw(Canvas canvas, CanvasElement element) {
        element.select();

        // Draw Rectangle logic (Paint on GUI)
        Rectangle rectangle = new Rectangle(element.getX() - element.getWidth()/2, element.getY() - element.getHeight()/2, element.getWidth(), element.getHeight());
        rectangle.setFill(element.getColor());

        canvas.getChildren().add(canvas.getIndexOfElement(element, true), rectangle);
    }
}
