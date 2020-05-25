package main.strategies.canvasElementStrategies.draw;

import javafx.scene.shape.Path;
import main.Canvas;
import main.canvasElements.decorators.border.BorderDecorator;
import main.factories.BorderFactory;

public class DrawBorderDecoratorStrategy {
    public void draw(Canvas canvas, BorderDecorator borderDecorator) {
        borderDecorator.select();

        // Draw Border logic (Paint on GUI)
        Path border = BorderFactory.getBorder(
                borderDecorator.getElement().getClass().getSimpleName().toUpperCase(),
                borderDecorator.getX(),
                borderDecorator.getY(),
                borderDecorator.getWidth(),
                borderDecorator.getHeight(),
                borderDecorator.getBorderStyle(),
                borderDecorator.getBorderThickness(),
                borderDecorator.getBorderColor());

        canvas.getChildren().add(0, border);

        // Store Drawn Border into it's container
        borderDecorator.setBorder(border);

        // Draw it's stored element
        borderDecorator.getElement().draw();
    }
}
