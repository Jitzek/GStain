package main.commands.shapeCommands.rectangleCommands;

import main.Canvas;
import main.canvasElements.shapes.Rectangle;
import main.commands.Command;
import main.strategies.canvasElementStrategies.DrawRectangleStrategy;
import main.strategies.canvasElementStrategies.RemoveElementStrategy;

public class DrawRectangleCommand implements Command {
    private final Rectangle rectangle;
    private final Canvas canvas;

    public DrawRectangleCommand(Canvas canvas, Rectangle rectangle) {
        this.rectangle = rectangle;
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        // Add Rectangle to Canvas Logic
        canvas.addCanvasElement(rectangle);
        new DrawRectangleStrategy().draw(canvas, rectangle);
    }

    @Override
    public void undo() {
        // Remove Rectangle from Canvas Logic
        new RemoveElementStrategy().remove(canvas, rectangle);
    }

    @Override
    public void redo() {
        // Add Rectangle to Canvas Logic
        execute();
    }
}
