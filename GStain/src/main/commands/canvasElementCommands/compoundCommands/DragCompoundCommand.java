package main.commands.canvasElementCommands.compoundCommands;

import main.Canvas;
import main.canvasElements.Compound;
import main.commands.Command;
import main.strategies.canvasElementStrategies.drag.DragCompoundStrategy;

public class DragCompoundCommand implements Command {
    private final Canvas canvas;
    private final Compound compound;
    private final double x;
    private final double y;
    private final double prev_x;
    private final double prev_y;

    public DragCompoundCommand(Canvas canvas, Compound compound, double x, double y) {
        this.canvas = canvas;
        this.compound = compound;
        this.x = x;
        this.y = y;
        prev_x = x * -1;
        prev_y = y * -1;
    }

    @Override
    public void execute() {
        new DragCompoundStrategy().drag(canvas, compound, x, y);
    }

    @Override
    public void undo() {
        new DragCompoundStrategy().drag(canvas, compound, prev_x, prev_y);
    }

    @Override
    public void redo() {
        new DragCompoundStrategy().drag(canvas, compound, x, y);
    }
}
