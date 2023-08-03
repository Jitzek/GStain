package main.commands.canvasElementCommands.borderCommands;

import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.commands.Command;
import main.canvasElementStrategies.convert.ConvertCompoundChildToBorderDecoratorStrategy;
import main.canvasElementStrategies.deconvert.DeConvertCompoundChildFromBorderDecoratorStrategy;

public class ConvertCompoundChildToBorderDecoratorCommand implements Command {
    private final Compound compound;
    private CanvasElement child;
    private final int index;

    public ConvertCompoundChildToBorderDecoratorCommand(Compound compound, CanvasElement child) {
        this.compound = compound;
        this.child = child;
        index = compound.getIndexOfElement(child, false);
    }

    @Override
    public void execute() {
        new ConvertCompoundChildToBorderDecoratorStrategy().convert(compound, child);
        child = compound.getChildren().get(index);
    }

    @Override
    public void undo() {
        new DeConvertCompoundChildFromBorderDecoratorStrategy().deconvert(compound, child);
        child = compound.getChildren().get(index);
    }

    @Override
    public void redo() {
        execute();
    }
}
