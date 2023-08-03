package main.canvasElementStrategies.color;

import javafx.scene.paint.Color;
import main.canvasElements.CanvasElement;
import main.canvasElements.decorators.border.BorderDecorator;

public class ChangeElementBorderColorStrategy implements ChangeColorStrategy {
    @Override
    public void changeColor(CanvasElement element, Color color) {
        ((BorderDecorator) element).recolorBorder(color);
    }
}
