package main.strategies.canvasElementStrategies.draw;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import main.canvasElements.SelectionBox;

public class DrawSelectionBoxStrategy {
    public Path draw(SelectionBox selectionBox) {
        selectionBox.getPath().getElements().clear();
        MoveTo move = new MoveTo(selectionBox.getSx(), selectionBox.getSy());
        LineTo line1 = new LineTo(selectionBox.getSx(), selectionBox.getEy());
        LineTo line2 = new LineTo(selectionBox.getEx(), selectionBox.getEy());
        LineTo line3 = new LineTo(selectionBox.getEx(), selectionBox.getSy());
        LineTo line4 = new LineTo(selectionBox.getSx(), selectionBox.getSy());
        selectionBox.getPath().getElements().add(move);
        selectionBox.getPath().getElements().addAll(line1, line2, line3, line4);
        return selectionBox.getPath();
    }
}
