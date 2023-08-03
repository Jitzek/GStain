package main.commands.canvasElementCommands;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.commands.Command;
import main.canvasElementStrategies.remove.RemoveElementStrategy;

import java.util.ArrayList;

public class DecorateElementsCommand implements Command {
    private final Canvas canvas;
    private final ArrayList<CanvasElement> elements;
    private final ArrayList<CanvasElement> decoratedElements;

    public DecorateElementsCommand(Canvas canvas, ArrayList<CanvasElement> elements, ArrayList<CanvasElement> decoratedElements) {
        this.canvas = canvas;
        this.elements = elements;
        this.decoratedElements = decoratedElements;
    }

    @Override
    public void execute() {
        // Remove elements
        for (CanvasElement element : new ArrayList<>(elements)) {
            new RemoveElementStrategy().remove(canvas, element);
        }

        // Draw decorated elements
        for (CanvasElement element : new ArrayList<>(decoratedElements)) {

        }
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }
}
