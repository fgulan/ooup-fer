package hr.fer.zemris.model;

import hr.fer.zemris.graphics.GraphicalObject;
import hr.fer.zemris.graphics.GraphicalObjectListener;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class DocumentModel implements GraphicalObjectListener {

    public final static double SELECTION_PROXIMITY = 10;

    private List<GraphicalObject> objects;
    private List<GraphicalObject> roObjects;

    private List<GraphicalObject> selectedObjects;
    private List<GraphicalObject> roSelectedObjects;

    private Set<DocumentModelListener> listeners;

    public DocumentModel() {
        objects = new ArrayList<>();
        roObjects = Collections.unmodifiableList(objects);
        selectedObjects = new ArrayList<>();
        roSelectedObjects = Collections.unmodifiableList(selectedObjects);
        listeners = new HashSet<>();
    }

    public void clear() {
        objects.forEach((object) -> {
            object.removeGraphicalObjectListener(DocumentModel.this);
        });
        objects.clear();
        selectedObjects.clear();
        notifyListeners();
    }

    public void addGraphicalObject(GraphicalObject object) {
        if(object.isSelected() && !selectedObjects.contains(object)) {
            selectedObjects.add(object);
        }
        objects.add(object);
        object.addGraphicalObjectListener(this);
        notifyListeners();
    }

    public void removeGraphicalObject(GraphicalObject object) {
        object.setSelected(false);
        object.removeGraphicalObjectListener(this);
        objects.remove(object);

        notifyListeners();
    }

    public List<GraphicalObject> list() {
        return roObjects;
    }

    public boolean addDocumentModelListener(DocumentModelListener listener) {
        return listeners.add(listener);
    }

    public boolean removeDocumentModelListener(DocumentModelListener listener) {
        return listeners.remove(listener);
    }

    public void notifyListeners() {
        listeners.forEach((listener) -> {
            listener.documentChange();
        });
    }

    public List<GraphicalObject> getSelectedObjects() {
        return roSelectedObjects;
    }

    public void increaseZ(GraphicalObject object) {
        int index = objects.indexOf(object);
        if(index != -1 && index < objects.size() - 1) {
            Collections.swap(objects, index, index + 1);
        }
        notifyListeners();

    }

    public void decreaseZ(GraphicalObject object) {
        int index = objects.indexOf(object);
        if(index != -1 && index > 0) {
            Collections.swap(objects, index, index - 1);
        }
        notifyListeners();

    }

    public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
        TreeMap<Double, GraphicalObject> selections = new TreeMap<>();
        objects.forEach((object) -> {
            double distance = object.selectionDistance(mousePoint);
            if (distance <= SELECTION_PROXIMITY) {
                selections.put(distance, object);
            }
        });
        if (selections.isEmpty()) {
            return null;
        }
        return selections.firstEntry().getValue();
    }

    public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
        int size = object.getNumberOfHotPoints();
        for (int i = 0; i < size; i++) {
            if(object.getHotPointDistance(i, mousePoint) <= SELECTION_PROXIMITY) {
                return i;
            }
        }
        return -1;
    }

    public void deselectAll() {
        roSelectedObjects.forEach(object -> object.setSelected(false));
        selectedObjects.clear();
    }

    @Override
    public void graphicalObjectChanged(GraphicalObject go) {
        notifyListeners();
    }

    @Override
    public void graphicalObjectSelectionChanged(GraphicalObject go) {
        if (go.isSelected() && !selectedObjects.contains(go)) {
            selectedObjects.add(go);
        }
        notifyListeners();
    }
}
