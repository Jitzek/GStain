package main.canvasElementStrategies.color;

import javafx.scene.paint.Color;
import main.canvasElements.CanvasElement;

public class ChangeElementFillColorStrategy implements ChangeColorStrategy {
    public void changeColor(CanvasElement element, Color color) {
        element.recolor(color);
    }
}
