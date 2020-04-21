package main.strategies.canvasElementStrategies.select;

import javafx.scene.paint.Color;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import main.Canvas;
import main.canvasElements.CanvasElement;

public class SelectRectangleStrategy implements SelectElementStrategy {
    public void select(Canvas canvas, CanvasElement element) {
        if (element.getSelectionStyle() != null) element.setSelectionStyle(null);
        Path selectionStyle = new Path();
        selectionStyle.setTranslateX(element.getX());
        selectionStyle.setTranslateY(element.getY());

        MoveTo moveTo = new MoveTo();
        moveTo.setX(0.0f);
        moveTo.setY(0.0f);

        HLineTo hLineTo = new HLineTo();
        hLineTo.setX(70.0f);

        LineTo lineTo = new LineTo();
        lineTo.setX(175.0f);
        lineTo.setY(55.0f);

        selectionStyle.setFill(Color.GRAY);

        selectionStyle.getElements().add(moveTo);
        selectionStyle.getElements().add(hLineTo);
        selectionStyle.getElements().add(lineTo);

        canvas.getChildren().add(selectionStyle);

        element.setSelectionStyle(selectionStyle);
    }
}
