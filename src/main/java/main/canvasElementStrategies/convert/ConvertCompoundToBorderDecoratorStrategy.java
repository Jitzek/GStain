package main.canvasElementStrategies.convert;

import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;

public class ConvertCompoundToBorderDecoratorStrategy {
    public void convert(Compound compound) {
        for (CanvasElement child : compound.getChildren()) {
            new ConvertCompoundChildToBorderDecoratorStrategy().convert(compound, child);
        }
    }
}