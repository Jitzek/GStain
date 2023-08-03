package main.visitor;

import main.canvasElements.Compound;
import main.canvasElements.decorators.border.EllipseBorderDecorator;
import main.canvasElements.decorators.border.RectangleBorderDecorator;
import main.canvasElements.shapes.Shape;

public interface CanvasElementVisitor {
    String visitShape(Shape shape);
    String visitGroup(Compound group);
    String visitRectangleBorderDecorator(RectangleBorderDecorator rectangleBorderDecorator);
    String visitEllipseBorderDecorator(EllipseBorderDecorator ellipseBorderDecorator);
}
