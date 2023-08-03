package main.canvasElementStrategies.convert;

import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.decorators.border.BorderDecorator;
import main.canvasElements.decorators.border.EllipseBorderDecorator;
import main.canvasElements.decorators.border.RectangleBorderDecorator;

public class ConvertCompoundChildToBorderDecoratorStrategy {
    public void convert(Compound compound, CanvasElement child) {
        if (child instanceof BorderDecorator) return;
        if (!(child instanceof Compound)) child.remove();
        int index = compound.getIndexOfElement(child, false);
        switch (child.getClass().getSimpleName().toUpperCase()) {
            case "COMPOUND":
                new ConvertCompoundToBorderDecoratorStrategy().convert((Compound) child);
                compound.getChildren().set(index, child);
                break;
            case "RECTANGLE":
                // Define new element
                RectangleBorderDecorator rectangle_w_border = new RectangleBorderDecorator(child);

                // Add new element
                compound.getChildren().set(index, rectangle_w_border);

                // Draw new element
                rectangle_w_border.draw();
                break;
            case "ELLIPSE":
                // Define new element
                EllipseBorderDecorator ellipse_w_border = new EllipseBorderDecorator(child);

                // Add new element
                compound.getChildren().set(index, ellipse_w_border);

                // Draw new element
                ellipse_w_border.draw();
                break;
        }
    }
}
