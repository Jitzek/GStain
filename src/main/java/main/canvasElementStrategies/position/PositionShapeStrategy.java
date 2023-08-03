package main.canvasElementStrategies.position;

import javafx.scene.Node;
import main.Canvas;
import main.canvasElements.CanvasElement;

public class PositionShapeStrategy implements PositionElementStrategy {
    @Override
    public void position(Canvas canvas, CanvasElement element, double fromX, double fromY, double toX, double toY) {
        Node shape = canvas.getChildren().get(canvas.getCanvasElements().indexOf(element));

        System.out.println("Positioning shape From:");
        System.out.println(element.getX() + ":" + element.getX());
        System.out.println(shape.getTranslateX() + ":" + shape.getTranslateY());

        element.setX(element.getX() + (toX - fromX));
        element.setY(element.getY() + (toY - fromY));

        shape.setTranslateX(shape.getTranslateX() + (toX - fromX));
        shape.setTranslateY(shape.getTranslateY() + (toY - fromY));

        System.out.println("To:");
        System.out.println(element.getX() + ":" + element.getX());
        System.out.println(shape.getTranslateX() + ":" + shape.getTranslateY());
    }
}
