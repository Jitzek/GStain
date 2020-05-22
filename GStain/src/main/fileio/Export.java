package main.fileio;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.decorators.border.BorderDecorator;
import main.canvasElements.decorators.border.EllipseBorderDecorator;
import main.canvasElements.decorators.border.RectangleBorderDecorator;
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

    /*
    @Override
    public String visitCircle(Ellipse circle) {
        return "Circle " + circle.getX() + " " + circle.getY() + " " + circle.getColor() +  " " + circle.getHeight() + " " + circle.getWidth();
    }

    @Override
    public String visitRectangle(Rectangle rectangle) {
        return "Rectangle " + rectangle.getX() + " " + rectangle.getY() + " " + rectangle.getColor() +  " "+ rectangle.getHeight() + " " + rectangle.getWidth();
    }

     */

    @Override
    public String visitShape(Shape shape){
        return shape.getClass().getSimpleName() + " " + shape.getX() + " " + shape.getY() + " " + shape.getColor() +  " "+ shape.getHeight() + " " + shape.getWidth();
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

    @Override
    public String visitRectangleBorderDecorator(RectangleBorderDecorator rectangleBorderDecorator) {
        return visitShape((Rectangle)rectangleBorderDecorator.getElement()) + " " + rectangleBorderDecorator.getBorderStyle() + " " + rectangleBorderDecorator.getBorderThickness() + " " + rectangleBorderDecorator.getBorderColor();
    }

    @Override
    public String visitEllipseBorderDecorator(EllipseBorderDecorator ellipseBorderDecorator) {
        return visitShape((Ellipse)ellipseBorderDecorator.getElement()) + " " + ellipseBorderDecorator.getBorderStyle() + " " + ellipseBorderDecorator.getBorderThickness() + " " + ellipseBorderDecorator.getBorderColor();
    }

    /**
     * goes trough all elements in the group
     * @param group group with the needed elements
     * @return string with all info from the group that will be put in the file
     */
    private String _visitGroup(Compound group){
        StringBuilder stringBuilder = new StringBuilder();
        deepness++;
        int i = 0;
        for(CanvasElement shape : group.getChildren()){
            String obj = shape.accept(this);
            //check if last iteration of loop
            if(++i == group.getChildren().size()){
            obj = weNeedToGoDeeper(deepness) + obj;
            }else{
            obj = weNeedToGoDeeper(deepness) + obj + "\n";
            }
            stringBuilder.append(obj);
        }
        deepness--;
        return stringBuilder.toString();
    }
    private String weNeedToGoDeeper(int deepness){
        return "\t".repeat(Math.max(0, deepness - 1));
    }
}
