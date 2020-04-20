package main.models;

import main.tools.ToolType;

public class ToolModel {
    private ToolType tool = ToolType.POINTER;
    private boolean isDragging;
    private boolean hasLegalDragPivot;

    private double mouseDragStartX;
    private double mouseDragStartY;
    private double mouseDragContinuousX;
    private double mouseDragContinuousY;
    private double mouseDragEndX;
    private double mouseDragEndY;

    public ToolModel() {
    }

    public ToolType getTool() {
        return tool;
    }

    public void setTool(ToolType tool) {
        this.tool = tool;
    }

    public void isDragging(boolean isDragging) {
        this.isDragging = isDragging;
    }
    public boolean isDragging() {
        return this.isDragging;
    }

    public void hasLegalDragPivot(boolean hasLegalDragPivot) {
        this.hasLegalDragPivot = hasLegalDragPivot;
    }
    public boolean hasLegalDragPivot() {
        return hasLegalDragPivot;
    }

    public double getMouseDragStartX() { return mouseDragStartX; }
    public void setMouseDragStartX(double x) {
        mouseDragStartX = x;
    }

    public double getMouseDragStartY() {
        return mouseDragStartY;
    }
    public void setMouseDragStartY(double y) {
        mouseDragStartY = y;
    }

    public double getMouseDragContinuousX() {
        return mouseDragContinuousX;
    }
    public void setMouseDragContinuousX(double x) {
        mouseDragContinuousX = x;
    }

    public double getMouseDragContinuousY() {
        return mouseDragContinuousY;
    }
    public void setMouseDragContinuousY(double y) {
        mouseDragContinuousY = y;
    }

    public double getMouseDragEndX() {
        return mouseDragEndX;
    }
    public void setMouseDragEndX(double x) {
        mouseDragEndX = x;
    }

    public double getMouseDragEndY() {
        return mouseDragEndY;
    }
    public void setMouseDragEndY(double y) {
        mouseDragEndY = y;
    }
}
