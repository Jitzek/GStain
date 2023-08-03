package main.commands.canvasElementCommands.borderCommands;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.decorators.border.BorderDecorator;
import main.commands.Command;
import main.canvasElementStrategies.border.ChangeBorderThicknessOfElementStrategy;

import java.util.ArrayList;

public class ChangeThicknessOfCompoundBorderCommand implements Command {
    private final Object parent;

    private Compound compound;
    private final double thickness;

    private ArrayList<Double> prev_thicknesses = new ArrayList<>();
    private ArrayList<Command> conversions = new ArrayList<>();

    private ArrayList<ChangeThicknessOfCompoundBorderCommand> subCommands = new ArrayList<>();

    private final int index;

    public ChangeThicknessOfCompoundBorderCommand(Compound compound, double thickness) {
        parent = compound.getParent();
        this.compound = compound;
        this.thickness = thickness;
        index = compound.getParent().getIndexOfElement(compound, false);
    }

    public ChangeThicknessOfCompoundBorderCommand(Compound parent, Compound compound, double thickness) {
        this.parent = parent;
        this.compound = compound;
        this.thickness = thickness;
        index = parent.getIndexOfElement(compound, false);
    }

    @Override
    public void execute() {
        for (CanvasElement child : compound.getChildren()) {
            int index = compound.getIndexOfElement(child, false);
            if (child instanceof Compound) {
                // Handle Child is Compound
                ChangeThicknessOfCompoundBorderCommand command = new ChangeThicknessOfCompoundBorderCommand(compound, (Compound) child, thickness);
                command.execute();
                subCommands.add(command);

                // FIXME value will be ignored but needs to exist to validate index
                prev_thicknesses.add(-1.0);
                continue;
            }
            if (!(child instanceof BorderDecorator)) {
                // Convert child
                ConvertCompoundChildToBorderDecoratorCommand conversion = new ConvertCompoundChildToBorderDecoratorCommand(compound, child);
                conversion.execute();
                conversions.add(conversion);
            }
            child = compound.getChildren().get(index);
            prev_thicknesses.add(((BorderDecorator) child).getBorderThickness());
            new ChangeBorderThicknessOfElementStrategy().changeThickness(child, thickness);
        }
        for (Command command : subCommands) command.execute();
    }

    @Override
    public void undo() {
        for (int i = 0; i < compound.getChildren().size(); i++) {
            if (compound.getChildren().get(i) instanceof Compound) continue;
            new ChangeBorderThicknessOfElementStrategy().changeThickness(compound.getChildren().get(i), prev_thicknesses.get(i));
        }
        for (Command subCommand : subCommands) subCommand.undo();
        for (Command conversion : conversions) conversion.undo();

        if (parent instanceof Compound) compound = (Compound) ((Compound) parent).getChildren().get(index);
        else if (parent instanceof Canvas) compound = (Compound) ((Canvas) parent).getCanvasElements().get(index);
        //else if (parent instanceof Canvas) compound = (Compound) ((Canvas) parent).getCanvasElementAt(index, false, false);
        else System.out.println("Invalid Parent instance: " + parent.getClass().getSimpleName());
    }

    @Override
    public void redo() {
        prev_thicknesses = new ArrayList<>();
        subCommands = new ArrayList<>();
        conversions = new ArrayList<>();
        execute();
    }
}
