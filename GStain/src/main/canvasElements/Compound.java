package main.canvasElements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import main.Canvas;
import main.strategies.canvasElementStrategies.deselect.DeselectCompoundStrategy;
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
    private Path selectionStyle = null;

    public Compound(Canvas parent) {
        this.parent = parent;
        this.uuid = UUID.randomUUID();
        children = new ArrayList<>();
        setWidth(-1);
        setHeight(-1);
    }

    public Compound(Canvas parent, ArrayList<CanvasElement> canvasElements) {
        this.parent = parent;
        this.uuid = UUID.randomUUID();
        children = canvasElements;
        setWidth(-1);
        setHeight(-1);
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
    }

    public void addChildren(CanvasElement... elements) {
        children.addAll(Arrays.asList(elements));
    }

    public void addChildren(ArrayList<CanvasElement> elements) {
        children.addAll(elements);
    }

    public void removeChild(CanvasElement element) {
        children.remove(element);
    }

    public void removeChildren(CanvasElement... elements) {
        children.removeAll(Arrays.asList(elements));
    }

    public void removeChildren(ArrayList<CanvasElement> elements) {
        children.removeAll(elements);
    }

    public void removeChildAt(int index) {
        children.remove(index);
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
        return 0;
    }

    @Override
    public void setX(double x) {

    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public void setY(double y) {

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
        return 0;
    }

    @Override
    public void setWidth(double width) {

    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public void setHeight(double height) {

    }

    @Override
    public void select() {
        selected = true;
        new SelectCompoundStrategy().select(parent, this);
    }

    @Override
    public void deselect() {
        selected = false;
        new DeselectCompoundStrategy().deselect(parent, this);
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
    public void setSelectionStyle(Path selectionStyle) {
        this.selectionStyle = selectionStyle;
    }

    @Override
    public Path getSelectionStyle() {
        return selectionStyle;
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
    public void decorate() {

    }

    @Override
    public void drag(double x, double y) {
        for (CanvasElement ce : getChildren()) {
            ce.drag(x, y);
        }
    }

    @Override
    public void resize(double width, double height) {
        new ResizeCompoundStrategy().resize(getParent(), this, width, height);
    }

    @Override
    public void resizeWidth(double width) {
        new ResizeCompoundStrategy().resize(getParent(), this, width, -1);
    }

    @Override
    public void resizeHeight(double height) {
        new ResizeCompoundStrategy().resize(getParent(), this, -1, height);
    }

    @Override
    public void position(double x, double y) {

    }
    @Override
    public String accept(CanvasElementVisitor visitor){
        return visitor.visitGroup(this);
    }
}
