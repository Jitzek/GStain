package main;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.Tool;
import main.canvasElements.shapes.Ellipse;
import main.canvasElements.shapes.Rectangle;
import main.commands.Command;
import main.commands.compoundCommands.DragCompoundCommand;
import main.commands.shapeCommands.ellipseCommands.DrawEllipseCommand;
import main.commands.shapeCommands.rectangleCommands.DrawRectangleCommand;
import main.factories.ShapeFactory;
import main.strategies.canvasElementStrategies.DragCompoundStrategy;

import java.util.ArrayList;

public class Model {
    private final CommandSender commandSender;
    private final Canvas canvas;
    private final Controller parent;

    private boolean dragging;
    private boolean legalDragPivot = false;

    private double mouseDragStartX;
    private double mouseDragStartY;
    private double mouseDragContinuousX;
    private double mouseDragContinuousY;
    private double mouseDragEndX;
    private double mouseDragEndY;

    public Model(Controller parent) {
        this.parent = parent;
        commandSender = new CommandSender();
        canvas = Canvas.getInstance();
        configureCanvasEventHandlers(canvas);
    }

    private void setSelectedText(ArrayList<CanvasElement> elements) {
        CanvasElement element = null;
        int count = 0;
        for (CanvasElement ce : elements) {
            if (ce.isSelected()) {
                if (count++ > 1) continue;
                element = ce;
            }
        }
        if (count > 1) parent.selectedElement.setText("Compound");
        else if (count < 1) parent.selectedElement.setText("No Selection");
        else parent.selectedElement.setText(element.getClass().getSimpleName());
    }

    private void configureCanvasEventHandlers(Canvas canvas) {
        /*
            SELECTION RULES:

            Selection is based on hierarchy, first element in group has priority

            When shift is being hold
                - Elements which are selected and clicked on will be deselected
                - Elements which are selected and NOT clicked on will stay selected

            When shift is NOT being hold:
                - All elements which are not clicked on will be deselected
        */
        // Handle Mouse Dragged Event
        canvas.setOnMousePressed(mouseEvent -> {
            if (parent.getTool() != Tool.POINTER) {
                double width = 100;
                double height = 100;
                Color color = Color.BLACK;
                // Handle different tool selections
                switch (parent.getTool()) {
                    case RECTANGLE:
                        addRectangleToCanvas(mouseEvent.getX(), mouseEvent.getY(), color, width, height);
                        return;
                    case ELLIPSE:
                        addEllipseToCanvas(mouseEvent.getX(), mouseEvent.getY(), color, width, height);
                        return;
                    default: return;
                }
            }
            boolean shiftDown = Main.currentlyActiveKeys.contains(KeyCode.SHIFT);
            boolean overlapping = false; // Mouse overlaps with element
            for (CanvasElement ce : canvas.getCanvasElements()) {
                // If shape overlaps the current mouse position
                if (ce.overlaps(mouseEvent.getX(), mouseEvent.getY(), 0, 0)) {
                    overlapping = true;
                    // If shape is selected and shift is being held
                    if (ce.isSelected() && shiftDown) {
                        // Deselect element
                        ce.deselect();
                    }
                    // If not selected and shift is down
                    else if (!ce.isSelected() && shiftDown) {
                        // Select element
                        ce.select();
                    }
                    // If not selected and shift is not down
                    else if (!ce.isSelected() && !shiftDown) {
                        for (CanvasElement ce1 : canvas.getCanvasElements()) {
                            // Deselect all other elements
                            if (ce1 != ce) ce1.deselect();
                        }
                        // Select element
                        ce.select();
                    }
                    // Selected and shift is not down
                    else if (ce.isSelected() && !shiftDown) {
                        // Nothing
                    }
                    break;
                }
            }
            // If no elements correspond to mouse position
            if (!overlapping && !shiftDown) {
                // Deselect all elements
                for (CanvasElement ce : canvas.getCanvasElements()) {
                    ce.deselect();
                }
            }

            setSelectedText(canvas.getCanvasElements());

            mouseDragStartX = mouseEvent.getX();
            mouseDragStartY = mouseEvent.getY();

            mouseDragContinuousX = mouseDragStartX;
            mouseDragContinuousY = mouseDragStartY;
        });

        canvas.setOnMouseDragged(mouseEvent -> {
            if (!dragging) {
                for (CanvasElement ce : canvas.getCanvasElements()) {
                    if (ce.isSelected()) {
                        if (ce.overlaps(mouseEvent.getX(), mouseEvent.getY(), 0, 0)) {
                            legalDragPivot = true;
                        }
                    }
                }
            }
            if (!legalDragPivot) return;
            dragging = true;
            mouseDragEndX = mouseEvent.getX();
            mouseDragEndY = mouseEvent.getY();

            // Make all selected shapes one compound
            Compound compound = new Compound();
            for (CanvasElement ce : canvas.getCanvasElements()) {
                if (ce.isSelected()) {
                    ce.hide(canvas);
                    compound.addChild(ce);
                }
            }
            new DragCompoundStrategy().drag(canvas, compound, mouseDragEndX - mouseDragContinuousX, mouseDragEndY - mouseDragContinuousY);
            mouseDragContinuousX = mouseDragEndX;
            mouseDragContinuousY = mouseDragEndY;
        });

        // Handle Mouse Release Event
        canvas.setOnMouseReleased(mouseEvent -> {
            // Prevent unnecessary commands
            if (!dragging) {
                return;
            }
            // Make all selected shapes one compound
            Compound compound = new Compound();
            //ArrayList<Shape> selectedShapes = new ArrayList<>();
            for (CanvasElement ce : canvas.getCanvasElements()) {
                if (ce.isSelected()) {
                    compound.addChild(ce);
                    //selectedShapes.add((Shape) ce);
                    ce.show(canvas);
                }
            }
            // Reset position
            new DragCompoundCommand(canvas, compound, mouseDragStartX - mouseDragEndX, mouseDragStartY - mouseDragEndY).execute();

            // Fully reposition to safe command for undo/redo
            commandSender.execute(new DragCompoundCommand(canvas, compound, mouseDragEndX - mouseDragStartX, mouseDragEndY - mouseDragStartY));

            dragging = false;
        });

        // Handle Double Click Event
        canvas.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    System.out.println("Double clicked");
                }
            }
        });
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void undoCommand() {
        commandSender.undo();
    }

    public void redoCommand() {
        commandSender.redo();
    }

    public void addRectangleToCanvas(double x, double y, Color color, double width, double height) {
        Rectangle rectangle = (Rectangle) ShapeFactory.getShape("RECTANGLE", x, y, color, width, height);
        Command command = new DrawRectangleCommand(canvas, rectangle);
        commandSender.execute(command);
    }

    public void addEllipseToCanvas(double x, double y, Color color, double width, double height) {
        Ellipse ellipse = (Ellipse) ShapeFactory.getShape("Ellipse", x, y, color, width, height);
        Command command = new DrawEllipseCommand(canvas, ellipse);
        commandSender.execute(command);
    }
}
