package main.commands.canvasElementCommands;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.commands.Command;
import main.strategies.canvasElementStrategies.draw.DrawCompoundStrategy;
import main.strategies.canvasElementStrategies.draw.DrawElementStrategy;
import main.strategies.canvasElementStrategies.remove.RemoveElementStrategy;

import java.util.ArrayList;

public class RemoveElementsCommand implements Command {
    private final ArrayList<CanvasElement> elements;
    private final Canvas canvas;

    public RemoveElementsCommand(Canvas canvas, ArrayList<CanvasElement> elements) {
        this.elements = elements;
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        for (CanvasElement element : new ArrayList<>(elements)) {
            new RemoveElementStrategy().remove(canvas, element);
        }
    }

    @Override
    public void undo() {
        for (CanvasElement element : elements) {
            canvas.addCanvasElement(element);
            element.draw();
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
