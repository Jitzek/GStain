package main.canvasElementStrategies.size;

import main.Canvas;
import main.canvasElements.CanvasElement;

public interface ResizeElementStrategy {
    void resize(Canvas canvas, CanvasElement element, double width, double height);
}
