package main.strategies.canvasElementStrategies.deselect;

import main.Canvas;
import main.canvasElements.CanvasElement;

public class DeselectElementStrategy {
    public void deselect(Canvas canvas, CanvasElement element) {
        element.deselect();
        if (element.getSelectionStyle() == null) return;
        canvas.getChildren().remove(element.getSelectionStyle());
        element.setSelectionStyle(null);
    }
}
