package main.strategies.canvasElementStrategies;

import main.Canvas;
import main.canvasElements.CanvasElement;

public interface PositionElementStrategy {
    void position(Canvas canvas, CanvasElement element, double fromX, double fromY, double toX, double toY);
}