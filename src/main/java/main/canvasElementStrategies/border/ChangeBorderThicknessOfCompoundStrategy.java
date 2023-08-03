package main.canvasElementStrategies.border;

import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.decorators.border.BorderDecorator;

public class ChangeBorderThicknessOfCompoundStrategy {
    public void changeThickness(Compound compound, double thickness) {
        for (CanvasElement child : compound.getChildren()) {
            if (child instanceof Compound) {
                new ChangeBorderThicknessOfCompoundStrategy().changeThickness((Compound) child, thickness);
                continue;
            }
            if (!(child instanceof BorderDecorator)) continue;
            new ChangeBorderThicknessOfElementStrategy().changeThickness(child, thickness);
        }
    }
}
