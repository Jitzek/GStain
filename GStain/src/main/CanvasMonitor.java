package main;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CanvasMonitor {
    private final Pane canvas;
    private final Label reporter;

    private String mouseLocation;

    public CanvasMonitor(Pane canvas) {
        this.canvas = canvas;
        reporter = new Label("--");
        reporter.setTextFill(Color.valueOf("#aaaaaa"));
        configureCanvasMonitor();
    }

    public Label getReporter() {
        return this.reporter;
    }

    private void configureCanvasMonitor() {
        canvas.setOnMouseMoved(event -> {
            mouseLocation =
                    "(x: " + event.getX() + ", y: " + event.getY() + ")";
            getReporter().setText(mouseLocation);
        });

        canvas.setOnMouseExited(event -> {
            mouseLocation = "--";
            getReporter().setText(mouseLocation);
        });
    }

}
