package main.commands.canvasElementCommands.borderCommands;

import main.CC;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.decorators.border.BorderDecorator;
import main.canvasElements.shapes.Rectangle;
import main.commands.Command;
import main.strategies.canvasElementStrategies.border.ChangeBorderThicknessOfElementStrategy;

public class ChangeThicknessOfShapeBorderCommand implements Command {
    private final Canvas canvas;
    private CanvasElement element;
    private final double thickness;

    private double prev_thickness;

    private ConvertElementToBorderDecoratorCommand conversion = null;

    private final int index;

    public ChangeThicknessOfShapeBorderCommand(CanvasElement element, double thickness) {
        canvas = element.getParent();
        this.element = element;
        this.thickness = thickness;
        index = element.getParent().getIndexOfElement(element, false);
    }

    @Override
    public void execute() {
        // Convert element to BorderDecorator if element is not already a BorderDecorator
        if (!(element instanceof BorderDecorator)) {
            if (conversion == null) {
                conversion = new ConvertElementToBorderDecoratorCommand(element);
                conversion.execute();
            } else conversion.redo();

            // Update element
            element = canvas.getCanvasElementAt(index, false, false);
        }

        // Save thickness for undo
        prev_thickness = ((BorderDecorator) element).getBorderThickness();

        // Change thickness of element
        new ChangeBorderThicknessOfElementStrategy().changeThickness(element, thickness);
    }

    @Override
    public void undo() {
        // Revert thickness of element's border to it's previous value
        new ChangeBorderThicknessOfElementStrategy().changeThickness(element, prev_thickness);

        // Undo conversion to BorderDecorator if applied
        if (conversion != null) {
            conversion.undo();

            // Update element
            element = element.getParent().getCanvasElementAt(index, false, false);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
