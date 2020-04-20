package main.dialogs;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import main.models.Model;
import main.models.ToolModel;
import main.tools.ToolType;

import java.util.Optional;

public class ShapeDialog {
    private final Model model;
    private final ToolModel toolModel;
    private final String shape;
    private final double x;
    private final double y;
    private final Color color;

    private boolean widthValid = false;
    private boolean heightValid = false;

    public ShapeDialog(Model model, ToolModel toolModel, String shape, double x, double y, Color color) {
        this.model = model;
        this.toolModel = toolModel;
        this.shape = shape;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void showDialog() {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        switch (shape.toUpperCase()) {
            case "RECTANGLE":
                dialog.setTitle("Rectangle");
                break;
            case "ELLIPSE":
                dialog.setTitle("Ellipse");
                break;
        }
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

        // Set the button types.
        ButtonType confirmBtnType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmBtnType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField widthField = new TextField();
        widthField.setPromptText("Width");
        TextField heightField = new TextField();
        heightField.setPromptText("Height");

        grid.add(new Label("Width:"), 0, 0);
        grid.add(widthField, 1, 0);
        grid.add(new Label("Height:"), 0, 1);
        grid.add(heightField, 1, 1);

        Node confirmBtn = dialog.getDialogPane().lookupButton(confirmBtnType);
        confirmBtn.setDisable(true);

        // Do some validation
        widthField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty() && newValue.matches("[0-9]+")) {
                widthValid = true;
                confirmBtn.setDisable(!heightValid);
            } else {
                widthValid = false;
                confirmBtn.setDisable(true);
            }
        });
        heightField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty() && newValue.matches("[0-9]+")) {
                heightValid = true;
                confirmBtn.setDisable(!widthValid);
            } else {
                heightValid = false;
                confirmBtn.setDisable(true);
            }
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(widthField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmBtnType) {
                if (!widthField.getText().matches("[0-9]+") && !heightField.getText().matches("[0-9]+")) return null;
                switch (shape.toUpperCase()) {
                    case "RECTANGLE":
                        model.addRectangleToCanvas(x, y, color, Double.parseDouble(widthField.getText()), Double.parseDouble(heightField.getText()));
                        break;
                    case "ELLIPSE":
                        model.addEllipseToCanvas(x, y, color, Double.parseDouble(widthField.getText()), Double.parseDouble(heightField.getText()));
                        break;
                }
                toolModel.setTool(ToolType.POINTER);
                dialog.close();
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
    }
}
