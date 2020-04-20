package main.strategies.canvasElementStrategies;

import javafx.scene.Node;
import main.Canvas;
import main.canvasElements.CanvasElement;

public class DragShapeStrategy implements DragElementStrategy {
    @Override
    public void drag(Canvas canvas, CanvasElement element, double x, double y) {
        Node shape = canvas.getChildren().get(canvas.getIndexOfElement(element, true));

        element.setX(element.getX() + x);
        element.setY(element.getY() + y);

        shape.setTranslateX(shape.getTranslateX() + x);
        shape.setTranslateY(shape.getTranslateY() + y);
    }
}
