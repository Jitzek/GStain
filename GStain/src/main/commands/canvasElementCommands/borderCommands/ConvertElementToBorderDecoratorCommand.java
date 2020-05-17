package main.commands.canvasElementCommands.borderCommands;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.decorators.border.BorderDecorator;
import main.commands.Command;
import main.factories.DecoratorFactory;

public class ConvertElementToBorderDecoratorCommand implements Command {
    private final Canvas canvas;
    private BorderDecorator decorator;
    private final CanvasElement element;
    private final int index;

    public ConvertElementToBorderDecoratorCommand(CanvasElement element) {
        canvas = element.getParent();
        this.element = element;
        index = element.getParent().getIndexOfElement(element, false);
    }

    @Override
    public void execute() {
        // Determine whether element needs to be converted
        if (element instanceof BorderDecorator) return;
        decorator = decorator == null ? DecoratorFactory.getDecorator(element, "BORDER") : decorator;

        element.remove();
        canvas.getCanvasElements().set(index, decorator);
        decorator.draw();
    }

    @Override
    public void undo() {
        if (decorator == null) return;
        decorator.remove();
        canvas.getCanvasElements().set(index, element);
        element.draw();
    }

    @Override
    public void redo() {
        // FIXME Converted element != original Converted element (execute makes new BorderDecorator instance)
        execute();
    }
}
