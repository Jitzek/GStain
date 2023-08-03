package main.canvasElementStrategies.deconvert;

import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.decorators.border.BorderDecorator;

public class DeConvertCompoundFromBorderDecoratorStrategy implements DeConvertStrategy {
    @Override
    public void deconvert(CanvasElement element) {
        if (!(element instanceof Compound)) return;

        for (CanvasElement child : ((Compound) element).getChildren()) {
            if (child instanceof Compound) new DeConvertCompoundFromBorderDecoratorStrategy().deconvert(child);
            if (!(child instanceof BorderDecorator)) continue;

            int index = element.getParent().getIndexOfElement(element, false);

            // Remove old GUI element
            child.remove();

            // Add new element
            ((Compound) element).getChildren().set(index, ((BorderDecorator) child).getElement());

            // Draw new element
            ((BorderDecorator) child).getElement().draw();
        }
    }
}
