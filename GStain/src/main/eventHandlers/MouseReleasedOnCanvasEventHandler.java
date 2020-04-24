package main.eventHandlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.commands.canvasElementCommands.DragElementsCommand;
import main.models.Model;
import main.canvasElements.CanvasElement;

import java.util.ArrayList;

public class MouseReleasedOnCanvasEventHandler implements EventHandler<MouseEvent>, MouseOnCanvasEventHandler {
    private final Model model;

    public MouseReleasedOnCanvasEventHandler(Model model) {
        this.model = model;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        switch (model.getToolModel().getTool()) {
            case POINTER:
                handlePointerTool(mouseEvent);
                break;
            case RECTANGLE:
                handleRectangleTool(mouseEvent);
                break;
            case ELLIPSE:
                handleEllipseTool(mouseEvent);
                break;
        }
    }

    @Override
    public void handlePointerTool(MouseEvent mouseEvent) {
        /*
            Finalize drag
         */

        // Prevent unnecessary commands
        if (!model.getToolModel().isDragging()) {
            return;
        }

        ArrayList<CanvasElement> elements = new ArrayList<>();
        for (CanvasElement ce : model.getCanvas().getCanvasElements()) {
            if (ce.isSelected()) {
                ce.show();
                elements.add(ce);
            }
        }
        // Reset position
        new DragElementsCommand(model.getCanvas(), elements, model.getToolModel().getMouseDragStartX() - model.getToolModel().getMouseDragEndX(), model.getToolModel().getMouseDragStartY() - model.getToolModel().getMouseDragEndY()).execute();

        // Fully reposition to safe command for undo/redo
        model.dragElements(elements, model.getToolModel().getMouseDragEndX() - model.getToolModel().getMouseDragStartX(), model.getToolModel().getMouseDragEndY() - model.getToolModel().getMouseDragStartY());

        model.getToolModel().isDragging(false);
    }

    @Override
    public void handleRectangleTool(MouseEvent mouseEvent) {

    }

    @Override
    public void handleEllipseTool(MouseEvent mouseEvent) {

    }
}
