package main.strategies.canvasElementStrategies.convert;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.decorators.border.BorderDecorator;
import main.canvasElements.decorators.border.EllipseBorderDecorator;
import main.canvasElements.decorators.border.RectangleBorderDecorator;

public class ConvertCanvasElementToBorderDecoratorStrategy {
    public void convert(Canvas canvas, CanvasElement element) {
        int index = canvas.getIndexOfElement(element, true);
        // Remove old GUI element
        element.remove();
        switch (element.getClass().getSimpleName().toUpperCase()) {
            case "COMPOUND":
                handleCompound((Compound) element);
                element.draw();
                break;
            case "RECTANGLE":
                // Define new element
                RectangleBorderDecorator rectangle_w_border = new RectangleBorderDecorator(element);

                // Add new element
                canvas.getCanvasElements().set(index, rectangle_w_border);

                // Draw new element
                rectangle_w_border.draw();
                break;
            case "ELLIPSE":
                // Define new element
                EllipseBorderDecorator ellipse_w_border = new EllipseBorderDecorator(element);

                // Add new element
                canvas.getCanvasElements().set(index, ellipse_w_border);

                // Draw new element
                ellipse_w_border.draw();
                break;
        }
    }

    private void handleCompound(Compound compound) {
        for (CanvasElement child : compound.getChildren()) {
            int index = compound.getIndexOfElement(child, false);
            // Remove old GUI element
            //child.remove();
            switch (child.getClass().getSimpleName().toUpperCase()) {
                case "COMPOUND":
                    handleCompound((Compound) child);
                    break;
                case "RECTANGLE":
                    // Define new element
                    RectangleBorderDecorator rectangle_w_border = new RectangleBorderDecorator(child);

                    // Add new element
                    compound.getChildren().set(index, rectangle_w_border);
                    break;
                case "ELLIPSE":
                    // Define new element
                    EllipseBorderDecorator ellipse_w_border = new EllipseBorderDecorator(child);

                    // Add new element
                    compound.getChildren().set(index, ellipse_w_border);
                    break;
            }
        }
    }
}