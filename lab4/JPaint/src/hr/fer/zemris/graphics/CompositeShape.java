package hr.fer.zemris.graphics;

import hr.fer.zemris.renderer.Renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * @author Filip Gulan
 */
public class CompositeShape implements GraphicalObject {

    private List<GraphicalObject> objects;
    private boolean selected;
    private List<GraphicalObjectListener> listeners;

    public CompositeShape() {
        this(new ArrayList<>());
    }

    public CompositeShape(List<GraphicalObject> objects) {
        this.objects = objects;
        this.listeners = new ArrayList<>();
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        notifySelectionListeners();
    }

    @Override
    public int getNumberOfHotPoints() {
        return 0;
    }

    @Override
    public Point getHotPoint(int index) {
        return null;
    }

    @Override
    public void setHotPoint(int index, Point point) {

    }

    @Override
    public boolean isHotPointSelected(int index) {
        return false;
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {

    }

    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        return Double.MAX_VALUE;
    }

    @Override
    public void translate(Point delta) {
        objects.forEach(object -> object.translate(delta));
        notifyObservers();
    }

    @Override
    public Rectangle getBoundingBox() {
        if (objects.isEmpty()) {
            return null;
        }
        Rectangle rect = objects.get(0).getBoundingBox();
        int minX = (int)rect.getMinX();
        int maxX = (int)rect.getMaxX();
        int minY = (int)rect.getMinY();
        int maxY = (int)rect.getMaxY();

        for (GraphicalObject object : objects) {
            rect = object.getBoundingBox();
            minX = rect.getMinX() < minX ? (int)rect.getMinX() : minX;
            maxX = rect.getMaxX() > maxX ? (int)rect.getMaxX() : maxX;
            minY = rect.getMinY() < minY ? (int)rect.getMinY() : minY;
            maxY = rect.getMaxY() > maxY ? (int)rect.getMaxY() : maxY;
        }
        return new Rectangle(new Point(minX, minY), new Dimension(maxX - minX, maxY - minY));
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        if(objects.size() == 0) {
            return Double.MAX_VALUE;
        }

        return objects
                .stream()
                .mapToDouble(object -> object.selectionDistance(mousePoint))
                .min()
                .getAsDouble();
    }

    @Override
    public void render(Renderer r) {
        objects.forEach(object -> object.render(r));
    }

    @Override
    public void addGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.add(l);
    }

    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.remove(l);
    }

    @Override
    public String getShapeName() {
        return "Composite";
    }

    @Override
    public GraphicalObject duplicate() {
        List<GraphicalObject> newObjects = objects
                .stream()
                .map(object -> object.duplicate())
                .collect(Collectors.toList());
        return new CompositeShape(newObjects);
    }

    @Override
    public String getShapeID() {
        return "@COMP";
    }

    @Override
    public void save(List<String> rows) {
        objects.forEach(object -> object.save(rows));
        rows.add(String.format("%s %d", getShapeID(), objects.size()));
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        int count = Integer.parseInt(data.trim());
        List<GraphicalObject> newObjects = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            newObjects.add(stack.pop());
        }
        stack.push(new CompositeShape(newObjects));
    }

    private void notifyObservers() {
        listeners.forEach(listener -> listener.graphicalObjectChanged(this));
    }

    public void notifySelectionListeners() {
        listeners.forEach(listener -> listener.graphicalObjectSelectionChanged(this));
    }

    public List<GraphicalObject> getObjects() {
        return objects;
    }
}
