package main.strategies.canvasElementStrategies.select;

import javafx.scene.paint.Color;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.SelectionBox;

public class SelectCompoundStrategy {
    public void select(Canvas canvas, Compound compound) {
        if (compound.getSelectionBox() != null) return;

        for (CanvasElement child : compound.getChildren()) {
            child.select();
        }

        /*
        Create SelectionBox
         */
        compound.setSelectionBox(new SelectionBox(compound));

        Path selectionStyle = new Path();

        double x = compound.getX();
        double y = compound.getY();

        double width = compound.getWidth();
        double height = compound.getHeight();

        // Start Top Left
        MoveTo moveTo = new MoveTo();
        moveTo.setX(x -= width/2);
        moveTo.setY(y -= height/2);

        // Top Right
        HLineTo TR = new HLineTo();
        TR.setX(x += width);

        // Bottom Right
        VLineTo BR = new VLineTo();
        BR.setY(y += height);

        // Bottom Left
        HLineTo BL = new HLineTo();
        BL.setX(x -= width);

        // Top Left
        VLineTo TL = new VLineTo();
        TL.setY(y -= height);

        selectionStyle.setStroke(Color.LIGHTGREY);
        selectionStyle.setStrokeWidth(2.0f);

        selectionStyle.getElements().addAll(moveTo, TR, BR, BL, TL);

        canvas.getChildren().add(selectionStyle);

        compound.getSelectionBox().setSelectionStyle(selectionStyle);
    }
}
