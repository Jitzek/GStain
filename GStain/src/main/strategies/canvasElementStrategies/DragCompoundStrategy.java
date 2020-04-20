package main.strategies.canvasElementStrategies;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

public class DragCompoundStrategy implements DragElementStrategy {
    @Override
    public void drag(Canvas canvas, CanvasElement element, double x, double y) {
        for (CanvasElement ce : ((Compound) element).getChildren()) {
            if (ce instanceof Compound) {
                new DragCompoundStrategy().drag(canvas, ce, x, y);
            }
            else {
                new DragShapeStrategy().drag(canvas, ce, x, y);
            }
        }
    }
}
