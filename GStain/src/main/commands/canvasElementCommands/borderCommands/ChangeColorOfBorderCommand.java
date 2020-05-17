package main.commands.canvasElementCommands.borderCommands;

import javafx.scene.paint.Color;
import main.canvasElements.CanvasElement;
import main.canvasElements.decorators.border.BorderDecorator;
import main.commands.Command;
import main.strategies.canvasElementStrategies.color.ChangeElementBorderColorStrategy;

public class ChangeColorOfBorderCommand implements Command {
    private final BorderDecorator element;
    private final Color color;
    private final Color prev_color;

    public ChangeColorOfBorderCommand(BorderDecorator element, Color color) {
        this.element = element;
        this.color = color;
        prev_color = element.getBorderColor();
    }

    @Override
    public void execute() {
        new ChangeElementBorderColorStrategy().changeColor(element, color);
    }

    @Override
    public void undo() {
        new ChangeElementBorderColorStrategy().changeColor(element, prev_color);
    }

    @Override
    public void redo() {
        execute();
    }
}
