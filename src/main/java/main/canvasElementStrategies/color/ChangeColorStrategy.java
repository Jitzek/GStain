package main.canvasElementStrategies.color;

import javafx.scene.paint.Color;
import main.canvasElements.CanvasElement;

public interface ChangeColorStrategy {
    void changeColor(CanvasElement element, Color color);
}
