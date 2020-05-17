package main.strategies.canvasElementStrategies.draw;

import javafx.scene.shape.*;
import main.Canvas;
import main.canvasElements.CanvasElement;

public class DrawRectangleStrategy implements DrawElementStrategy {
    public void draw(Canvas canvas, CanvasElement element) {
        element.select();

        // Draw Rectangle logic (Paint on GUI)
        Rectangle rectangle = new Rectangle(element.getX() - element.getWidth()/2, element.getY() - element.getHeight()/2, element.getWidth(), element.getHeight());
        rectangle.setFill(element.getColor());

        canvas.getChildren().add(0, rectangle);

        // Store Drawn Rectangle into it's container
        ((main.canvasElements.shapes.Rectangle) element).setRectangle(rectangle);
    }
}
