package main.commands.canvasElementCommands;

import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.commands.Command;

import java.util.ArrayList;

public class ResizeElementsCommand implements Command {
    private final ArrayList<CanvasElement> elements;
    private final double added_width;
    private final double added_height;
    private final ArrayList<Double> prev_widths = new ArrayList<>();
    private final ArrayList<Double> prev_heights = new ArrayList<>();

    // TODO find a better (more cleaner) way to handle compounds
    private final ArrayList<Command> compoundCommands = new ArrayList<>();

    public ResizeElementsCommand(ArrayList<CanvasElement> elements, double added_width, double added_height) {
        this.elements = elements;
        this.added_width = added_width;
        this.added_height = added_height;
        for (CanvasElement element : elements) {
            if (element instanceof Compound) compoundCommands.add(new ResizeElementsCommand(((Compound) element).getChildren(), added_width, added_height));
            else {
                prev_widths.add(element.getWidth());
                prev_heights.add(element.getHeight());
            }
        }
    }

    @Override
    public void execute() {
        for (CanvasElement element : elements) {
            element.resizeWidth(element.getWidth() + added_width);
            element.resizeHeight(element.getHeight() + added_height);
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
    }
}
