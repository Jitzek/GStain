package main.strategies.canvasElementStrategies;

import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

public class DrawEllipseStrategy implements DrawElementStrategy {
    @Override
    public void draw(Canvas canvas, CanvasElement element) {
        System.out.println("Drawing Rectangle " + element.getUUID());

        element.select();

        // Draw Rectangle logic (Paint on GUI)
        Ellipse ellipse = new Ellipse(element.getX(), element.getY(), element.getWidth()/2, element.getHeight()/2);
        ellipse.setFill(element.getColor());

        canvas.getChildren().add(canvas.getIndexOfElement(element, true), ellipse);
    }
}
