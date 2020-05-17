package main.commands.canvasElementCommands;

import javafx.scene.paint.Color;
import main.canvasElements.CanvasElement;
import main.commands.Command;
import main.strategies.canvasElementStrategies.color.ChangeElementFillColorStrategy;

import java.util.ArrayList;

public class ChangeFillColorOfElementsCommand implements Command {
    private final ArrayList<CanvasElement> elements;
    private final ArrayList<Color> prev_colors = new ArrayList<>();
    private final Color new_color;

    public ChangeFillColorOfElementsCommand(ArrayList<CanvasElement> elements, Color color) {
        this.elements = elements;
        for (CanvasElement element : elements) {
            prev_colors.add(element.getColor());
        }
        new_color = color;
    }

    @Override
    public void execute() {
        for (CanvasElement element : elements) {
            new ChangeElementFillColorStrategy().changeColor(element, new_color);
        }
    }

    @Override
    public void undo() {
        for (int i = 0; i < elements.size(); i++) {
            new ChangeElementFillColorStrategy().changeColor(elements.get(i), prev_colors.get(i));
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
