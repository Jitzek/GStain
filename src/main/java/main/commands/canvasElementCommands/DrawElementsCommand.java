package main.commands.canvasElementCommands;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.commands.Command;
import main.canvasElementStrategies.remove.RemoveElementStrategy;

import java.util.ArrayList;

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
            element.draw();
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
