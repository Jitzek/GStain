package main.fileio;

import main.Canvas;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.decorators.border.EllipseBorderDecorator;
import main.canvasElements.decorators.border.RectangleBorderDecorator;
import main.canvasElements.shapes.Ellipse;
import main.canvasElements.shapes.Rectangle;
import main.canvasElements.shapes.Shape;
import main.visitor.CanvasElementVisitor;

import java.util.ArrayList;

public class Export implements CanvasElementVisitor {
    private int totalTabsNeeded = 0;

    /**
     * export builds the file
     * 
     * @param canvasElements list of all canvaselements in the canvas
     * @param canvas         canvas from the project
     * @return complete string to be put in the file
     */
    public String export(ArrayList<CanvasElement> canvasElements, Canvas canvas) {
        StringBuilder result = new StringBuilder();
        // first line of the file should always be the canvas
        result.append("canvas" + " " + canvas.getCanvasElements().size() + " " + canvas.getWidth() + " "
                + canvas.getHeight() + "\n");
        totalTabsNeeded++;
        for (CanvasElement canvasElement : canvasElements) {
            result.append(prependTabs(totalTabsNeeded) + canvasElement.accept(this)).append("\n");
            System.out.println(result.toString());
        }
        return result.toString();
    }

    @Override
    public String visitShape(Shape shape) {
        return shape.getClass().getSimpleName() + " " + shape.getX() + " " + shape.getY() + " " + shape.getColor() + " "
                + shape.getHeight() + " " + shape.getWidth();
    }

    @Override
    public String visitGroup(Compound group) {
        ArrayList<CanvasElement> groupElements = group.getChildren();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("group ");
        stringBuilder.append(groupElements.size());
        stringBuilder.append("\n");

        // enter group
        totalTabsNeeded++;

        for (int i = 0; i < groupElements.size(); i++) {
            String obj = groupElements.get(i).accept(this);
            obj = prependTabs(totalTabsNeeded) + obj;
            if (i < groupElements.size() - 1) {
                obj += "\n";
            }
            stringBuilder.append(obj);
        }
        // exit group
        totalTabsNeeded--;

        return stringBuilder.toString();
    }

    @Override
    public String visitRectangleBorderDecorator(RectangleBorderDecorator rectangleBorderDecorator) {
        return visitShape((Rectangle) rectangleBorderDecorator.getElement()) + " "
                + rectangleBorderDecorator.getBorderStyle() + " " + rectangleBorderDecorator.getBorderThickness() + " "
                + rectangleBorderDecorator.getBorderColor();
    }

    @Override
    public String visitEllipseBorderDecorator(EllipseBorderDecorator ellipseBorderDecorator) {
        return visitShape((Ellipse) ellipseBorderDecorator.getElement()) + " " + ellipseBorderDecorator.getBorderStyle()
                + " " + ellipseBorderDecorator.getBorderThickness() + " " + ellipseBorderDecorator.getBorderColor();
    }

    private String prependTabs(int deepness) {
        return "\t".repeat(Math.max(0, deepness - 1));
    }
}
