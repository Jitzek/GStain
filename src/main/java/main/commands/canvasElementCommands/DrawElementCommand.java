package main.commands.canvasElementCommands;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.commands.Command;
import main.canvasElementStrategies.remove.RemoveElementStrategy;

public class DrawElementCommand implements Command {
    private final CanvasElement element;
    private final Canvas canvas;

    public DrawElementCommand(Canvas canvas, CanvasElement element) {
        this.element = element;
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        // Add Rectangle to Canvas Logic
        canvas.addCanvasElement(element);
        element.draw();
    }

    @Override
    public void undo() {
        // Remove Rectangle from Canvas Logic
        new RemoveElementStrategy().remove(canvas, element);
    }

    @Override
    public void redo() {
        // Add Rectangle to Canvas Logic
        execute();
    }
}
