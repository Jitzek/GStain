package main.strategies.canvasElementStrategies.size;

import main.Canvas;
import main.canvasElements.CanvasElement;

import java.util.ArrayList;

public interface ResizeElementStrategy {
    void resize(Canvas canvas, CanvasElement element, double width, double height);
}
