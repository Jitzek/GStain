package main;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.dialogs.CanvasInitDialog;
import main.dialogs.SaveBeforeClosingDialog;
import main.tools.ToolType;
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
    @FXML
    TextField Stroke;
    @FXML
    TextField ShapeWidth;
    @FXML
    TextField ShapeHeight;
    @FXML
    StackPane CanvasHolder;

    private Model model;
    private boolean canvasLoaded = false;

    public void init(Stage stage) {
		model = new Model(stage, CanvasHolder, selectedElementLabel, Stroke, ShapeWidth, ShapeHeight);

        CanvasArea.setPrefSize(model.getScene().getWidth(), model.getScene().getWidth());
        CanvasArea.setStyle("-fx-background-color: #3B3B3B");
        configureStageEventHandlers(model.getStage());
        configureSceneEventHandlers(model.getScene());
        configureMiscEventHandlers();
    }

    private void configureStageEventHandlers(Stage stage) {
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    private void closeWindowEvent(WindowEvent event) {
        if (model.unsavedChanges()) {
            event.consume();
            new SaveBeforeClosingDialog(model, CanvasHolder).showDialog(false);
        }
    }

    /**
     * Configure event handlers for this window
     *
     * @param scene Window to be configured
     */
    private void configureSceneEventHandlers(Scene scene) {
        // Configure common shortcuts
        scene.setOnKeyPressed(event -> {
            if (!model.isKeyActive(event.getCode())) model.addActiveKey(event.getCode());

            // Ctrl+Z
            if (new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN).match(event)) model.undoCommand();

                // Ctrl+Shift+Z
            else if (new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN).match(event))
                model.redoCommand();

                // CTRL+C
            else if (new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN).match(event))
                model.copySelectedElements();

                // CTRL+X
            else if (new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN).match(event))
                model.cutSelectedElements();

                //  CTRL+V
            else if (new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN).match(event))
                model.pasteSelectedElements();

                // Backspace
            else if (model.isKeyActive(KeyCode.BACK_SPACE)) model.removeSelectedElementsFromCanvas();

                // CTRL+G
            else if (new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN).match(event))
                model.groupSelectedElements();

                // CTRL+Shift+G
            else if (new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN).match(event))
                model.ungroupSelectedElements();
        });
        scene.setOnKeyReleased(event -> model.removeActiveKey(event.getCode()));
    }

    private void configureMiscEventHandlers() {
        /*ShapeWidth.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) changeWidth();
        });*/
        ShapeWidth.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) changeWidth();
        });

        /*ShapeHeight.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) changeHeight();
        });*/
        ShapeHeight.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) changeHeight();
        });

        /*Stroke.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) changeStroke();
        });*/
        Stroke.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) changeStroke();
        });
    }

    public void handleCreateCanvas() {
        if (canvasLoaded) {
            // Handle closing and creating canvas
            if (model.unsavedChanges()) {
                new SaveBeforeClosingDialog(model, CanvasHolder).showDialog(true);
				return;
            }
			model.closeCanvas();
        }

        // CanvasInitDialog handles Canvas Creation and Event handlers assignment
        new CanvasInitDialog(model, CanvasHolder).showDialog();

        MonitorArea.getChildren().add((new CanvasMonitor(model.getCanvas()).getReporter()));

        canvasLoaded = true;
    }

    public void handleOpen(){
        model.importGurbe(CanvasHolder);
    }

    public void handleSave(){
        model.export();
    }
    public void handleSaveAs() {
        model.exportAs();
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

    public void handleUndo() {
        model.undoCommand();
    }

    public void handleRedo() {
        model.redoCommand();
    }

    public void handleCut() {
        model.cutSelectedElements();
    }

    public void handleCopy() {
        model.copySelectedElements();
    }

    public void handlePaste() {
        model.pasteSelectedElements();
    }

    public void handleRemove() {
        model.removeSelectedElementsFromCanvas();
    }

    public void handleGroup() {
        model.groupSelectedElements();
    }

    public void handleUngroup() {
        model.ungroupSelectedElements();
    }

    public void handleFillColorPicked() {
        model.fillColorChanged(FillColorPicker.getValue());
    }

    public void handleBorderColorPicked() {
        model.changeBorderColor(BorderColorPicker.getValue());
    }

    public void handleStrokeChange() {
        if (!Stroke.getText().matches("[0-9.]+")) return;
        model.setLastStrokeSize(Double.parseDouble(Stroke.getText()));
    }
	
	private void changeStroke() {
        model.changeSelectedShapesStroke(model.getLastStrokeSize());
    }

    public void handleWidthChange() {
        if (!ShapeWidth.getText().matches("[0-9.]+")) return;
        model.setLastShapeWidth(Double.parseDouble(ShapeWidth.getText()));
    }

    private void changeWidth() {
        model.changeSelectedShapesWidth(model.getLastShapeWidth());
    }

    public void handleHeightChange() {
        if (!ShapeHeight.getText().matches("[0-9.]+")) return;
        model.setLastShapeHeight(Double.parseDouble(ShapeHeight.getText()));
    }

    private void changeHeight() {
        model.changeSelectedShapesHeight(model.getLastShapeHeight());
    }

    public void dev_btn_1() {
        model.printHierarchy();
    }

    public void dev_btn_2() {

    }
}
