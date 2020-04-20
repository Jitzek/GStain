package main.eventHandlers;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import main.CC;
import main.Controller;
import main.canvasElements.Compound;
import main.dialogs.EllipseDialog;
import main.dialogs.RectangleDialog;
import main.models.Model;
import main.canvasElements.CanvasElement;
import main.models.ToolModel;

public class MousePressedOnCanvasEventHandler implements EventHandler<MouseEvent>, MouseOnCanvasEventHandler {
    private final Model model;

    public MousePressedOnCanvasEventHandler(Model model) {
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
            Select overlapping elements and/or deselect non-overlapping elements
         */

        boolean isShiftDown = model.isKeyActive(KeyCode.SHIFT);
        boolean overlapping = false; // Mouse overlaps with element
        for (CanvasElement ce : model.getCanvas().getCanvasElements()) {
            // If shape overlaps the current mouse position
            if (ce.overlaps(mouseEvent.getX(), mouseEvent.getY(), 0, 0)) {
                overlapping = true;
                // If shape is selected and shift is being held
                if (ce.isSelected() && isShiftDown) {
                    // Deselect element
                    ce.deselect();
                }
                // If not selected and shift is down
                else if (!ce.isSelected() && isShiftDown) {
                    // Select element
                    ce.select();
                }
                // If not selected and shift is not down
                else if (!ce.isSelected() && !isShiftDown) {
                    for (CanvasElement ce1 : model.getCanvas().getCanvasElements()) {
                        // Deselect all other elements
                        if (ce1 != ce) ce1.deselect();
                    }
                    // Select element
                    ce.select();
                }
                // Selected and shift is not down
                else if (ce.isSelected() && !isShiftDown) {
                    // Nothing
                }
                break;
            }
        }
        // If no elements correspond to mouse position (and shift is not down)
        if (!overlapping && !isShiftDown) {
            // Deselect all elements
            for (CanvasElement ce : model.getCanvas().getCanvasElements()) {
                ce.deselect();
            }
        }

        for (CanvasElement ce : model.getCanvas().getSelectedCanvasElements()) {
            System.out.println(CC.CYAN.getColor() + ce.getClass().getSimpleName() + CC.RESET.getColor());
        }

        model.getToolModel().setMouseDragStartX(mouseEvent.getX());
        model.getToolModel().setMouseDragStartY(mouseEvent.getY());

        model.getToolModel().setMouseDragContinuousX(model.getToolModel().getMouseDragStartX());
        model.getToolModel().setMouseDragContinuousY(model.getToolModel().getMouseDragStartY());
    }

    @Override
    public void handleRectangleTool(MouseEvent mouseEvent) {
        /*
            Add Rectangle to Canvas
         */

        // Get Data
        Color color = Color.BLACK;
        new RectangleDialog(model, mouseEvent.getX(), mouseEvent.getY(), color).showDialog();
        model.getCanvas().deselectAllElements();
    }

    @Override
    public void handleEllipseTool(MouseEvent mouseEvent) {
        /*
            Add Ellipse to Canvas
         */

        // Get Data
        Color color = Color.BLACK;
        new EllipseDialog(model, mouseEvent.getX(), mouseEvent.getY(), color).showDialog();
        model.getCanvas().deselectAllElements();
    }
}
