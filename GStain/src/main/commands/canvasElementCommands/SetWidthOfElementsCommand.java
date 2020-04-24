package main.commands.canvasElementCommands;

import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.shapes.Shape;
import main.commands.Command;
import main.strategies.canvasElementStrategies.size.ResizeElementStrategy;

import java.util.ArrayList;

public class SetWidthOfElementsCommand implements Command {
    private final ArrayList<CanvasElement> elements;
    private final double width;
    private final ArrayList<Double> prev_widths = new ArrayList<>();

    // TODO find a better (more cleaner) way to handle compounds
    private final ArrayList<Command> compoundCommands = new ArrayList<>();

    public SetWidthOfElementsCommand(ArrayList<CanvasElement> elements, double width) {
        this.elements = elements;
        this.width = width;
        for (CanvasElement element : elements) {
            if (element instanceof Compound) compoundCommands.add(new SetWidthOfElementsCommand(((Compound) element).getChildren(), width));
            else prev_widths.add(element.getWidth());
        }
    }

    @Override
    public void execute() {
        for (CanvasElement element : elements) {
            if (element instanceof Command) continue;
            element.resizeWidth(width);
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
            element.resizeWidth(prev_widths.get(i));
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
