package main.strategies.canvasElementStrategies.border;

import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.decorators.border.BorderDecorator;

public class ChangeBorderThicknessOfElementStrategy {
    public void changeThickness(CanvasElement element, double thickness) {
        // Handle given element is Compound
        if (element instanceof Compound) new ChangeBorderThicknessOfCompoundStrategy().changeThickness((Compound) element, thickness);

        // Handle give element is not a BorderDecorator
        if (!(element instanceof BorderDecorator)) return;

        // Change thickness of element
        ((BorderDecorator) element).setBorderThickness(thickness);
        ((BorderDecorator) element).getBorder().setStrokeWidth(thickness);
    }
}
