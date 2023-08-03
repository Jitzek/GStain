package main.canvasElementStrategies.size;

import main.canvasElements.CanvasElement;
import main.canvasElements.SelectionPoint;
import main.commands.canvasElementCommands.ResizeElementsCommand;

import java.util.ArrayList;

public class ResizeElementByDraggingStrategy {
    public void resize(ArrayList<CanvasElement> elements, SelectionPoint point, double new_x, double new_y) {
        double x_change = new_x - point.getCoordinates()[0];
        double y_change = new_y - point.getCoordinates()[1];
        point.setCoordinates(new double[]{new_x, new_y});

        switch (point.getType()) {
            case TOPLEFT:
                new ResizeElementsCommand(elements, x_change * -1, y_change * -1).execute();
                break;
            case TOPMID:
                new ResizeElementsCommand(elements, 0, y_change * -1).execute();
                break;
            case TOPRIGHT:
                new ResizeElementsCommand(elements, x_change, y_change * -1).execute();
                break;
            case MIDRIGHT:
                new ResizeElementsCommand(elements, x_change, 0).execute();
                break;
            case BOTTOMRIGHT:
                new ResizeElementsCommand(elements, x_change, y_change).execute();
                break;
            case BOTTOMMID:
                new ResizeElementsCommand(elements, 0, y_change).execute();
                break;
            case BOTTOMLEFT:
                new ResizeElementsCommand(elements, x_change * -1, y_change).execute();
                break;
            case MIDLEFT:
                new ResizeElementsCommand(elements, x_change * -1, 0).execute();
                break;
        }
    }
}
