package main.strategies.canvasElementStrategies;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

public interface DrawElementStrategy {
    void draw(Canvas canvas, CanvasElement element);
}
