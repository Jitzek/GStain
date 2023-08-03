package main.factories;

import javafx.scene.paint.Color;
import main.Canvas;
import main.canvasElements.shapes.Ellipse;
import main.canvasElements.shapes.Rectangle;
import main.canvasElements.shapes.Shape;

public class ShapeFactory {
    public static Shape getShape(Canvas canvas, String shape, double x, double y, Color color, double width, double height) {
        switch (shape.toUpperCase()) {
            case "RECTANGLE":
                return new Rectangle(canvas, x, y, color, width, height);
            case "ELLIPSE":
                return new Ellipse(canvas, x, y, color, width, height);
            case "TRIANGLE":
                break;
        }
        return null;
    }
}
