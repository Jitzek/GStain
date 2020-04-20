package main.commands;

import main.CC;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.strategies.canvasElementStrategies.DragCompoundStrategy;
import main.strategies.canvasElementStrategies.DragElementsStrategy;
import main.strategies.canvasElementStrategies.DragShapeStrategy;

import java.util.ArrayList;

public class DragElementsCommand implements Command {
    private final Canvas canvas;
    private final ArrayList<CanvasElement> elements;
    private final double x;
    private final double y;
    private final double prev_x;
    private final double prev_y;

    public DragElementsCommand(Canvas canvas, ArrayList<CanvasElement> elements, double x, double y) {
        this.canvas = canvas;
        this.elements = elements;
        this.x = x;
        this.y = y;
        prev_x = x * -1;
        prev_y = y * -1;
    }

    @Override
    public void execute() {
        new DragElementsStrategy().drag(canvas, new ArrayList<>(elements), x, y);
    }

    @Override
    public void undo() {
        new DragElementsStrategy().drag(canvas, new ArrayList<>(elements), prev_x, prev_y);
    }

    @Override
    public void redo() {
        execute();
    }
}
