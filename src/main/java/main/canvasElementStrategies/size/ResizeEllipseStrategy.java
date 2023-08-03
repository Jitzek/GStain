package main.canvasElementStrategies.size;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.shapes.Ellipse;

public class ResizeEllipseStrategy implements ResizeElementStrategy {
    @Override
    public void resize(Canvas canvas, CanvasElement element, double width, double height) {
        element.deselect();

        element.setWidth(width);
        element.setHeight(height);

        // Draw Rectangle logic (Paint on GUI)
        javafx.scene.shape.Ellipse ellipse = new javafx.scene.shape.Ellipse(element.getX(), element.getY(), element.getWidth() / 2, element.getHeight() / 2);
        ellipse.setFill(element.getColor());

        canvas.getChildren().set(canvas.getChildren().indexOf(((Ellipse) element).getEllipse()), ellipse);

        ((Ellipse) element).setEllipse(ellipse);

        element.select();
    }
}
