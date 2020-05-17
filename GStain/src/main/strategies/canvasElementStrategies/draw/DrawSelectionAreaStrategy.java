package main.strategies.canvasElementStrategies.draw;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import main.canvasElements.SelectionArea;
import main.canvasElements.SelectionBox;

public class DrawSelectionAreaStrategy {
    public Path draw(SelectionArea selectionArea) {
        selectionArea.getPath().getElements().clear();
        MoveTo move = new MoveTo(selectionArea.getSx(), selectionArea.getSy());
        LineTo line1 = new LineTo(selectionArea.getSx(), selectionArea.getEy());
        LineTo line2 = new LineTo(selectionArea.getEx(), selectionArea.getEy());
        LineTo line3 = new LineTo(selectionArea.getEx(), selectionArea.getSy());
        LineTo line4 = new LineTo(selectionArea.getSx(), selectionArea.getSy());
        selectionArea.getPath().getElements().add(move);
        selectionArea.getPath().getElements().addAll(line1, line2, line3, line4);
        return selectionArea.getPath();
    }
}
