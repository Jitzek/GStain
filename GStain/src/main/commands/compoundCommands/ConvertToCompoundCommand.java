package main.commands.compoundCommands;

import main.Canvas;
import main.CC;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.commands.Command;
import main.factories.CanvasElementCloneFactory;
import main.strategies.canvasElementStrategies.*;

import java.lang.reflect.Array;
import java.util.*;

public class ConvertToCompoundCommand implements Command {
    private final Canvas canvas;
    private final Compound compound = new Compound();
    private int index = -1;

    public ConvertToCompoundCommand(Canvas canvas, ArrayList<CanvasElement> elements) {
        this.canvas = canvas;
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
        new DrawCompoundStrategy().draw(canvas, compound);
    }

    @Override
    public void undo() {
        ArrayList<CanvasElement> toBeDrawn = new ArrayList<>(compound.getChildren());
        new RemoveElementStrategy().remove(canvas, compound);
        for (CanvasElement element : toBeDrawn) {
            canvas.addCanvasElement(element);
            switch (element.getClass().getSimpleName().toUpperCase()) {
                case "COMPOUND":
                    new DrawCompoundStrategy().draw(canvas, (Compound) element);
                    break;
                case "RECTANGLE":
                    new DrawRectangleStrategy().draw(canvas, element);
                    break;
                case "ELLIPSE":
                    new DrawEllipseStrategy().draw(canvas, element);
                    break;
            }
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
