package main.factories;

import javafx.scene.paint.Color;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.shapes.Ellipse;
import main.canvasElements.shapes.Rectangle;

public class CanvasElementCloneFactory {
    public static CanvasElement getClone(CanvasElement element) {
        switch (element.getClass().getSimpleName().toUpperCase()) {
            case "COMPOUND":
                Compound compound = new Compound(element.getParent());
                for (CanvasElement child : ((Compound) element).getChildren()) {
                    compound.addChild(CanvasElementCloneFactory.getClone(child));
                }
                return compound;
            case "RECTANGLE":
                return new Rectangle(element.getParent(), element.getX(), element.getY(), element.getColor(), element.getWidth(), element.getHeight());
            case "ELLIPSE":
                return new Ellipse(element.getParent(), element.getX(), element.getY(), element.getColor(), element.getWidth(), element.getHeight());
            case "TRIANGLE":
                break;
        }
        return null;
    }
}
