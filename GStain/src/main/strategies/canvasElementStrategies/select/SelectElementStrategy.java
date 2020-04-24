package main.strategies.canvasElementStrategies.select;

import javafx.scene.paint.Color;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.shapes.Ellipse;
import main.canvasElements.shapes.Rectangle;

public class SelectElementStrategy {
    public void select(Canvas canvas, CanvasElement element) {
        if (element.getSelectionStyle() != null) return;

        Path selectionStyle = new Path();

        double x = element.getX();
        double y = element.getY();

        // Start Top Left
        MoveTo moveTo = new MoveTo();
        moveTo.setX(x -= element.getWidth()/2);
        moveTo.setY(y -= element.getHeight()/2);

        // Top Right
        HLineTo TR = new HLineTo();
        TR.setX(x += element.getWidth());

        // Bottom Right
        VLineTo BR = new VLineTo();
        BR.setY(y += element.getHeight());

        // Bottom Left
        HLineTo BL = new HLineTo();
        BL.setX(x -= element.getWidth());

        // Top Left
        VLineTo TL = new VLineTo();
        TL.setY(y -= element.getHeight());

        selectionStyle.setStroke(Color.LIGHTGREY);
        selectionStyle.setStrokeWidth(2.0f);

        selectionStyle.getElements().addAll(moveTo, TR, BR, BL, TL);

        canvas.getChildren().add(selectionStyle);

        element.setSelectionStyle(selectionStyle);
    }
}
