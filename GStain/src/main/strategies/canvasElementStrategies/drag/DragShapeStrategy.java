package main.strategies.canvasElementStrategies.drag;

import javafx.scene.Node;
import javafx.scene.shape.Path;
import main.Canvas;
import main.canvasElements.CanvasElement;

public class DragShapeStrategy implements DragElementStrategy {
    @Override
    public void drag(Canvas canvas, CanvasElement element, double x, double y) {
        element.drag(x, y);
    }
}
