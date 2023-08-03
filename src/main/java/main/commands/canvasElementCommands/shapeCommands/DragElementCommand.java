package main.commands.canvasElementCommands.shapeCommands;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.commands.Command;
import main.canvasElementStrategies.drag.DragShapeStrategy;

public class DragElementCommand implements Command {
    private final Canvas canvas;
    private final CanvasElement element;
    private final double x;
    private final double y;
    private final double prev_x;
    private final double prev_y;

    public DragElementCommand(Canvas canvas, CanvasElement element, double x, double y) {
        this.canvas = canvas;
        this.element = element;
        this.x = x;
        this.y = y;
        prev_x = x * -1;
        prev_y = y * -1;
    }

    @Override
    public void execute() {
        new DragShapeStrategy().drag(canvas, element, x, y);
    }

    @Override
    public void undo() {
        new DragShapeStrategy().drag(canvas, element, prev_x, prev_y);
    }

    @Override
    public void redo() {
        new DragShapeStrategy().drag(canvas, element, x, y);
    }
}
