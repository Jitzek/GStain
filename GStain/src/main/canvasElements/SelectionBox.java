package main.canvasElements;

import javafx.scene.shape.Path;

import java.util.ArrayList;

public class SelectionBox {
    private final CanvasElement parent;
    private Path selectionStyle = null;
    private ArrayList<SelectionPoint> points = new ArrayList<>();

    public SelectionBox(CanvasElement parent) {
        this.parent = parent;
    }

    public CanvasElement getParent() {
        return parent;
    }

    public Path getSelectionStyle() {
        return selectionStyle;
    }

    public void setSelectionStyle(Path selectionStyle) {
        this.selectionStyle = selectionStyle;
    }

    public ArrayList<SelectionPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<SelectionPoint> points) {
        this.points = points;
    }

    public SelectionPoint getOverlappingPoint(double x, double y, double width, double height) {
        for (SelectionPoint sp : points) if (sp.overlaps(x, y, width, height)) return sp;
        return null;
    }

    public void drag(double x, double y) {
        getSelectionStyle().setTranslateX(getSelectionStyle().getTranslateX() + x);
        getSelectionStyle().setTranslateY(getSelectionStyle().getTranslateY() + y);

        for (var point : getPoints()) {
            point.getUiElement().setTranslateX(point.getUiElement().getTranslateX() + x);
            point.getUiElement().setTranslateY(point.getUiElement().getTranslateY() + y);
        }
    }
}
