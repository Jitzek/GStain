package main.strategies.canvasElementStrategies.size;

import javafx.scene.shape.Ellipse;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.shapes.Rectangle;

public class ResizeEllipseStrategy implements ResizeElementStrategy {
    @Override
    public void resize(Canvas canvas, CanvasElement element, double width, double height) {
        element.deselect();

        element.setWidth(width);
        element.setHeight(height);

        // Draw Ellipse logic (Paint on GUI)
        // TODO find way to change size of existing shape instead of replacing it
        Ellipse ellipse = new javafx.scene.shape.Ellipse(element.getX(), element.getY(), element.getWidth() / 2, element.getHeight() / 2);
        ellipse.setFill(element.getColor());

        canvas.getChildren().set(canvas.getIndexOfElement(element, true), ellipse);

        // Store Drawn Ellipse into it's container
        ((main.canvasElements.shapes.Ellipse) element).setEllipse(ellipse);

        element.select();
    }
}
