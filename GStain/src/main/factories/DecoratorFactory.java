package main.factories;

import main.canvasElements.CanvasElement;
import main.canvasElements.decorators.border.BorderDecorator;
import main.canvasElements.decorators.border.RectangleBorderDecorator;

public class DecoratorFactory {
    public static BorderDecorator getDecorator(CanvasElement element, String type) {
        switch (type.toUpperCase()) {
            case "BORDER":
                switch (element.getClass().getSimpleName().toUpperCase()) {
                    case "RECTANGLE":
                        return new RectangleBorderDecorator(element);
                    case "ELLIPSE":
                        break;
                    case "TRIANGLE":
                        break;
                }
            case "":
                break;
            case " ":
                break;
        }
        return null;
    }
}
