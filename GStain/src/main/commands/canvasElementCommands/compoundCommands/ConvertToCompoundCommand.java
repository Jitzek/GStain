package main.commands.canvasElementCommands.compoundCommands;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.commands.Command;
import main.strategies.canvasElementStrategies.draw.DrawCompoundStrategy;
import main.strategies.canvasElementStrategies.draw.DrawElementStrategy;
import main.strategies.canvasElementStrategies.remove.RemoveElementStrategy;

import java.util.*;

public class ConvertToCompoundCommand implements Command {
    private final Canvas canvas;
    private final Compound compound;
    private int index = -1;

    public ConvertToCompoundCommand(Canvas canvas, ArrayList<CanvasElement> elements) {
        this.canvas = canvas;
        compound = new Compound(canvas);
        for (CanvasElement ce : new ArrayList<>(elements)) {
            int i = canvas.getIndexOfElement(ce, false);
            compound.addChild(ce);
            // Index is lowest index of elements to be converted
            if (index == -1 || index > i) index = i;
        }
    }

    @Override
    public void execute() {
        for (CanvasElement element : new ArrayList<>(compound.getChildren())) {
            new RemoveElementStrategy().remove(canvas, element);
        }
        canvas.insertCanvasElement(index, compound);
        compound.draw();
    }

    @Override
    public void undo() {
        ArrayList<CanvasElement> toBeDrawn = new ArrayList<>(compound.getChildren());
        new RemoveElementStrategy().remove(canvas, compound);
        for (CanvasElement element : toBeDrawn) {
            canvas.addCanvasElement(element);
            element.draw();
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
