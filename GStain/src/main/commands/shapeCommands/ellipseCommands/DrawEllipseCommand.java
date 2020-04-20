package main.commands.shapeCommands.ellipseCommands;

import main.Canvas;
import main.canvasElements.shapes.Ellipse;
import main.commands.Command;
import main.strategies.canvasElementStrategies.DrawEllipseStrategy;
import main.strategies.canvasElementStrategies.RemoveElementStrategy;

public class DrawEllipseCommand implements Command {
    private final Ellipse ellipse;
    private final Canvas canvas;

    public DrawEllipseCommand(Canvas canvas, Ellipse ellipse) {
        this.ellipse = ellipse;
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        // Add Ellipse to Canvas Logic
        canvas.addCanvasElement(ellipse);
        new DrawEllipseStrategy().draw(canvas, ellipse);
    }

    @Override
    public void undo() {
        // Remove Ellipse from Canvas Logic
        new RemoveElementStrategy().remove(canvas, ellipse);
    }

    @Override
    public void redo() {
        // Add Ellipse to Canvas Logic
        execute();
    }
}
