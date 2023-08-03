package main.canvasElementStrategies.deselect;

import main.Canvas;
import main.canvasElements.Compound;

public class DeselectCompoundStrategy {
    public void deselect(Canvas canvas, Compound compound) {
        if (compound.getSelectionBox() == null) return;

        canvas.getChildren().remove(compound.getSelectionBox().getSelectionStyle());

        compound.setSelectionBox(null);
    }
}
