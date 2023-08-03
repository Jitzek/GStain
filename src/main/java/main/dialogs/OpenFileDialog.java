package main.dialogs;


import javafx.stage.FileChooser;
import main.models.Model;

import java.io.File;

public class OpenFileDialog {
    private final Model model;

    public OpenFileDialog(Model model) {
        this.model = model;
    }

    public File OpenFile(){
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showOpenDialog(model.getStage());
    }


}
