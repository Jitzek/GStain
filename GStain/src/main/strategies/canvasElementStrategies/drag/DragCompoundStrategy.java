package main.strategies.canvasElementStrategies.drag;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

public class DragCompoundStrategy implements DragElementStrategy {
    @Override
    public void drag(Canvas canvas, CanvasElement element, double x, double y) {
        element.drag(x, y);
    }
}
