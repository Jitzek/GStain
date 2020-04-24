package main.models;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.BorderStyle;
import main.Canvas;
import main.CommandSender;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.commands.canvasElementCommands.*;
import main.commands.canvasElementCommands.compoundCommands.ConvertToCompoundCommand;
import main.commands.canvasElementCommands.compoundCommands.ConvertToElementsCommand;
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
    private Color selectedFillColor = Color.BLACK;
    private Color selectedBorderColor = Color.BLACK;
    private double selectedBorderThickness = 0.0;
    private double lastShapeWidth;
    private double lastShapeHeight;
    private final TextField ShapeWidth;
    private final TextField ShapeHeight;

    private final Label selectedElementLabel;

    public Model(Stage stage, Label selectedElementLabel, TextField ShapeWidth, TextField ShapeHeight) {
        this.stage = stage;
        this.selectedElementLabel = selectedElementLabel;
        commandSender = new CommandSender();
        canvas = Canvas.getInstance();
        this.ShapeWidth = ShapeWidth;
        this.ShapeHeight = ShapeHeight;
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

    public double getLastShapeWidth() {
        return lastShapeWidth;
    }
    public void setLastShapeWidth(double lastShapeWidth) {
        this.lastShapeWidth = lastShapeWidth;
    }

    public double getLastShapeHeight() {
        return lastShapeHeight;
    }
    public void setLastShapeHeight(double lastShapeHeight) {
        this.lastShapeHeight = lastShapeHeight;
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

        // Display width and height of element
        if (count == 1 && !(canvas.getCanvasElementAt(index, false, false) instanceof Compound)) {
            ShapeWidth.setText(String.valueOf(canvas.getCanvasElementAt(index, false, false).getWidth()));
            ShapeHeight.setText(String.valueOf(canvas.getCanvasElementAt(index, false, false).getHeight()));
        } else {
            ShapeWidth.setText("");
            ShapeHeight.setText("");
        }
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
        ArrayList<Compound> compounds = new ArrayList<>();
        for (CanvasElement element : canvas.getSelectedCanvasElements()) {
            if (element instanceof Compound) compounds.add((Compound) element);
        }
        commandSender.execute(new ConvertToElementsCommand(canvas, compounds));
    }

    public void addRectangleToCanvas(double x, double y, Color color, double width, double height) {
        CanvasElement rectangle = ShapeFactory.getShape(getCanvas(),"RECTANGLE", x, y, color, width, height);
        commandSender.execute(new DrawElementCommand(canvas, rectangle));
        handleElementSelection();
    }

    public void addEllipseToCanvas(double x, double y, Color color, double width, double height) {
        CanvasElement ellipse = ShapeFactory.getShape(getCanvas(),"Ellipse", x, y, color, width, height);
        commandSender.execute(new DrawElementCommand(canvas, ellipse));
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

    public void fillColorChanged(Color color) {
        selectedFillColor = color;
        if (canvas.getSelectedCanvasElements().size() < 1) return;
        commandSender.execute(new ChangeFillColorOfElementsCommand(canvas.getSelectedCanvasElements(), color));
    }
    public Color getSelectedFillColor() {
        return selectedFillColor;
    }

    public void changeSelectedShapesWidth(double width) {
        // FIXME pressing on canvas will deselect element(s) before the width can be changed
        commandSender.execute(new SetWidthOfElementsCommand(canvas.getSelectedCanvasElements(), width));
        handleElementSelection();
    }
    public void changeSelectedShapesHeight(double height) {
        // FIXME pressing on canvas will deselect element(s) before the height can be changed
        commandSender.execute(new SetHeightOfElementsCommand(canvas.getSelectedCanvasElements(), height));
        handleElementSelection();
    }


    public void changeBorderColor(Color color) {
        //
    }
    public Color getSelectedBorderColor() {
        return selectedBorderColor;
    }

    public void changeBorderThickness(double thickness) {
        //
    }

    private void convertToBorderDecorator(CanvasElement element, BorderStyle borderStyle, double borderThickness, Color borderColor) {

    }
}
