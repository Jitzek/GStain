package main.dialogs;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import main.models.Model;
import main.tools.ToolType;

import java.util.Optional;

public class RectangleDialog {
    private final Model model;
    private final double x;
    private final double y;
    private final Color color;

    private boolean widthValid = false;
    private boolean heightValid = false;

    public RectangleDialog(Model model, double x, double y, Color color) {
        this.model = model;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void showDialog() {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Rectangle");

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
            widthValid = !newValue.trim().isEmpty() && newValue.matches("[0-9]+");
            confirmBtn.setDisable(!(widthValid && heightValid));
        });
        heightField.textProperty().addListener((observable, oldValue, newValue) -> {
            heightValid = !newValue.trim().isEmpty() && newValue.matches("[0-9]+");
            confirmBtn.setDisable(!(widthValid && heightValid));
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(widthField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmBtnType) {
                if (!widthField.getText().matches("[0-9]+") && !heightField.getText().matches("[0-9]+")) return null;
                model.addRectangleToCanvas(x, y, color, Double.parseDouble(widthField.getText()), Double.parseDouble(heightField.getText()));
                model.getToolModel().setTool(ToolType.POINTER);
                dialog.close();
            }
            return null;
        });

        dialog.showAndWait();
    }
}
