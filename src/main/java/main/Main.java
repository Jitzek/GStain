package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Gurbe Stain");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();

        final Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);

        Image icon = new Image("main/view/images/Gurbe_Paint_alt.png");
        stage.getIcons().add(icon);

        stage.setMaximized(true);

        stage.show();

        // Define controller
        Controller controller = loader.getController();
        controller.init(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
