package main.strategies.canvasElementStrategies.select;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

public class SelectCompoundStrategy {
    public void select(Canvas canvas, Compound compound) {
        // Draw big rectangle around all elements

        // Select all other elements
        for (CanvasElement child : compound.getChildren()) {
            child.select();
        }
    }
}
