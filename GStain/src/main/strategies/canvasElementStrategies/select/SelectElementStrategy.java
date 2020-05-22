package main.strategies.canvasElementStrategies.select;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.SelectionBox;

public class SelectElementStrategy {
    public void select(Canvas canvas, CanvasElement element) {
        if (element.getSelectionBox() != null) return;

        /*
        Create SelectionBox
         */
        element.setSelectionBox(new SelectionBox(element));

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

        element.getSelectionBox().setSelectionStyle(selectionStyle);

        /*
        Create Selection Points
         */

        /*double width = SelectionPoint.WIDTH;
        double height = SelectionPoint.HEIGHT;

        // Top Left

        x = element.getX();
        y = element.getY();

        x -= element.getWidth()/2;
        y -= element.getHeight()/2;

        Rectangle point = new Rectangle(x - width/2, y  - height/2, width, height);
        point.setFill(Color.GRAY);
        element.getSelectionBox().getPoints().add(new SelectionPoint(
                SelectionPointType.TOPLEFT,
                new double[]{x, y},
                point));


        // Top Mid
        x += element.getWidth()/2;
        point = new Rectangle(x - width/2, y  - height/2, width, height);
        point.setFill(Color.GRAY);
        element.getSelectionBox().getPoints().add(new SelectionPoint(
                SelectionPointType.TOPMID,
                new double[]{x, y},
                point));


        // Top Right
        x += element.getWidth()/2;
        point = new Rectangle(x - width/2, y  - height/2, width, height);
        point.setFill(Color.GRAY);
        element.getSelectionBox().getPoints().add(new SelectionPoint(
                SelectionPointType.TOPRIGHT,
                new double[]{x, y},
                point));


        // Mid Right
        y += element.getWidth()/2;
        point = new Rectangle(x - width/2, y  - height/2, width, height);
        point.setFill(Color.GRAY);
        element.getSelectionBox().getPoints().add(new SelectionPoint(
                SelectionPointType.MIDRIGHT,
                new double[]{x, y},
                point));


        // Bottom Right
        y += element.getWidth()/2;
        point = new Rectangle(x - width/2, y  - height/2, width, height);
        point.setFill(Color.GRAY);
        element.getSelectionBox().getPoints().add(new SelectionPoint(
                SelectionPointType.BOTTOMRIGHT,
                new double[]{x, y},
                point));


        // Bottom Mid
        x -= element.getWidth()/2;
        point = new Rectangle(x - width/2, y  - height/2, width, height);
        point.setFill(Color.GRAY);
        element.getSelectionBox().getPoints().add(new SelectionPoint(
                SelectionPointType.BOTTOMMID,
                new double[]{x, y},
                point));

        // Bottom Left
        x -= element.getWidth()/2;
        point = new Rectangle(x - width/2, y  - height/2, width, height);
        point.setFill(Color.GRAY);
        element.getSelectionBox().getPoints().add(new SelectionPoint(
                SelectionPointType.BOTTOMLEFT,
                new double[]{x, y},
                point));

        // Mid Left
        y -= element.getWidth()/2;
        point = new Rectangle(x - width/2, y  - height/2, width, height);
        point.setFill(Color.GRAY);
        element.getSelectionBox().getPoints().add(new SelectionPoint(
                SelectionPointType.MIDLEFT,
                new double[]{x, y},
                point));

        for (SelectionPoint sp : element.getSelectionBox().getPoints()) {
            canvas.getChildren().add(sp.getUiElement());
        }*/
    }
}
