package main.strategies.canvasElementStrategies.deselect;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

public class DeselectCompoundStrategy {
    public void deselect(Canvas canvas, Compound compound) {
        // TODO Remove selectionBox

        compound.setSelectionStyle(null);

        // Deselect all other elements
        for (CanvasElement child : compound.getChildren()) {
            child.deselect();
        }
    }
}
