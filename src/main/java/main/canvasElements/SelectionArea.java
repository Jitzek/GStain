package main.canvasElements;

import javafx.scene.shape.Path;
import main.canvasElementStrategies.draw.DrawSelectionAreaStrategy;

public class SelectionArea{
    private double sx;
    private double sy;
    private double ex;
    private double ey;
    private static SelectionArea selectionArea;
    private Path path = new Path();
    private SelectionArea(){

    }

    public static SelectionArea getSelectionArea(){
        if(selectionArea == null){
            selectionArea = new SelectionArea();
        }
        return selectionArea;
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
        return new DrawSelectionAreaStrategy().draw(selectionArea);
    }
}