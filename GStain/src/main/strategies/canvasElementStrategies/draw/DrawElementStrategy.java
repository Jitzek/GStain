package main.strategies.canvasElementStrategies.draw;

import main.Canvas;
import main.canvasElements.CanvasElement;

public interface DrawElementStrategy {
    void draw(Canvas canvas, CanvasElement element);
}
