package main.commands.canvasElementCommands.compoundCommands;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.commands.Command;
import main.strategies.canvasElementStrategies.draw.DrawCompoundStrategy;
import main.strategies.canvasElementStrategies.draw.DrawElementStrategy;

import java.util.ArrayList;

public class RemoveCompoundCommand implements Command {
    private final Canvas canvas;
    private final Compound compound;
    private final int index;
    private final ArrayList<Integer> indexes = new ArrayList<>();

    public RemoveCompoundCommand(Canvas canvas, Compound compound) {
        this.compound = compound;
        this.canvas = canvas;
        index = canvas.getIndexOfElement(compound, false);
        for (CanvasElement child : compound.getChildren()) {
            indexes.add(canvas.getIndexOfElement(child, true));
        }
    }

    @Override
    public void execute() {
    }

    @Override
    public void undo() {
        canvas.addCanvasElement(compound);
        for (int i = 0; i < compound.getChildren().size(); i++) {
            canvas.addCanvasElement(compound.getChildren().get(i));
            compound.getChildren().get(i).draw();
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
