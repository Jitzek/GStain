package main.strategies.canvasElementStrategies;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

import java.util.ArrayList;

public class DragElementsStrategy {
    public void drag(Canvas canvas, ArrayList<CanvasElement> elements, double x, double y) {
        for (CanvasElement element : elements) {
            if ("COMPOUND".equals(element.getClass().getSimpleName().toUpperCase())) {
                new DragCompoundStrategy().drag(canvas, element, x, y);
            } else {
                new DragShapeStrategy().drag(canvas, element, x, y);
            }
        }
    }

    /*public void drag(Canvas canvas, ArrayList<Integer> indexes, double x, double y) {
        for (int index : indexes) {
            if ("COMPOUND".equals(canvas.getCanvasElements().get(index).getClass().getSimpleName().toUpperCase())) {
                new DragCompoundStrategy().drag(canvas, canvas.getCanvasElements().get(index), x, y);
            } else {
                new DragShapeStrategy().drag(canvas, canvas.getCanvasElements().get(index), x, y);
            }
        }
    }*/
}
