package main.strategies.canvasElementStrategies.draw;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.decorators.border.BorderDecorator;

public class DrawBorderStrategy implements DrawElementStrategy {
    @Override
    public void draw(Canvas canvas, CanvasElement element) {
        element.select();

        canvas.getChildren().add(0, ((BorderDecorator) element).getBorder());
    }
}
