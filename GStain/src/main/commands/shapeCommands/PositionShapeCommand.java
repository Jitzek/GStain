package main.commands.shapeCommands;

import main.Canvas;
import main.canvasElements.shapes.Shape;
import main.commands.Command;
import main.strategies.canvasElementStrategies.DragShapeStrategy;
import main.strategies.canvasElementStrategies.PositionShapeStrategy;

public class PositionShapeCommand implements Command {
    private final Canvas canvas;
    private final Shape shape;
    private final double x;
    private final double y;
    private final double prev_x;
    private final double prev_y;

    public PositionShapeCommand(Canvas canvas, Shape shape, double x, double y) {
        this.canvas = canvas;
        this.shape = shape;
        this.x = x;
        this.y = y;
        prev_x = shape.getX();
        prev_y = shape.getY();
    }

    @Override
    public void execute() {
        PositionShapeStrategy strategy = new PositionShapeStrategy();
        strategy.position(canvas, shape, prev_x, prev_y, x, y);
    }

    @Override
    public void undo() {
        PositionShapeStrategy strategy = new PositionShapeStrategy();
        strategy.position(canvas, shape, x, y, prev_x, prev_y);
    }

    @Override
    public void redo() {
        PositionShapeStrategy strategy = new PositionShapeStrategy();
        strategy.position(canvas, shape, prev_x, prev_y, x, y);
    }
}
