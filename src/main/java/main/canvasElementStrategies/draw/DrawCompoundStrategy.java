package main.canvasElementStrategies.draw;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

public class DrawCompoundStrategy implements DrawElementStrategy {
    @Override
    public void draw(Canvas canvas, CanvasElement element) {
        for (CanvasElement child : ((Compound) element).getChildren()) {
            child.draw();
        }
    }
}
