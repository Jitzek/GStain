package main.factories;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import main.Canvas;
import main.canvasElements.decorators.border.border.BorderStyle;
import main.canvasElements.shapes.Ellipse;
import main.canvasElements.shapes.Rectangle;
import main.canvasElements.shapes.Shape;

public class BorderFactory {
    public static Path getBorder(String shape, double x, double y, double width, double height, BorderStyle style, double thickness, Color color) {
        switch (shape.toUpperCase()) {
            case "RECTANGLE":
                return getRectangleBorder(x, y, width, height, style, thickness, color);
            case "ELLIPSE":
                return getEllipseBorder(x, y, width, height, style, thickness, color);
            case "TRIANGLE":
                break;
        }
        return null;
    }

    private static Path getRectangleBorder(double x, double y, double width, double height, BorderStyle style, double thickness, Color color) {
        Path border = new Path();

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

        border.setStroke(color);
        border.setStrokeWidth(thickness);

        border.getElements().addAll(moveTo, TR, BR, BL, TL);

        return border;
    }

    private static Path getEllipseBorder(double x, double y, double width, double height, BorderStyle style, double thickness, Color color) {
        Path border = new Path();

        // Start Mid Left
        MoveTo moveTo = new MoveTo();
        moveTo.setX(x -= width/2);
        moveTo.setX(y -= height/2);

        ArcTo firstHalf = new ArcTo();
        firstHalf.setX(x += width/2);
        firstHalf.setY(y += height/2);

        border.getElements().addAll(moveTo, firstHalf);

        return border;
    }
}
