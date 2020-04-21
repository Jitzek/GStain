package main.strategies.canvasElementStrategies.select;

import main.Canvas;
import main.canvasElements.CanvasElement;

public interface SelectElementStrategy {
    void select(Canvas canvas, CanvasElement element);
}
