package main.commands.shapeCommands;

import main.Canvas;
import main.canvasElements.shapes.Shape;
import main.commands.Command;
import main.strategies.canvasElementStrategies.DragShapeStrategy;

import java.util.ArrayList;
import java.util.Arrays;

public class DragShapeCommand implements Command {
    private final Canvas canvas;
    private final ArrayList<Shape> shapes;
    private final double x;
    private final double y;
    private final double prev_x;
    private final double prev_y;

    public DragShapeCommand(Canvas canvas, ArrayList<Shape> shapes, double x, double y) {
        this.canvas = canvas;
        this.shapes = shapes;
        this.x = x;
        this.y = y;
        prev_x = x * -1;
        prev_y = y * -1;
    }

    public DragShapeCommand(Canvas canvas, Shape shape, double x, double y) {
        this.canvas = canvas;
        (this.shapes = new ArrayList<>()).add(shape);
        this.x = x;
        this.y = y;
        prev_x = x * -1;
        prev_y = y * -1;
    }

    @Override
    public void execute() {
        for (Shape shape : shapes) {
            System.out.println("Dragging Shape " + shape.getUUID() + " to x:" + shape.getX() + x + " y:" + shape.getY() + y);
            DragShapeStrategy strategy = new DragShapeStrategy();
            strategy.drag(canvas, shape, x, y);
        }
    }

    @Override
    public void undo() {
        for (Shape shape : shapes) {
            System.out.println("Undoing Dragging Shape " + shape.getUUID());
            DragShapeStrategy strategy = new DragShapeStrategy();
            strategy.drag(canvas, shape, prev_x, prev_y);
        }
    }

    @Override
    public void redo() {
        for (Shape shape : shapes) {
            System.out.println("Redoing Dragging Shape " + shape.getUUID());
            DragShapeStrategy strategy = new DragShapeStrategy();
            strategy.drag(canvas, shape, x, y);
        }
    }
}
