package main.strategies.canvasElementStrategies.color;

import javafx.scene.paint.Color;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

public class ChangeElementFillColorStrategy {
    public void changeFill(CanvasElement element, Color color) {
        element.recolor(color);
    }
}
