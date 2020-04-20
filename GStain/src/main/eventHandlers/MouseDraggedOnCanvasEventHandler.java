package main.eventHandlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.commands.DragElementsCommand;
import main.models.Model;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.strategies.canvasElementStrategies.DragCompoundStrategy;
import main.strategies.canvasElementStrategies.DragElementsStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
        if (!model.getToolModel().hasLegalDragPivot()) return;

        model.getToolModel().isDragging(true);
        model.getToolModel().setMouseDragEndX(mouseEvent.getX());
        model.getToolModel().setMouseDragEndY(mouseEvent.getY());

        /* Configure real-time dragging */
        for (CanvasElement ce : model.getCanvas().getSelectedCanvasElements()) {
            ce.hide(model.getCanvas());
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
}
