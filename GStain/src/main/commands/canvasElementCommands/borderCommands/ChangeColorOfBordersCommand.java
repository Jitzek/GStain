package main.commands.canvasElementCommands.borderCommands;

import javafx.scene.paint.Color;
import main.canvasElements.decorators.border.BorderDecorator;
import main.commands.Command;

import java.util.ArrayList;

public class ChangeColorOfBordersCommand implements Command {
    private final ArrayList<BorderDecorator> elements;
    private final Color color;

    private final ArrayList<ChangeColorOfBorderCommand> commands = new ArrayList<>();

    public ChangeColorOfBordersCommand(ArrayList<BorderDecorator> elements, Color color) {
        this.elements = elements;
        this.color = color;
    }

    @Override
    public void execute() {
        for (BorderDecorator element : elements) {
            commands.add(new ChangeColorOfBorderCommand(element, color));
        }
        for (ChangeColorOfBorderCommand command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        for (ChangeColorOfBorderCommand command : commands) {
            command.undo();
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
