package main.canvasElementStrategies.deconvert;

import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.decorators.border.BorderDecorator;

public class DeConvertElementFromBorderDecoratorStrategy implements DeConvertStrategy {
    @Override
    public void deconvert(CanvasElement element) {
        if (element instanceof Compound) {
            new DeConvertCompoundFromBorderDecoratorStrategy().deconvert(element);
            //handleCompound((Compound) element);
            return;
        }
        int index = element.getParent().getIndexOfElement(element, false);
        // Remove old GUI element
        element.remove();

        // Add new element
        element.getParent().getCanvasElements().set(index, ((BorderDecorator) element).getElement());

        // Draw new element
        ((BorderDecorator) element).getElement().draw();
    }

    private void handleCompound(Compound compound) {
        for (CanvasElement child : compound.getChildren()) {
            if (child instanceof Compound) {
                handleCompound((Compound) child);
                continue;
            }
            int index = compound.getIndexOfElement(child, false);
            // Remove old GUI element
            child.remove();

            // Add new element
            compound.getChildren().set(index, ((BorderDecorator) child).getElement());

            // Draw new element
            ((BorderDecorator) child).getElement().draw();
        }
    }
}
