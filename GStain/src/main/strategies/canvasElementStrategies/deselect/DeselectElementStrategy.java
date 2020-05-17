package main.strategies.canvasElementStrategies.deselect;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

public class DeselectElementStrategy {
    public void deselect(Canvas canvas, CanvasElement element) {
        if (element.getSelectionBox() == null) return;
        // Remove selectionStyle from UI
        canvas.getChildren().remove(element.getSelectionBox().getSelectionStyle());

        if (element instanceof Compound) {
            for (CanvasElement child : ((Compound) element).getChildren()) {
                child.deselect();
            }
        }

        // Remove points from UI
        /*for (var point : element.getSelectionBox().getPoints()) {
            canvas.getChildren().remove(point.getUiElement());
        }*/

        element.setSelectionBox(null);
    }
}
