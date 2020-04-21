package main.commands.compoundCommands;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.commands.Command;
import main.strategies.canvasElementStrategies.DrawCompoundStrategy;
import main.strategies.canvasElementStrategies.DrawEllipseStrategy;
import main.strategies.canvasElementStrategies.DrawRectangleStrategy;
import main.strategies.canvasElementStrategies.RemoveElementStrategy;

import java.util.ArrayList;

public class ConvertToElementsCommand implements Command {
    private final Canvas canvas;
    private final ArrayList<Compound> compounds;
    private final ArrayList<Integer> indexes = new ArrayList<>();
    private final ArrayList<CanvasElement> elements = new ArrayList<>();

    public ConvertToElementsCommand(Canvas canvas, ArrayList<Compound> compounds) {
        this.canvas = canvas;
        this.compounds = compounds;
        for (Compound compound : compounds) {
            indexes.add(canvas.getIndexOfElement(compound, false));
        }
    }

    @Override
    public void execute() {
        int i = 0;
        for (Compound compound : compounds) {
            new RemoveElementStrategy().remove(canvas, compound);
            for (CanvasElement element : new ArrayList<>(compound.getChildren())) {
                canvas.insertCanvasElement(indexes.get(i), element);
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
                elements.add(element);
            }
            i++;
        }
    }

    @Override
    public void undo() {
        for (CanvasElement element : new ArrayList<>(elements)) {
            new RemoveElementStrategy().remove(canvas, element);
        }
        for (int i = 0; i < compounds.size(); i++) {
            canvas.insertCanvasElement(indexes.get(i), compounds.get(i));
            new DrawCompoundStrategy().draw(canvas, compounds.get(i));
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
