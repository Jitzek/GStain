package main.strategies.canvasElementStrategies.size;

import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Path;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.decorators.border.BorderDecorator;
import main.factories.BorderFactory;

public class ResizeBorderStrategy implements ResizeElementStrategy {
    @Override
    public void resize(Canvas canvas, CanvasElement element, double width, double height) {
        // Draw Border logic (Paint on GUI)
        Path border = BorderFactory.getBorder(((BorderDecorator) element).getElement().getClass().getSimpleName().toUpperCase(), element.getX(), element.getY(), width, height, ((BorderDecorator) element).getBorderStyle(), ((BorderDecorator) element).getBorderThickness(), ((BorderDecorator) element).getBorderColor());

        // Replace current border
        canvas.getChildren().set(canvas.getChildren().indexOf(((BorderDecorator) element).getBorder()), border);
        ((BorderDecorator) element).setBorder(border);
    }
}
