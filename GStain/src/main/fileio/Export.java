package main.fileio;

import jdk.internal.joptsimple.internal.Strings;
import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.shapes.Ellipse;
import main.canvasElements.shapes.Rectangle;
import main.canvasElements.shapes.Shape;
import main.visitor.CanvasElementVisitor;

import java.util.ArrayList;
import java.util.Arrays;

public class Export implements CanvasElementVisitor {
    private int deepness = 0;
    /**
     * export builds the file
     * @param args list of all canvaselements in the canvas
     * @param canvas canvas from the project
     * @return complete string to be put in the file
     */
    public String export(ArrayList<CanvasElement> args, Canvas canvas){
        StringBuilder sb = new StringBuilder();
        sb.append("canvas" + " " + canvas.getCanvasElements().size() + " " + canvas.getWidth() + " " + canvas.getHeight() + "\n");
        deepness++;
        for(CanvasElement shape : args){
            sb.append(weNeedToGoDeeper(deepness) + shape.accept(this)).append("\n");
            System.out.println(sb.toString());
        }
        return sb.toString();
    }

    /**
     * get all info for every circle
     * @param circle circle that has been visited
     * @return string with all info from the circle that will be put in the file
     */
    @Override
    public String visitCircle(Ellipse circle) {
        return "Circle " + circle.getX() + " " + circle.getY() + " " + circle.getColor() +  " " + circle.getHeight() + " " + circle.getWidth();
    }

    @Override
    public String visitRectangle(Rectangle rectangle) {
        return "Rectangle " + rectangle.getX() + " " + rectangle.getY() + " " + rectangle.getColor() +  " "+ rectangle.getHeight() + " " + rectangle.getWidth();
    }
/*
    @Override
    public String visitTriangle(Triangle triangle) {
        return "Triangle " + triangle.getX() + " " + triangle.getY() + " " + triangle.getHeigth() + " " + triangle.getWidth();
    }
*/

    @Override
    public String visitGroup(Compound group) {
        return "group " + group.getChildren().size() + "\n" + _visitGroup(group);
    }

    /**
     * goes trough all elements in the group
     * @param group group with the needed elements
     * @return string with all info from the group that will be put in the file
     */
    //FIXME: fix extra \n after group ended
    public String _visitGroup(Compound group){
        StringBuilder stringBuilder = new StringBuilder();
        deepness++;
        for(CanvasElement shape : group.getChildren()){
            String obj = shape.accept(this);
            obj = weNeedToGoDeeper(deepness) + obj.replace("\n", "\n") + "\n";

            stringBuilder.append(obj);
        }
        deepness--;
        return stringBuilder.toString();
    }
    private String weNeedToGoDeeper(int deepness){
        return "\t".repeat(Math.max(0, deepness - 1));
    }
}
