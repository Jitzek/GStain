package main.strategies.canvasElementStrategies;

import main.CC;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

public class DrawCompoundStrategy {
    public void draw(Canvas canvas, Compound compound) {
        for (CanvasElement element : compound.getChildren()) {
            switch (element.getClass().getSimpleName().toUpperCase()) {
                case "COMPOUND":
                    // Add Compound to Canvas Logic
                    new DrawCompoundStrategy().draw(canvas, (Compound) element);
                    break;
                case "RECTANGLE":
                    // Add Rectangle to Canvas Logic
                    new DrawRectangleStrategy().draw(canvas, element);
                    break;
                case "ELLIPSE":
                    // Add Ellipse to Canvas Logic
                    new DrawEllipseStrategy().draw(canvas, element);
                    break;
            }
        }
    }
}
