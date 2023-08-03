package main.dialogs;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import main.models.Model;

public class CanvasInitDialog {
    private boolean titleValid = false;
    private boolean widthValid = false;
    private boolean heightValid = false;

    private Model model;
    private Pane parent;

    public CanvasInitDialog(Model model, Pane parent) {
        this.model = model;
        this.parent = parent;
    }

    public void showDialog() {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Canvas");

        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

        // Set the button types.
        ButtonType confirmBtnType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmBtnType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextField widthField = new TextField();
        widthField.setPromptText("Width");

        TextField heightField = new TextField();
        heightField.setPromptText("Height");

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Width:"), 0, 1);
        grid.add(widthField, 1, 1);
        grid.add(new Label("Height:"), 0, 2);
        grid.add(heightField, 1, 2);

        Node confirmBtn = dialog.getDialogPane().lookupButton(confirmBtnType);
        confirmBtn.setDisable(true);

        // Do some validation
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            // TODO: Expand list of acceptable characters (preferably a clean solution)
            titleValid = !newValue.trim().isEmpty() && newValue.matches("[A-Za-z0-9_ÄäËëÖöÏïÜüÿÂâÊêÔôÎîÛûÁáÉéÓóÍíÚúÝýÀàÈèÒòÌìÙù!@#$^&()+={} ]+");
            confirmBtn.setDisable(!(titleValid && widthValid && heightValid));
        });
        widthField.textProperty().addListener((observable, oldValue, newValue) -> {
            widthValid = !newValue.trim().isEmpty() && newValue.matches("[0-9]+");
            confirmBtn.setDisable(!(titleValid && widthValid && heightValid));
        });
        heightField.textProperty().addListener((observable, oldValue, newValue) -> {
            heightValid = !newValue.trim().isEmpty() && newValue.matches("[0-9]+");
            confirmBtn.setDisable(!(titleValid && widthValid && heightValid));
        });


        dialog.getDialogPane().setContent(grid);

        Platform.runLater(titleField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmBtnType) {
                if (!titleField.getText().matches("[A-Za-z0-9_ÄäËëÖöÏïÜüÿÂâÊêÔôÎîÛûÁáÉéÓóÍíÚúÝýÀàÈèÒòÌìÙù!@#$^&()+={} ]+") || !widthField.getText().matches("[0-9]+") || !heightField.getText().matches("[0-9]+")) return null;

                model.createCanvas(titleField.getText(), Double.parseDouble(widthField.getText()), Double.parseDouble(heightField.getText()));
				model.createSelectionArea();

                dialog.close();
            }
            return null;
        });

        dialog.showAndWait();
    }
}
