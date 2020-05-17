package main.strategies.canvasElementStrategies.convert;

import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.decorators.border.BorderDecorator;
import main.canvasElements.decorators.border.EllipseBorderDecorator;
import main.canvasElements.decorators.border.RectangleBorderDecorator;

public class ConvertCompoundToBorderDecoratorStrategy {
    public void convert(Compound compound) {
        for (CanvasElement child : compound.getChildren()) {
            new ConvertCompoundChildToBorderDecoratorStrategy().convert(compound, child);
        }
    }
}