package main.strategies.canvasElementStrategies.draw;

import javafx.scene.shape.Ellipse;
import main.Canvas;
import main.canvasElements.CanvasElement;

public class DrawEllipseStrategy implements DrawElementStrategy {
    @Override
    public void draw(Canvas canvas, CanvasElement element) {
        element.select();

        // Draw Ellipse logic (Paint on GUI)
        Ellipse ellipse = new javafx.scene.shape.Ellipse(element.getX(), element.getY(), element.getWidth() / 2, element.getHeight() / 2);
        ellipse.setFill(element.getColor());

        canvas.getChildren().add(0, ellipse);

        // Store Drawn Ellipse into it's container
        ((main.canvasElements.shapes.Ellipse) element).setEllipse(ellipse);
    }
}
