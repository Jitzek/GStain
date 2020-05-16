package main.eventHandlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.commands.canvasElementCommands.DragElementsCommand;
import main.models.Model;
import main.canvasElements.CanvasElement;

public class MouseDraggedOnCanvasEventHandler implements EventHandler<MouseEvent>, MouseOnCanvasEventHandler {

    private final Model model;

    public MouseDraggedOnCanvasEventHandler(Model model) {
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
        if (!model.getToolModel().isDragging()) {
            // Check if element is dragged from a valid point on the canvas
            // Element needs to overlap this point
            model.getToolModel().hasLegalDragPivot(false);
            for (CanvasElement ce : model.getCanvas().getCanvasElements()) {
                if (ce.isSelected()) {
                    if (ce.overlaps(mouseEvent.getX(), mouseEvent.getY(), 0, 0)) {
                        model.getToolModel().hasLegalDragPivot(true);
                    }
                }
            }
        }
        model.getToolModel().isDragging(true);
        if (!model.getToolModel().hasLegalDragPivot()){
            //on every drag update set endpoint x,y of the selectionbox
            model.getSelectionBox().setEx(mouseEvent.getX());
            model.getSelectionBox().setEy(mouseEvent.getY());
            model.getSelectionBox().draw();
            return;
        }
        model.getToolModel().setMouseDragEndX(mouseEvent.getX());
        model.getToolModel().setMouseDragEndY(mouseEvent.getY());

        /* Configure real-time dragging */
        for (CanvasElement ce : model.getCanvas().getSelectedCanvasElements()) {
            ce.hide();
        }
        new DragElementsCommand(model.getCanvas(), model.getCanvas().getSelectedCanvasElements(), model.getToolModel().getMouseDragEndX() - model.getToolModel().getMouseDragContinuousX(), model.getToolModel().getMouseDragEndY() - model.getToolModel().getMouseDragContinuousY()).execute();
        model.getToolModel().setMouseDragContinuousX(model.getToolModel().getMouseDragEndX());
        model.getToolModel().setMouseDragContinuousY(model.getToolModel().getMouseDragEndY());
    }

    @Override
    public void handleRectangleTool(MouseEvent mouseEvent) {
        //
    }

    @Override
    public void handleEllipseTool(MouseEvent mouseEvent) {
        //
    }
    /*
    private void handleSelectionBox(MouseEvent mouseEvent){

    }
     */
}
