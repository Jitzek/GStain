package main.commands.canvasElementCommands;

import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.commands.Command;

import java.util.ArrayList;

public class SetHeightOfElementsCommand implements Command {
    private final ArrayList<CanvasElement> elements;
    private final double height;
    private final ArrayList<Double> prev_heights = new ArrayList<>();

    // TODO find a better (more cleaner) way to handle compounds
    private final ArrayList<Command> compoundCommands = new ArrayList<>();

    public SetHeightOfElementsCommand(ArrayList<CanvasElement> elements, double height) {
        this.elements = elements;
        this.height = height;
        for (CanvasElement element : elements) {
            if (element instanceof Compound) compoundCommands.add(new SetHeightOfElementsCommand(((Compound) element).getChildren(), height));
            else prev_heights.add(element.getHeight());
        }
    }

    @Override
    public void execute() {
        for (CanvasElement element : elements) {
            if (element instanceof Command) continue;
            element.resizeHeight(height);
        }
        for (Command command : compoundCommands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        int i = 0;
        for (CanvasElement element : elements) {
            if (element instanceof Compound) continue;
            element.resizeHeight(prev_heights.get(i));
            i++;
        }
        for (Command command : compoundCommands) {
            command.undo();
        }
    }

    @Override
    public void redo() {
        execute();
        for (Command command : compoundCommands) {
            command.redo();
        }
    }
}
