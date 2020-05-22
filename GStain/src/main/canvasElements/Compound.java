package main.canvasElements;

import javafx.scene.paint.Color;
import main.Canvas;
import main.canvasElements.decorators.border.BorderDecorator;
import main.strategies.canvasElementStrategies.deselect.DeselectElementStrategy;
import main.strategies.canvasElementStrategies.draw.DrawCompoundStrategy;
import main.strategies.canvasElementStrategies.select.SelectCompoundStrategy;
import main.strategies.canvasElementStrategies.size.ResizeCompoundStrategy;
import main.visitor.CanvasElementVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Compound implements CanvasElement {
    private final Canvas parent;
    private final ArrayList<CanvasElement> children;
    private UUID uuid;
    private boolean selected = false;
    private SelectionBox selectionBox = null;

    private double x;
    private double y;
    private double width;
    private double height;

    public Compound(Canvas parent) {
        this.parent = parent;
        this.uuid = UUID.randomUUID();
        children = new ArrayList<>();
    }

    public Compound(Canvas parent, ArrayList<CanvasElement> canvasElements) {
        this.parent = parent;
        this.uuid = UUID.randomUUID();
        children = canvasElements;

        configure_X_Y_Width_Height();
    }
    private void configure_X_Y_Width_Height() {
        double min_x = -1;
        double max_x = -1;
        double min_y = -1;
        double max_y = -1;
        for (CanvasElement child : getChildren()) {
            if ((min_x > child.getX() - child.getWidth()/2) || min_x == -1) {
                min_x = child.getX() - child.getWidth()/2;
                if (child instanceof BorderDecorator) min_x -= ((BorderDecorator) child).getBorderThickness()/2;
            }
            if ((max_x < child.getX() + child.getWidth()/2) || max_x == -1) {
                max_x = child.getX() + child.getWidth()/2;
                if (child instanceof BorderDecorator) max_x += ((BorderDecorator) child).getBorderThickness()/2;
            }
            if ((min_y > child.getY() - child.getHeight()/2) || min_y == -1) {
                min_y = child.getY() - child.getHeight()/2;
                if (child instanceof BorderDecorator) min_y -= ((BorderDecorator) child).getBorderThickness()/2;
            }
            if ((max_y < child.getY() + child.getHeight()/2) || max_y == -1) {
                max_y = child.getY() + child.getHeight()/2;
                if (child instanceof BorderDecorator) max_y += ((BorderDecorator) child).getBorderThickness()/2;
            }
        }

        this.width = Math.abs(max_x - min_x);
        this.height = Math.abs(max_y - min_y);

        this.x = min_x + width/2;
        this.y = min_y + height/2;
    }

    @Override
    public Canvas getParent() {
        return parent;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public void addChild(CanvasElement element) {
        children.add(element);
        configure_X_Y_Width_Height();
    }

    public void addChildren(CanvasElement... elements) {
        children.addAll(Arrays.asList(elements));
        configure_X_Y_Width_Height();
    }

    public void addChildren(ArrayList<CanvasElement> elements) {
        children.addAll(elements);
        configure_X_Y_Width_Height();
    }

    public void removeChild(CanvasElement element) {
        children.remove(element);
        configure_X_Y_Width_Height();
    }

    public void removeChildren(CanvasElement... elements) {
        children.removeAll(Arrays.asList(elements));
        configure_X_Y_Width_Height();
    }

    public void removeChildren(ArrayList<CanvasElement> elements) {
        children.removeAll(elements);
        configure_X_Y_Width_Height();
    }

    public void removeChildAt(int index) {
        children.remove(index);
        configure_X_Y_Width_Height();
    }

    public CanvasElement selectChildAt(int index) {
        return children.get(index);
    }

    public ArrayList<CanvasElement> getChildren() {
        return children;
    }

    public int getSize(boolean recursively) {
        if (!recursively) return children.size();
        int size = 0;
        for (CanvasElement child : children) {
            if (child instanceof Compound) size += ((Compound) child).getSize(true);
            else size++;
        }
        return size;
    }

    public int getIndexOfElement(CanvasElement element, boolean recursively) {
        if (!recursively) return children.indexOf(element);
        int index = 0;
        for (CanvasElement child : children) {
            if (child == element) return index;
            if (child instanceof Compound) {
                if (((Compound) child).containsElement(element, true)) {
                    return index + ((Compound) child).getIndexOfElement(element, true);
                }
                index += ((Compound) child).getSize(true);
            } else {
                index++;
            }
        }
        return index;
    }

    public boolean containsElement(CanvasElement element, boolean recursively) {
        if (!recursively || children.contains(element)) return children.contains(element);
        for (CanvasElement child : children) {
            if (child == element) return true;
            if (child instanceof Compound) {
                if (((Compound) child).containsElement(element, true)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void setColor(Color color) {

    }

    @Override
    public double getElementOpacity() {
        return 0;
    }

    @Override
    public void setElementOpacity(double opacity) {

    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void select() {
        selected = true;
        new SelectCompoundStrategy().select(parent, this);
    }

    @Override
    public void deselect() {
        selected = false;
        new DeselectElementStrategy().deselect(parent, this);
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public boolean insideBounds(double x, double y) {
        return false;
    }

    @Override
    public boolean overlaps(double x, double y, double width, double height) {
        for (CanvasElement ce : children) {
            if (ce.overlaps(x, y, width, height)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void hide() {
        for (CanvasElement child : children) {
            child.hide();
        }
    }

    @Override
    public void show() {
        for (CanvasElement child : children) {
            child.show();
        }
    }

    private int getAbsoluteIndex(Canvas canvas) {
        int count = 0;
        for (CanvasElement ce : canvas.getCanvasElements()) {
            if (ce instanceof Compound) {
                count += compoundIndexes((Compound) ce, 0);
            }
            if (ce.equals(this)) break;
            count++;
            //indexes.add(canvas.getCanvasElements().indexOf(ce) + count);
        }
        return count;
    }

    private int compoundIndexes(Compound compound, int count) {
        for (CanvasElement ce : compound.getChildren()) {
            if (ce instanceof Compound) {
                count += compoundIndexes(compound, count);
            } else {
                count++;
            }
        }
        return count - 1;
    }

    @Override
    public void recolor(Color color) {
        for (CanvasElement element : children) {
            element.recolor(color);
        }
    }

    @Override
    public void draw() {
        new DrawCompoundStrategy().draw(getParent(), this);
    }

    @Override
    public void remove() {
        for (CanvasElement element : getChildren()) {
            element.remove();
        }
    }

    @Override
    public SelectionBox getSelectionBox() {
        return selectionBox;
    }

    @Override
    public void setSelectionBox(SelectionBox selectionBox) {
        this.selectionBox = selectionBox;
    }

    @Override
    public void drag(double x, double y) {
        for (CanvasElement ce : getChildren()) {
            ce.drag(x, y);
        }

        setX(getX() + x);
        setY(getY() + y);

        if (isSelected()) {
            getSelectionBox().drag(x, y);
        }
    }

    @Override
    public void resize(double width, double height) {
        new ResizeCompoundStrategy().resize(getParent(), this, width, height);
        configure_X_Y_Width_Height();
    }

    @Override
    public void resizeWidth(double width) {
        new ResizeCompoundStrategy().resize(getParent(), this, width, -1);
        configure_X_Y_Width_Height();
    }

    @Override
    public void resizeHeight(double height) {
        new ResizeCompoundStrategy().resize(getParent(), this, -1, height);
        configure_X_Y_Width_Height();
    }

    @Override
    public void position(double x, double y) {

    }
    @Override
    public String accept(CanvasElementVisitor visitor){
        return visitor.visitGroup(this);
    }
}
