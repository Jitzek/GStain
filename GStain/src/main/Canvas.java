package main;

import javafx.scene.layout.Pane;
import main.canvasElements.CanvasElement;
import main.canvasElements.Compound;
import main.canvasElements.SelectionPoint;

import java.util.ArrayList;

public class Canvas extends Pane {
    private String name;
    private static Canvas Instance;
    private final ArrayList<CanvasElement> canvasElements = new ArrayList<>();
    private SelectionPoint selectedSelectionPoint = null;

    private Canvas() {
        // Singleton
    }

    public static Canvas getInstance() {
        if (Instance == null) Instance = new Canvas();
        return Instance;
    }

    public void destroy() {
        Instance = null;
    }

    public String getName() {
        return Instance.name;
    }
    public void setName(String name) {
        Instance.name = name;
    }

    public SelectionPoint getSelectedSelectionPoint() {
        return selectedSelectionPoint;
    }

    public void setSelectedSelectionPoint(SelectionPoint sp) {
        selectedSelectionPoint = sp;
    }

    /**
     * (Recursively) iterates through all elements until requested has been reached
     * @param element Given element
     * @param recursively true: Iterate through compounds
     *                    false: Don't Iterate through compounds
     * @return Index of requested element
     */
    public int getIndexOfElement(CanvasElement element, boolean recursively) {
        if (!recursively) return getCanvasElements().indexOf(element);
        int index = 0;

        for (CanvasElement ce : getCanvasElements()) {
            if (ce.equals(element)) break;
            if (ce instanceof Compound) {
                if (((Compound) ce).containsElement(element, true)) {
                    index += ((Compound) ce).getIndexOfElement(element, true);
                    break;
                }
                index += ((Compound) ce).getSize(true);
            } else {
                index++;
            }
        }

        return index;
    }

    public void selectElements(CanvasElement... elements) {
        for (CanvasElement element : elements) element.select();
    }

    public void deselectElements(CanvasElement... elements) {
        for (CanvasElement element : elements) element.deselect();
    }

    public void selectAllElements() {
        for (CanvasElement element : getCanvasElements()) element.select();
    }

    public void deselectAllElements() {
        for (CanvasElement element : getCanvasElements()) element.deselect();
    }


    public ArrayList<CanvasElement> getCanvasElements() {
        return canvasElements;
    }

    public ArrayList<CanvasElement> getSelectedCanvasElements() {
        ArrayList<CanvasElement> selectedElements = new ArrayList<>();
        for (CanvasElement element : getCanvasElements()) {
            if (element.isSelected()) {
                selectedElements.add(element);
            }
        }
        return selectedElements;
    }

    public void addCanvasElement(CanvasElement element) {
        getCanvasElements().add(0, element);
    }

    /**
     * Returns element on given index
     * @param index Index of requested element
     * @param recursively true: Iterate through compounds
     *                    false: Don't iterate through compounds
     * @param ignore_compounds true: Ignore compounds, index won't be counted up
     *                         false: Don't ignore compounds, index will be counted up
     * @return The element on the requested index
     */
    public CanvasElement getCanvasElementAt(int index, boolean recursively, boolean ignore_compounds) {
        if (!recursively) return getCanvasElements().get(index);
        CanvasElement current = null;
        for (int i = 0; i <= index; i++) {
            current = getCanvasElements().get(i);
            if (current instanceof Compound) {
                for (CanvasElement child : ((Compound) current).getChildren()) {
                    if (ignore_compounds) {
                        current = child;
                        if (i++ == index) break;
                    } else {
                        if (i++ == index) break;
                        current = child;
                    }
                }
            }
        }
        return current;
    }

    public void addCanvasElements(ArrayList<CanvasElement> elements) {
        Instance.getCanvasElements().addAll(elements);
    }

    public void insertCanvasElement(int index, CanvasElement element) {
        Instance.getCanvasElements().add(index, element);
    }

    /**
     * Remove requested element
     * @param element Element to be removed
     * @param recursively true: Iterate through compounds
     *                    false: Don't Iterate through compounds
     */
    public void removeCanvasElement(CanvasElement element, boolean recursively) {
        if (getCanvasElements().contains(element)) {
            getCanvasElements().remove(element);
            return;
        }
        if (!recursively) return;
        for (CanvasElement ce : getCanvasElements()) {
            if (ce.equals(element)) break;
            else if (ce instanceof Compound) {
                if (removeFromCompound((Compound) ce, element)) return;
            }
        }
    }
    // Sub algorithm
    private boolean removeFromCompound(Compound compound, CanvasElement element) {
        for (CanvasElement ce : compound.getChildren()) {
            if (ce instanceof Compound) return removeFromCompound((Compound) ce, element);
            else if (ce.equals(element)) {
                compound.removeChild(ce);
                return true;
            }
        }
        return false;
    }

    public void removeCanvasElementAt(int index) {
        getCanvasElements().remove(index);
    }

    public void removeCanvasElements(ArrayList<CanvasElement> elements) {
        getCanvasElements().removeAll(elements);
    }

    @Override
    public boolean isResizable() {
        return true;
    }
}
