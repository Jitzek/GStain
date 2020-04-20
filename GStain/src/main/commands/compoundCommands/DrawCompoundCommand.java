package main.commands.compoundCommands;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.shapes.Rectangle;
import main.commands.Command;
import main.strategies.canvasElementStrategies.*;

public class DrawCompoundCommand implements Command {
    private final Compound compound;
    private final Canvas canvas;
    private final int index;

    public DrawCompoundCommand(Canvas canvas, Compound compound) {
        this.compound = compound;
        this.canvas = canvas;
        index = canvas.getIndexOfElement(compound, true);
    }

    @Override
    public void execute() {
        canvas.addCanvasElement(compound);
        new DrawCompoundStrategy().draw(canvas, compound);
    }

    @Override
    public void undo() {
        new RemoveElementStrategy().remove(canvas, canvas.getCanvasElementAt(index, true, true));
    }

    @Override
    public void redo() {
        // Add Compound to Canvas Logic
        execute();
    }
}
