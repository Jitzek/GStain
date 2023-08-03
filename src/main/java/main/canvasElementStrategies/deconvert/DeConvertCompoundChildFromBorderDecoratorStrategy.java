package main.canvasElementStrategies.deconvert;

import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.decorators.border.BorderDecorator;

public class DeConvertCompoundChildFromBorderDecoratorStrategy {
    public void deconvert(Compound compound, CanvasElement child) {
        if (child instanceof Compound) {
            new DeConvertCompoundFromBorderDecoratorStrategy().deconvert(child);
            return;
        }
        if (!(child instanceof BorderDecorator)) return;

        int index = compound.getIndexOfElement(child, false);

        // Remove old GUI element
        child.remove();

        // Add new element
        compound.getChildren().set(index, ((BorderDecorator) child).getElement());

        // Draw new element
        compound.getChildren().get(index).draw();
    }
}
