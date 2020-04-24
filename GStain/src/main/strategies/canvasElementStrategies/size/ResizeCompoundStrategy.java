package main.strategies.canvasElementStrategies.size;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

public class ResizeCompoundStrategy implements ResizeElementStrategy {
    @Override
    public void resize(Canvas canvas, CanvasElement element, double width, double height) {
        for (CanvasElement child : ((Compound) element).getChildren()) {
            if (width == -1) child.resize(child.getWidth(), height);
            else if (height == -1) child.resize(width, child.getHeight());
        }
    }
}
