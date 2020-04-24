package main.strategies.canvasElementStrategies.remove;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

import java.util.ArrayList;

public class RemoveElementStrategy {
    public void remove(Canvas canvas, CanvasElement element) {
        element.deselect();
        if ("COMPOUND".equals(element.getClass().getSimpleName().toUpperCase())) {
            // Compound requires different logic
            removeCompound(canvas, (Compound) element, 0);
        } else {
            // Remove Shape logic (Remove from GUI)
            element.remove();
        }
        canvas.removeCanvasElement(element, false);
    }

    /*
        Back-end information is separated from front-end (2 separate lists, Canvas->canvasElements & Canvas->children)
        where canvasElements serve as containers pertaining the necessary information for an element to be drawn
        and children server as the GUI element drawn for the user

        e.g.
        canvasElements:
        Compound
            Rectangle
            Compound
                Compound
                    Rectangle
                    Ellipse
                Rectangle
            Compound
                Ellipse
                Ellipse
            Rectangle

        would translate to children as:
        Rectangle
        Rectangle
        Ellipse
        Rectangle
        Ellipse
        Rectangle
     */
    /**
     * Recursively removes all (GUI) elements from Canvas
     * @param canvas Canvas where the compound should be removed from
     * @param compound Compound which should be removed
     * @param pointer Assists in pointing to correct index of element in children
     * @return pointer, for recursion
     */
    private int removeCompound(Canvas canvas, Compound compound, int pointer) {
        ArrayList<CanvasElement> elements = new ArrayList<>(compound.getChildren());
        for (CanvasElement element : elements) {
            element.deselect();
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
