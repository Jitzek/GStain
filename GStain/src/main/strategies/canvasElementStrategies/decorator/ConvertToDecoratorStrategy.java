package main.strategies.canvasElementStrategies.decorator;

import main.canvasElements.CanvasElement;
import main.decorators.ElementDecorator;

public class ConvertToDecoratorStrategy {
    public void convert(CanvasElement element, ElementDecorator decorator) {
        element.getParent().getCanvasElements().set(element.getParent().getIndexOfElement(element, true), decorator);
    }
}
