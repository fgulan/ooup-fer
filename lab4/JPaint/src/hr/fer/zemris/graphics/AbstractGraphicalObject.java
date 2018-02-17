package hr.fer.zemris.graphics;

import hr.fer.zemris.geometry.GeometryUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Gulan
 */
public abstract class AbstractGraphicalObject implements GraphicalObject {

    private Point[] hotPoints;
    private boolean[] hotPointSelected;
    private boolean selected;
    private List<GraphicalObjectListener> listeners = new ArrayList<>();

    public AbstractGraphicalObject(Point[] hotPoints) {
        this.hotPoints = hotPoints;
        this.hotPointSelected = new boolean[hotPoints.length];
    }

    @Override
    public Point getHotPoint(int index) {
        if(index < hotPoints.length) {
            return hotPoints[index];
        }
        return null;
    }

    @Override
    public void setHotPoint(int index, Point point) {
        if(index < hotPoints.length) {
            hotPoints[index] = point;
        }
    }

    @Override
    public int getNumberOfHotPoints() {
        return hotPoints.length;
    }

    @Override
    public double getHotPointDistance(int index, Point mousePoint) throws IndexOutOfBoundsException {
        if(index >= hotPoints.length) {
            throw new IndexOutOfBoundsException("Hotpoint index out of bounds.");
        }
        return GeometryUtil.distanceFromPoint(hotPoints[index], mousePoint);
    }

    @Override
    public boolean isHotPointSelected(int index) {
        if(index < hotPointSelected.length) {
            return hotPointSelected[index];
        }
        return false;
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {
        if(index < hotPointSelected.length) {
            hotPointSelected[index] = selected;
        }
        notifyListeners();
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
    public void translate(Point delta) {
        for (Point point : hotPoints) {
            point.x += delta.x;
            point.y += delta.y;
        }
        notifyListeners();
    }

    @Override
    public void addGraphicalObjectListener(GraphicalObjectListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners() {
        listeners.forEach((listener) -> {
            listener.graphicalObjectChanged(AbstractGraphicalObject.this);
        });
    }

    public void notifySelectionListeners() {
        listeners.forEach((listener) -> {
            listener.graphicalObjectSelectionChanged(AbstractGraphicalObject.this);
        });
    }

}
