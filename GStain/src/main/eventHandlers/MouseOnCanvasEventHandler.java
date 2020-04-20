package main.eventHandlers;


import javafx.scene.input.MouseEvent;

public interface MouseOnCanvasEventHandler {
    void handlePointerTool(MouseEvent mouseEvent);
    void handleRectangleTool(MouseEvent mouseEvent);
    void handleEllipseTool(MouseEvent mouseEvent);
}
