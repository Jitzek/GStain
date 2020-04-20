package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.dialogs.CanvasInitDialog;
import main.dialogs.SaveBeforeClosingDialog;
import main.tools.ToolType;
import main.eventHandlers.MouseDraggedOnCanvasEventHandler;
import main.eventHandlers.MousePressedOnCanvasEventHandler;
import main.eventHandlers.MouseReleasedOnCanvasEventHandler;
import main.models.Model;

public class Controller {
    @FXML
    HBox MonitorArea;
    @FXML
    Label selectedElementLabel;
    @FXML
    GridPane MainWindow;
    @FXML
    ScrollPane CanvasArea;
    @FXML
    ColorPicker FillColorPicker;
    @FXML
    ColorPicker BorderColorPicker;

    private Model model;
    private final StackPane CanvasHolder = new StackPane();
    private boolean canvasLoaded = false;

    public void init(Stage stage) {
        model = new Model(stage, selectedElementLabel);

        CanvasArea.setPrefSize(model.getScene().getWidth(), model.getScene().getWidth());
        CanvasArea.setStyle("-fx-background-color: #3B3B3B");
        configureSceneEventHandlers(model.getScene());
    }

    /**
     * Configure event handlers for this window
     * @param scene Window to be configured
     */
    private void configureSceneEventHandlers(Scene scene) {
        // Configure common shortcuts
        scene.setOnKeyPressed(event -> {
            if (!model.isKeyActive(event.getCode())) model.addActiveKey(event.getCode());

            // Ctrl+Z
            if (new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN).match(event)) model.undoCommand();

            // Ctrl+Shift+Z
            else if (new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN).match(event)) model.redoCommand();

            // CTRL+C
            else if (new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN).match(event)) model.copySelectedElements();

            // CTRL+X
            else if (new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN).match(event)) model.cutSelectedElements();

            //  CTRL+V
            else if (new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN).match(event)) model.pasteSelectedElements();

            // Backspace
            else if (model.isKeyActive(KeyCode.BACK_SPACE)) model.removeSelectedElementsFromCanvas();

            // CTRL+G
            else if (new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN).match(event)) model.groupSelectedElements();
        });
        scene.setOnKeyReleased(event -> model.removeActiveKey(event.getCode()));
    }

    public void handleCreateCanvas() {
        // Create Canvas Container
        CanvasArea.setContent(CanvasHolder);

        if (canvasLoaded) {
            // Handle closing and creating canvas
            new SaveBeforeClosingDialog(model, CanvasHolder).showDialog(true);
            return;
        }

        // CanvasInitDialog handles Canvas Creation and Event handlers assignment
        new CanvasInitDialog(model, CanvasHolder).showDialog();

        MonitorArea.getChildren().add((new CanvasMonitor(model.getCanvas()).getReporter()));

        canvasLoaded = true;
    }

    public void handlePointerToolSelect() {
        model.getToolModel().setTool(ToolType.POINTER);
    }

    public void handleRectangleToolSelect() {
        model.getToolModel().setTool(ToolType.RECTANGLE);
    }

    public void handleEllipseToolSelect() {
        model.getToolModel().setTool(ToolType.ELLIPSE);
    }

    public void handleUndo() { model.undoCommand(); }

    public void handleRedo() {
        model.redoCommand();
    }

    public void handleCut() {  }

    public void handleCopy() { model.copySelectedElements(); }

    public void handlePaste() { model.pasteSelectedElements(); }

    public void handleRemove() { model.removeSelectedElementsFromCanvas(); }

    public void handleGroup() {
        model.groupSelectedElements();
    }

    public void handleUngroup() {
        model.ungroupSelectedElements();
    }
}
