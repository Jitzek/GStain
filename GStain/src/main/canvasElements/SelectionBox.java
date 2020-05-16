package main.canvasElements;

import javafx.scene.Node;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import main.canvasElements.shapes.Shape;
import main.strategies.canvasElementStrategies.draw.DrawSelectionBoxStrategy;

import java.awt.*;
import java.util.ArrayList;

public class SelectionBox{
    private double sx;
    private double sy;
    private double ex;
    private double ey;
    private static SelectionBox selectionBox;
    private Path path = new Path();
    private SelectionBox(){

    }

    public static SelectionBox getSelectionBox(){
        if(selectionBox == null){
            selectionBox = new SelectionBox();
        }
        return selectionBox;
    }

    public double getSx() {
        return sx;
    }

    public void setSx(double sx) {
        this.sx = sx;
    }

    public double getSy() {
        return sy;
    }

    public void setSy(double sy) {
        this.sy = sy;
    }

    public double getEx() {
        return ex;
    }

    public void setEx(double ex) {
        this.ex = ex;
    }

    public double getEy() {
        return ey;
    }

    public void setEy(double ey) {
        this.ey = ey;
    }

    public Path getPath() {
        return this.path;
    }

    public Path draw(){
        return new DrawSelectionBoxStrategy().draw(selectionBox);
    }
}
