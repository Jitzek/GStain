package main.commands;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.strategies.canvasElementStrategies.*;

import java.util.ArrayList;
import java.util.Collections;

public class DrawElementsCommand implements Command {
    private final ArrayList<CanvasElement> elements;
    private final Canvas canvas;

    public DrawElementsCommand(Canvas canvas, ArrayList<CanvasElement> elements) {
        this.elements = elements;
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        for (CanvasElement element : elements) {
            canvas.addCanvasElement(element);
            switch (element.getClass().getSimpleName().toUpperCase()) {
                case "COMPOUND":
                    new DrawCompoundStrategy().draw(canvas, (Compound) element);
                    break;
                case "RECTANGLE":
                    new DrawRectangleStrategy().draw(canvas, element);
                    break;
                case "ELLIPSE":
                    new DrawEllipseStrategy().draw(canvas, element);
                    break;
            }
        }
    }

    @Override
    public void undo() {
        for (CanvasElement element : new ArrayList<>(elements)) {
            new RemoveElementStrategy().remove(canvas, element);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
