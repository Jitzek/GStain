package main.strategies.canvasElementStrategies;

import main.Canvas;
import main.canvasElements.CanvasElement;

public interface DragElementStrategy {
    void drag(Canvas canvas, CanvasElement element, double x, double y);
}
