package main.commands.canvasElementCommands.borderCommands;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.commands.Command;
import main.factories.DecoratorFactory;

public class ConvertCompoundToBorderDecoratorCommand implements Command {
    private final Canvas canvas;
    private final Compound converted_compound;
    private final Compound compound;

    private final int index;

    public ConvertCompoundToBorderDecoratorCommand(Compound compound) {
        canvas = compound.getParent();
        this.compound = compound;
        index = canvas.getIndexOfElement(compound, false);
        converted_compound = new Compound(canvas);
        for (CanvasElement child : compound.getChildren()) {
            converted_compound.addChild(DecoratorFactory.getDecorator(child, "BORDER"));
        }
    }

    @Override
    public void execute() {
        compound.remove();
        canvas.getCanvasElements().set(index, converted_compound);
        converted_compound.draw();
    }

    @Override
    public void undo() {
        converted_compound.remove();
        canvas.getCanvasElements().set(index, compound);
        compound.draw();
    }

    @Override
    public void redo() {
        execute();
    }
}
