package main.commands.canvasElementCommands.compoundCommands;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.commands.Command;
import main.strategies.canvasElementStrategies.remove.RemoveElementStrategy;

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
        for (Compound compound : compounds) {
            new RemoveElementStrategy().remove(canvas, compound);
            for (CanvasElement element : new ArrayList<>(compound.getChildren())) {
                element.draw();
                elements.add(element);
            }
        }
    }

    @Override
    public void undo() {
        for (CanvasElement element : new ArrayList<>(elements)) {
            new RemoveElementStrategy().remove(canvas, element);
        }
        for (int i = 0; i < compounds.size(); i++) {
            canvas.insertCanvasElement(indexes.get(i), compounds.get(i));
            compounds.get(i).draw();
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
