package main.dialogs;

import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import main.models.Model;

public class SaveBeforeClosingDialog {
    private final Model model;
    private final Pane CanvasParent;

    public SaveBeforeClosingDialog(Model model, Pane CanvasParent) {
        this.model = model;
        this.CanvasParent = CanvasParent;
    }

    public void showDialog(boolean promptNew) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Current project has been modified");
        alert.setHeaderText("Save Current Project?");

        ButtonType yesBtn = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yesBtn, noBtn, cancelBtn);

        alert.showAndWait().ifPresent(type -> {
            if (type == cancelBtn) {
                alert.close();
                return;
            }
            if (type == yesBtn) {
                // Save Project
            }

            // Remove Canvas
            model.closeCanvas();

            alert.close();
            if (promptNew) {
                // Create new Canvas
                new CanvasInitDialog(model, CanvasParent).showDialog();
            }
            else {
                model.getStage().close();
            }
        });
    }
}
