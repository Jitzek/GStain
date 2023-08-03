package main.commands.canvasElementCommands.borderCommands;

import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.commands.Command;

import java.util.ArrayList;

public class ChangeThicknessOfBordersCommand implements Command {
    private final ArrayList<CanvasElement> elements;
    private final double thickness;

    private ArrayList<Command> commands = new ArrayList<>();

    private final ArrayList<Integer> indexes = new ArrayList<>();

    public ChangeThicknessOfBordersCommand(ArrayList<CanvasElement> elements, double thickness) {
        this.elements = elements;
        this.thickness = thickness;
        for (CanvasElement element : elements) {
            indexes.add(element.getParent().getIndexOfElement(element, false));
        }
    }

    @Override
    public void execute() {
        // Prepare commands (Compound- and ShapeBorder Thickness commands)
        commands = new ArrayList<>();
        for (CanvasElement element : elements) {
            if (element instanceof Compound)
                commands.add(new ChangeThicknessOfCompoundBorderCommand((Compound) element, thickness));
            else commands.add(new ChangeThicknessOfShapeBorderCommand(element, thickness));
        }
        // Execute prepared commands
        for (Command command : commands) command.execute();

        // Update elements list
        for (int i = 0; i < elements.size(); i++) {
            elements.set(i, elements.get(i).getParent().getCanvasElementAt(indexes.get(i), false, false));
        }
    }

    @Override
    public void undo() {
        // Undo commands
        for (Command command : commands) command.undo();

        // Update elements list
        for (int i = 0; i < elements.size(); i++) {
            elements.set(i, elements.get(i).getParent().getCanvasElementAt(indexes.get(i), false, false));
        }
    }

    @Override
    public void redo() {
        for (Command command : commands) command.redo();
        // Update elements list
        for (int i = 0; i < elements.size(); i++) {
            elements.set(i, elements.get(i).getParent().getCanvasElementAt(indexes.get(i), false, false));
        }
    }
}
