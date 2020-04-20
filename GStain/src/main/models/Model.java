package main.models;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Canvas;
import main.CommandSender;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.shapes.Ellipse;
import main.canvasElements.shapes.Rectangle;
import main.commands.Command;
import main.commands.DragElementsCommand;
import main.commands.DrawElementsCommand;
import main.commands.RemoveElementsCommand;
import main.commands.compoundCommands.ConvertToCompoundCommand;
import main.commands.shapeCommands.ellipseCommands.DrawEllipseCommand;
import main.commands.shapeCommands.rectangleCommands.DrawRectangleCommand;
import main.factories.CanvasElementCloneFactory;
import main.factories.ShapeFactory;

import java.util.ArrayList;
import java.util.Collections;

public class Model {
    private final ArrayList<CanvasElement> copiedElements = new ArrayList<>();
    private final ArrayList<KeyCode> activeKeys = new ArrayList<>();
    private final ToolModel toolModel = new ToolModel();
    private CommandSender commandSender;
    private Canvas canvas;
    private final Stage stage;

    private final Label selectedElementLabel;

    public Model(Stage stage, Label selectedElementLabel) {
        this.stage = stage;
        this.selectedElementLabel = selectedElementLabel;
        commandSender = new CommandSender();
        canvas = Canvas.getInstance();
    }

    public void closeCanvas() {
        // Flush commands
        commandSender = new CommandSender();

        // Reset Canvas
        canvas.destroy();
        canvas = Canvas.getInstance();
    }

    public Stage getStage() {
        return stage;
    }
    public Scene getScene() {
        return stage.getScene();
    }

    public void setStageTitle(String title) {
        stage.setTitle(title);
    }

    public Label getSelectedElementLabel() {
        return selectedElementLabel;
    }

    /**
     * Logic for the misc actions that (should) take place when an element has been selected
     */
    public void handleElementSelection() {
        // Set selectedElementLabel Text
        int index = -1;
        int count = 0;
        for (CanvasElement ce : canvas.getSelectedCanvasElements()) {
            if (count++ > 1) break;
            index = canvas.getCanvasElements().indexOf(ce);
        }
        if (count > 1) selectedElementLabel.setText("Multiple Selections");
        else if (count < 1) selectedElementLabel.setText("No Selection");
        else selectedElementLabel.setText(canvas.getCanvasElementAt(index, false, false).getClass().getSimpleName());
    }

    /**
     * Clones selected elements (preserving the information of the moment it was copied)
     */
    public void copySelectedElements() {
        if (canvas.getSelectedCanvasElements().size() < 1) return;
        copiedElements.clear();
        for (CanvasElement ce : canvas.getSelectedCanvasElements()) {
            copiedElements.add(CanvasElementCloneFactory.getClone(ce));
        }
    }

    /**
     * Clones using copySelectedElements and removes afterwards
     */
    public void cutSelectedElements() {
        if (canvas.getSelectedCanvasElements().size() < 1) return;
        copiedElements.clear();
        copySelectedElements();
        commandSender.execute(new RemoveElementsCommand(canvas, canvas.getSelectedCanvasElements()));
    }

    /**
     * Clones and Pastes Copied elements
     */
    public void pasteSelectedElements() {
        if (copiedElements.size() < 1) return;

        canvas.deselectAllElements();
        ArrayList<CanvasElement> clones = new ArrayList<>();
        for (CanvasElement ce : copiedElements) clones.add(0, CanvasElementCloneFactory.getClone(ce));
        commandSender.execute(new DrawElementsCommand(canvas, clones));
        handleElementSelection();
    }

    public ArrayList<CanvasElement> getCopiedElements() {
        return copiedElements;
    }

    public ToolModel getToolModel() {
        return toolModel;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void undoCommand() {
        commandSender.undo();
    }

    public void redoCommand() {
        commandSender.redo();
    }

    public void addActiveKey(KeyCode key) {
        activeKeys.add(key);
    }

    public void removeActiveKey(KeyCode key) {
        activeKeys.remove(key);
    }

    public ArrayList<KeyCode> getActiveKeys() {
        return activeKeys;
    }

    public boolean isKeyActive(KeyCode key) {
        return activeKeys.contains(key);
    }

    public boolean areKeysActive(KeyCode... keys) {
        for (KeyCode key : keys) {
            if (!activeKeys.contains(key)) return false;
        }
        return true;
    }

    /**
     * Debugging purposes:
     * Prints a visual representation of the different compounds and their elements
     */
    public void printHierarchy() {
        for (CanvasElement ce : canvas.getCanvasElements()) {
            System.out.println("\u001b[34m" + ce.getClass().getSimpleName() + "\u001B[0m" + " " + ce.getUUID());
            if (ce.getClass().getSimpleName().toUpperCase().equals("COMPOUND")) {
                printCompound((Compound) ce, "\t");
            }
        }
        System.out.println("");
    }
    private void printCompound(Compound compound, String whitespace) {
        for (CanvasElement ce : compound.getChildren()) {
            System.out.println("\u001b[34m" + whitespace + "" + ce.getClass().getSimpleName() + "\u001B[0m" + " " + ce.getUUID());
            if (ce.getClass().getSimpleName().toUpperCase().equals("COMPOUND")) {
                printCompound((Compound) ce, whitespace + "\t");
            }
        }
    }

    /**
     * Converts selected elements to a compound
     */
    public void groupSelectedElements() {
        if (canvas.getSelectedCanvasElements().size() < 2) return;
        Collections.reverse(canvas.getSelectedCanvasElements());
        commandSender.execute(new ConvertToCompoundCommand(canvas, canvas.getSelectedCanvasElements()));
        printHierarchy();
    }

    public void ungroupSelectedElements() {
        if (canvas.getSelectedCanvasElements().size() < 1) return;
        // TODO
    }

    public void addRectangleToCanvas(double x, double y, Color color, double width, double height) {
        Rectangle rectangle = (Rectangle) ShapeFactory.getShape("RECTANGLE", x, y, color, width, height);
        Command command = new DrawRectangleCommand(canvas, rectangle);
        commandSender.execute(command);
        handleElementSelection();
    }

    public void addEllipseToCanvas(double x, double y, Color color, double width, double height) {
        Ellipse ellipse = (Ellipse) ShapeFactory.getShape("Ellipse", x, y, color, width, height);
        Command command = new DrawEllipseCommand(canvas, ellipse);
        commandSender.execute(command);
        handleElementSelection();
    }

    public void dragElements(ArrayList<CanvasElement> elements, double x_dragged, double y_dragged) {
        commandSender.execute(new DragElementsCommand(canvas, elements, x_dragged, y_dragged));
    }

    /**
     * Removes all elements currently selected
     */
    public void removeSelectedElementsFromCanvas() {
        if (canvas.getSelectedCanvasElements().size() > 0) commandSender.execute(new RemoveElementsCommand(canvas, canvas.getSelectedCanvasElements()));
    }
}
