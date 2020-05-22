package main.visitor;

import main.canvasElements.Compound;
import main.canvasElements.decorators.border.BorderDecorator;
import main.canvasElements.decorators.border.EllipseBorderDecorator;
import main.canvasElements.decorators.border.RectangleBorderDecorator;
import main.canvasElements.shapes.Ellipse;
import main.canvasElements.shapes.Rectangle;
import main.canvasElements.shapes.Shape;

public interface CanvasElementVisitor {
    //String visitRectangle(Rectangle rectangle);
    //String visitTriangle(Triangle triangle);
    //String visitCircle(Ellipse circle);
    String visitShape(Shape shape);
    String visitGroup(Compound group);
    String visitRectangleBorderDecorator(RectangleBorderDecorator rectangleBorderDecorator);
    String visitEllipseBorderDecorator(EllipseBorderDecorator ellipseBorderDecorator);
}
