package main.strategies.canvasElementStrategies;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

import java.util.ArrayList;
import java.util.Collections;

public class RemoveElementStrategy {
    public void remove(Canvas canvas, CanvasElement element) {
        if ("COMPOUND".equals(element.getClass().getSimpleName().toUpperCase())) {
            removeCompound(canvas, (Compound) element, 0);
        } else {
            // Remove Shape logic (Remove from GUI)
            canvas.getChildren().remove(canvas.getIndexOfElement(element, true));
        }
        canvas.removeCanvasElement(element, false);
    }

    private int removeCompound(Canvas canvas, Compound compound, int pointer) {
        ArrayList<CanvasElement> elements = new ArrayList<>(compound.getChildren());
        for (CanvasElement element : elements) {
            if (element instanceof Compound) {
                pointer = removeCompound(canvas, (Compound) element, pointer);
            } else {
                canvas.getChildren().remove(canvas.getIndexOfElement(element, true) - pointer);
                pointer++;
            }
        }
        return pointer;
    }
}
