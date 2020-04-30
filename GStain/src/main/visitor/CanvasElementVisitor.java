package main.visitor;

import main.canvasElements.Compound;
import main.canvasElements.shapes.Ellipse;
import main.canvasElements.shapes.Rectangle;

public interface CanvasElementVisitor {
    String visitRectangle(Rectangle rectangle);
    //String visitTriangle(Triangle triangle);
    String visitCircle(Ellipse circle);
    String visitGroup(Compound group);
}
