package main.models;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.canvasElements.SelectionArea;
import main.canvasElements.decorators.border.BorderDecorator;
import main.Canvas;
import main.CommandSender;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.SelectionBox;
import main.commands.canvasElementCommands.*;
import main.commands.canvasElementCommands.borderCommands.ChangeColorOfBordersCommand;
import main.commands.canvasElementCommands.borderCommands.ChangeThicknessOfBordersCommand;
import main.commands.canvasElementCommands.compoundCommands.ConvertToCompoundCommand;
import main.commands.canvasElementCommands.compoundCommands.ConvertToElementsCommand;
import main.dialogs.OpenFileDialog;
import main.eventHandlers.MouseDraggedOnCanvasEventHandler;
import main.eventHandlers.MousePressedOnCanvasEventHandler;
import main.eventHandlers.MouseReleasedOnCanvasEventHandler;
import main.factories.CanvasElementCloneFactory;
import main.factories.ShapeFactory;
import main.fileio.Export;
import main.fileio.Import;
import main.tools.ToolType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Model {
    private Canvas canvas;
    private SelectionBox selectionBox;
    private StackPane canvasHolder;
    private final ArrayList<CanvasElement> copiedElements = new ArrayList<>();
    private final ArrayList<KeyCode> activeKeys = new ArrayList<>();
    private final ToolModel toolModel = new ToolModel();
    private CommandSender commandSender;
    private final Stage stage;
    private Color selectedFillColor = Color.BLACK;
    private Color selectedBorderColor = Color.BLACK;
    private double selectedBorderThickness = 0.0;
	private double lastStrokeSize;
    private double lastShapeWidth;
    private double lastShapeHeight;
	private final TextField StrokeSize;
    private final TextField ShapeWidth;
    private final TextField ShapeHeight;
    private String filepath;

    private final Label selectedElementLabel;

    public Model(Stage stage, StackPane canvasHolder, Label selectedElementLabel, TextField StrokeSize, TextField ShapeWidth, TextField ShapeHeight) {
        this.stage = stage;
        this.canvasHolder = canvasHolder;
        this.selectedElementLabel = selectedElementLabel;
        commandSender = new CommandSender();
        canvas = Canvas.getInstance();
		this.StrokeSize = StrokeSize;
        this.ShapeWidth = ShapeWidth;
        this.ShapeHeight = ShapeHeight;
    }

    public void createCanvas(String title, double width, double height) {
        // Define Canvas
        Canvas canvas = getCanvas();

        // Configure Canvas
        canvas.setName(title);
        setStageTitle(canvas.getName());
        configureCanvasEventHandlers(canvas, this);
        canvasHolder.getChildren().add(canvas);

        // Define size of Canvas
        canvas.setMinSize(width, height);
        canvas.setMaxSize(width, height);

        // Canvas Styling
        canvas.setStyle("-fx-background-color: #ffffff");

        getToolModel().setTool(ToolType.POINTER);
    }

    public void createSelectionArea(){
        SelectionArea selectionArea = SelectionArea.getSelectionArea();
        canvas.getChildren().add(selectionArea.getPath());
    }

    private void configureCanvasEventHandlers(Canvas canvas, Model model) {
        /*
            SELECTION RULES:

            Selection is based on hierarchy, first element in group has priority

            When shift is being hold
                - Elements which are selected and clicked on will be deselected
                - Elements which are selected and NOT clicked on will stay selected

            When shift is NOT being hold:
                - All elements which are not clicked on will be deselected
        */
        // Handle Mouse Pressed Event
        MousePressedOnCanvasEventHandler mousePressedOnCanvasEventHandler = new MousePressedOnCanvasEventHandler(model);
        canvas.setOnMousePressed(mousePressedOnCanvasEventHandler);

        // Handle Mouse Dragged Event
        MouseDraggedOnCanvasEventHandler mouseDraggedOnCanvasEventHandler = new MouseDraggedOnCanvasEventHandler(model);
        canvas.setOnMouseDragged(mouseDraggedOnCanvasEventHandler);

        // Handle Mouse Released Event
        MouseReleasedOnCanvasEventHandler mouseReleasedOnCanvasEventHandler = new MouseReleasedOnCanvasEventHandler(model);
        canvas.setOnMouseReleased(mouseReleasedOnCanvasEventHandler);
    }

    public void closeCanvas() {
        // Remove GUI element
        canvasHolder.getChildren().remove(canvas);

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

	public double getLastStrokeSize() {
        return lastStrokeSize;
    }
    public void setLastStrokeSize(double stroke) {
        lastStrokeSize = stroke;
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
            if (canvas.getCanvasElementAt(index, false, false) instanceof BorderDecorator) {
                StrokeSize.setText(String.valueOf(((BorderDecorator) canvas.getCanvasElementAt(index, false, false)).getBorderThickness()));
            }
            ShapeWidth.setText(String.valueOf(canvas.getCanvasElementAt(index, false, false).getWidth()));
            ShapeHeight.setText(String.valueOf(canvas.getCanvasElementAt(index, false, false).getHeight()));
        } else {
            StrokeSize.setText("");
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

    public void changeSelectedShapesStroke(double stroke) {
        if (canvas.getSelectedCanvasElements().size() < 1) return;
        // FIXME pressing on canvas will deselect element(s) before the stroke can be changed
        commandSender.execute(new ChangeThicknessOfBordersCommand(canvas.getSelectedCanvasElements(), stroke));
        handleElementSelection();
    }
    public void changeSelectedShapesWidth(double width) {
        if (canvas.getSelectedCanvasElements().size() < 1) return;
        // FIXME pressing on canvas will deselect element(s) before the width can be changed
        commandSender.execute(new SetWidthOfElementsCommand(canvas.getSelectedCanvasElements(), width));
        handleElementSelection();
    }
    public void changeSelectedShapesHeight(double height) {
        if (canvas.getSelectedCanvasElements().size() < 1) return;
        // FIXME pressing on canvas will deselect element(s) before the height can be changed
        commandSender.execute(new SetHeightOfElementsCommand(canvas.getSelectedCanvasElements(), height));
        handleElementSelection();
    }


    public void changeBorderColor(Color color) {
        if (canvas.getSelectedCanvasElements().size() < 1) return;
        ArrayList<BorderDecorator> toBeRecolored = new ArrayList<>();
        for (CanvasElement element : canvas.getSelectedCanvasElements()) {
            if (element instanceof BorderDecorator) toBeRecolored.add((BorderDecorator) element);
        }
        commandSender.execute(new ChangeColorOfBordersCommand(toBeRecolored, color));
    }
    public Color getSelectedBorderColor() {
        return selectedBorderColor;
    }

    public boolean unsavedChanges() {
        return commandSender.getCommands().size() > 0;
    }

    public void importGurbe(StackPane canvasHolder){
        OpenFileDialog openFileDialog = new OpenFileDialog(this);
        File file = openFileDialog.OpenFile();
        if(file == null){
            System.out.println("No file selected");
            return;
        }
        Import anImport = new Import(canvas,this, file);
        createSelectionArea();
        anImport.importProject();
        //anImport.traverseGroup(canvas.getCanvasElements());
        anImport.drawAll();
        filepath = anImport.getFile().getPath();
    }

    /**
     * exports the current project to a .gurbe file
     */
    public void export() {
        if(filepath == null){
            exportAs();
        }
        else{
            Export exportVisitor = new Export();
            System.out.println(exportVisitor.export(canvas.getCanvasElements(), canvas));
            File file = new File(filepath);
            try{
                if(file.createNewFile()){
                    System.out.println("File created");
                }
                else{
                    System.out.println("File already exists");
                }
            }
            catch (IOException e){
                System.out.println("An error occurred");
                e.printStackTrace();
            }
            try{
                FileWriter fileWriter = new FileWriter(filepath);
                fileWriter.write(exportVisitor.export(canvas.getCanvasElements(), canvas));
                fileWriter.close();
            }
            catch(IOException e){
                System.out.println("An error occurred");
                e.printStackTrace();
            }
        }
    }
    public void exportAs(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as");
        fileChooser.setInitialFileName(canvas.getName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("gurbe Files", "*.gurbe"));
        try {
            File file = fileChooser.showSaveDialog(stage);
            if(file != null){
                filepath = file.getPath();
                Export exportVisitor = new Export();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(exportVisitor.export(canvas.getCanvasElements(), canvas));
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SelectionArea getSelectionArea() {
        return SelectionArea.getSelectionArea();
    }

    public void setSelectionBox(SelectionBox selectionBox) {
        this.selectionBox = selectionBox;
    }
}
