package main.canvasElementStrategies.remove;

import main.Canvas;
import main.canvasElements.CanvasElement;

public class RemoveElementStrategy {
    public void remove(Canvas canvas, CanvasElement element) {
        element.deselect();

        element.remove();

        canvas.removeCanvasElement(element, false);
    }
}
